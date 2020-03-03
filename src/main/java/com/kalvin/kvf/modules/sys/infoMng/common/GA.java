package com.kalvin.kvf.modules.sys.infoMng.common;

import com.kalvin.kvf.modules.sys.infoMng.classInfo.service.IClassInfoService;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.entity.Course;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.service.ICourseInfoService;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.entity.Schedule;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.service.IScheduleInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GA {

    @Autowired
    ICourseInfoService courseInfoService;

    @Autowired
    IClassInfoService classInfoService;

    @Autowired
    IScheduleInfoService scheduleInfoService;

    private Map<String, String> courseCodeMap;
    private Map<String, String> teacherCodeMap;
    private Map<String, String> classCodeMap;
    private Map<String, String> classroomCodeMap;


    public boolean classScheduling() {

        // 获取课程信息并对课程信息进行编码
//        List<Course> courseList = courseInfoService.list();

        // 先获取课程任务的编码，由SQL直接完成
        // SQL位置:CourseInfoMapper.xml listCourseForCode();
        try {

            List<String> geneList = getCode();

            // 进行时间分配
            List<String> resultGeneList = codingTime(geneList);

            // 将整个编码按班级分组
            Map<String, List<String>> individualMap = transformIndividual(resultGeneList);

            // 进行遗传进化操作
            individualMap = geneticEvolution(individualMap);
            double count = 0;
            for (String key :
                    individualMap.keySet()) {
                double value = GAUtils.alculateExpectedValue(individualMap.get(key));
                count += value;
                System.out.println(key + " 的适应度为：" + value);
            }
            System.out.println("适应度均值为：" + count/9);

            List<String> resultList = closedGene(individualMap);

            //第七步对分配好时间教室的基因进行解码，准备存入数据库
            List<Schedule> schedules = decoding(resultList);

            scheduleInfoService.saveBatch(schedules);

            //操作全部完成
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveCode(List<Map<String, Object>> maps) {
        courseCodeMap = new HashMap<>();
        teacherCodeMap = new HashMap<>();
        classCodeMap = new HashMap<>();
        classroomCodeMap = new HashMap<>();

        for (Map<String, Object> map : maps) {
            courseCodeMap.put(map.get("Course_No").toString(), map.get("Course_name").toString());
            teacherCodeMap.put(map.get("teacherNo").toString(), map.get("name").toString());
            classCodeMap.put(map.get("classNo").toString(), map.get("Class_name").toString());
            classroomCodeMap.put(map.get("ClassroomNo").toString(), map.get("Classroom_name").toString());
        }
    }

    /**
     * 先获取课程任务的编码，由SQL直接完成
     * 编码格式
     * 教师编码4位（0，4）+ 课程编码6位（4，10） + 班级编码4位（10，14） + 教室编码4位（14，18） + 上课时间2位（18，20）
     *
     * @return
     */
    private List<String> getCode() {

        List<String> geneList = new ArrayList<>();
        List<Map<String, Object>> maps = courseInfoService.listCourseForCode();

        // 存储班级、教师、教室、课程的编码
        saveCode(maps);

        // 在编码最后加上默认上课时间00
        for (Map<String, Object> map : maps) {
            int size = Integer.parseInt(map.get("Course_times").toString());

            // 计算一周的上课次数，一次对应一个基因，2次对应两个基因。依此类推
            for (int i = 0; i < size; i++) {
                geneList.add(map.get("code").toString() + GAConstant.DEFAULT_CLASS_TIME);
            }
        }

        return geneList;
    }

    /**
     * 进行时间分配，解决同一班级同一时间上多门课的情况
     *
     * @param geneList
     * @return
     */
    private List<String> codingTime(List<String> geneList) {
        List<String> resultGeneList = new ArrayList<>();

        for (int i = 0; i < geneList.size(); i++) {
            GAUtils.countTime = 0;
            String gene = geneList.get(i);
            String time = GAUtils.randomTime(gene, resultGeneList);

            if (null == time) {
                resultGeneList.clear();
                i = -1;
                GAUtils.countTime = 0;
                System.out.println("***********************分配时间失败！重新分配***********************");
                continue;
            }
            gene = gene.substring(0, 20) + time;
            resultGeneList.add(gene);
        }
        return resultGeneList;
    }

    /**
     * 将编码格式按班级分组，
     * 生成一个班级初级课表，但未解决教师冲突和机房冲突
     *
     * @param resultGeneList
     * @return
     */
    private Map<String, List<String>> transformIndividual(List<String> resultGeneList) {

        // 从数据库中查询出班级信息，并收集每个班级的number
       /* List<String> classNoList = classInfoService.list().stream()
                .map(Clas::getClassNo)
                .collect(Collectors.toList());

        for (String classNo : classNoList) {
            List<String> geneList = new ArrayList<>();
            for (String gene : resultGeneList) {
                if (classNo.equals(GAUtils.cutGene(GAConstant.CLASS_NO, gene))) {
                    geneList.add(gene);
                }
            }

            if (geneList.size() > 1) {
                individualMap.put(classNo, geneList);
            }
        }*/
        // 从数据库中查询出班级信息，并收集每个班级的number,
        // 使用Stream api 和 Lambda表达式比上面一段代码简洁
        Map<String, List<String>> individualMap = resultGeneList.stream()
                .collect(Collectors.groupingBy(t -> GAUtils.cutGene(GAConstant.CLASS_NO, t)));

        return individualMap;
    }

    /**
     * 开始遗传进化操作
     *
     * @param individualMap
     * @return
     */
    private Map<String, List<String>> geneticEvolution(Map<String, List<String>> individualMap) {
        int generation = GAConstant.GENERATION;//进化代数设为100
        List<String> resultGeneList;
        for (int i = 0; i < generation; ++i) {
            //第一步完成交叉操作,产生新一代的父本
            individualMap = hybridization(individualMap);
            //第二步合拢个体准备变异
            closedGene(individualMap);

            resultGeneList = closedGene(individualMap);
            //第三步开始变异
//            resultGeneList = geneticMutation(resultGeneList);
            //第四步进行冲突检测并消除
//            resultGeneList = conflictResolution(resultGeneList);
            //第五步将冲突消除后的个体再次进行分割，按班级进行分配准备进入下一次的进化
            individualMap = transformIndividual(resultGeneList);

        }
        return individualMap;
    }

    //完成整个种群的交叉操作
    private Map<String, List<String>> hybridization(Map<String, List<String>> individualMap) {
        for (String classNo : individualMap.keySet()) {
            List<String> individualList = individualMap.get(classNo);
            List<String> oldIndividualList = new ArrayList<>();
            CollectionUtils.addAll(oldIndividualList, new Object[individualList.size()]);
            Collections.copy(oldIndividualList, individualList);

            //对父代的适应度值和新生成的子代适应值进行对比，选择适应度值高的一个进入下一代的遗传
            individualList = selectiveGene(individualList);
            double newValue = GAUtils.alculateExpectedValue(individualList);
            double oldValue = GAUtils.alculateExpectedValue(oldIndividualList);

            if (newValue > oldValue) {
                System.out.println("旧适应值为：" + oldValue);
                System.out.println("新适应值为：" + newValue);
            }
            if (newValue >= oldValue) {
                individualMap.put(classNo, individualList);
            } else {
                individualMap.put(classNo, oldIndividualList);
            }

        }
        return individualMap;
    }


    //个体间的随机选择两条基因准备进行杂交并生成一个新个体
    private List<String> selectiveGene(List<String> individualList) {
        System.out.println("*********************选择两条基因进行杂交*********************");
        int min = 0;
        int max = individualList.size() - 1;
        boolean flag;
        do {
            //随机生成0到individualList.size - 1的两个数，用来选取基因
            int firstTemp = min + (int) (Math.random() * (max + 1 - min));//选取第一个随机数
            int secondTemp = min + (int) (Math.random() * (max + 1 - min));//选取第二个随机数
            //判断选择的两个随机数为否相同，确保不会选择同一条基因进行交叉操作
            String firstGene = individualList.get(firstTemp);//获取第一条基因
            String secondGene = individualList.get(secondTemp);//获取第二条基因

            //分别获取所选的两条基因的时间片值
            String firstClassTime = GAUtils.cutGene(GAConstant.CLASS_TIME, firstGene);
            String secondClassTime = GAUtils.cutGene(GAConstant.CLASS_TIME, secondGene);

            Boolean firstRepo = GAUtils.isTeacherRepe(secondClassTime, firstGene, individualList);
            Boolean secondRepo = GAUtils.isTeacherRepe(firstClassTime, secondGene, individualList);
            if (firstTemp == secondTemp || firstRepo || secondRepo) {
                flag = false;
            } else {
                //将它们的时间进行交换
                firstGene = firstGene.substring(0, 20) + secondClassTime;
                secondGene = secondGene.substring(0, 20) + firstClassTime;
                //对原有的基因进行移除，然后将交换过时间的两条基因添加进去
                individualList.remove(firstTemp);
                individualList.add(firstTemp, firstGene);
                individualList.remove(secondTemp);
                individualList.add(secondTemp, secondGene);
                flag = true;
            }
        } while (!flag);
        return individualList;
    }

    //将分割好的个体（按班级分好的初始课表）重新合拢在一起，方便进行冲突检测
    private List<String> closedGene(Map<String, List<String>> individualMap) {
        List<String> resultGeneList = new ArrayList<>();
        for (List<String> individualList : individualMap.values()) {
            resultGeneList.addAll(individualList);
        }
        return resultGeneList;
    }

    //基因变异操作
    private List<String> geneticMutation(List<String> resultGeneList) {
        int min = 0;
        int max = resultGeneList.size() - 1;
        double mutationRate = 0.01;//变异概率
        int mutationNumber = (int) (resultGeneList.size() * mutationRate);//每一代所要选取的变异个数,计算公式为基因数量*变异率
        if (mutationNumber < 1) {
            mutationNumber = 1;
        }
        for (int i = 0; i < mutationNumber; ) {
            int temp = min + (int) (Math.random() * (max + 1 - min));//生成随机数
            String gene = resultGeneList.get(temp);

            String newClassTime = GAUtils.randomTime(gene, resultGeneList);
            gene = gene.substring(0, 20) + newClassTime;
            resultGeneList.remove(temp);
            resultGeneList.add(temp, gene);
            i = i + 1;

        }
        return resultGeneList;
    }

    //解决冲突，同一时间一个教师上多门课的冲突
    private List<String> conflictResolution(List<String> resultGeneList) {
        exit:
        for (int i = 0; i < resultGeneList.size(); ++i) {
            String gene = resultGeneList.get(i);
            String teacherNo = GAUtils.cutGene(GAConstant.TEACHER_NO, gene);
            String classTime = GAUtils.cutGene(GAConstant.CLASS_TIME, gene);
            for (int j = i + 1; j < resultGeneList.size(); ++j) {
                String tempGene = resultGeneList.get(j);
                String tempTeacherNo = GAUtils.cutGene(GAConstant.TEACHER_NO, tempGene);
                String tempClassTime = GAUtils.cutGene(GAConstant.CLASS_TIME, tempGene);
                if (teacherNo.equals(tempTeacherNo) && classTime.equals(tempClassTime)) {
                    String newClassTime = GAUtils.randomTime(gene, resultGeneList);
                    gene = gene.substring(0, 20) + newClassTime;
                    continue exit;
                }

            }
        }
        return resultGeneList;
    }

    private List<Schedule> decoding(List<String> resultList) {
        List<Schedule> schedules = new ArrayList<>();
        for (String gene : resultList) {
            Schedule schedule = new Schedule();
            schedule.setClassName(classCodeMap.get(GAUtils.cutGene(GAConstant.CLASS_NO, gene)));
            schedule.setClassroom(classroomCodeMap.get(GAUtils.cutGene(GAConstant.CLASSROOM_NO, gene)));
            schedule.setCourse(courseCodeMap.get(GAUtils.cutGene(GAConstant.COURSE_NO, gene)));
            schedule.setTeacher(teacherCodeMap.get(GAUtils.cutGene(GAConstant.TEACHER_NO, gene)));
            schedule.setAttr(GAUtils.cutGene(GAConstant.COURSE_ATTR, gene));
            schedule.setTime(GAUtils.cutGene(GAConstant.CLASS_TIME, gene));
            schedules.add(schedule);

        }
        return schedules;
    }
}
