package com.kalvin.kvf.modules.sys.infoMng.common;

import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;


public class GAUtils {

    public static Integer countTime = 0;

    //随机生成时间片
    public static String randomTime(String gene, List<String> geneList) {
        countTime++;
        if (countTime > 2000) {
            return null;
        }
        int min = 1;
        int max = 20;
        String time;
        //随机生成1到20范围的数字，并将其转化为字符串，方便进行编码
        int temp = min + (int) (Math.random() * (max + 1 - min));
        time = temp < 10 ? "0" + temp : "" + temp;
        if (isTimeRepe(time, gene, geneList) && isTeacherRepe(time, gene, geneList)) {
            return time;
        } else {
            return randomTime(gene, geneList);
        }
    }

    //判断同一个班是否在同一时间内上课有重复
    private static Boolean isTimeRepe(String time, String gene, List<String> geneList) {
        //获得班级编号
        String classNo = cutGene(GAConstant.CLASS_NO, gene);
        for (String str : geneList) {
            //判断班级编号是否相等
            if (classNo.equals(cutGene(GAConstant.CLASS_NO, str))) {
                //班级编号相等的则判断时间是否有重复，没有返回true
                String classTime = cutGene(GAConstant.CLASS_TIME, str);
                if (time.equals(classTime)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Boolean isTeacherRepe(String time, String gene, List<String> geneList) {
        Map<String, List<String>> teacherTimeMap = geneList.stream()
                .collect(Collectors.groupingBy(c ->
                        cutGene(GAConstant.TEACHER_NO, c), mapping(t ->
                        cutGene(GAConstant.CLASS_TIME, t), Collectors.toList())));

        String teacherNo = cutGene(GAConstant.TEACHER_NO, gene);
        List<String> teacherTimeList = teacherTimeMap.get(teacherNo);
        if (null != teacherTimeList && teacherTimeList.contains(time)) {
            return false;
        } else {
            return true;
        }

    }

    public static String cutGene(String aim, String source) {
        switch (aim) {
            case GAConstant.CLASS_NO:
                return source.substring(10, 14);
            case GAConstant.TEACHER_NO:
                return source.substring(0, 4);
            case GAConstant.COURSE_NO:
                return source.substring(4, 10);
            case GAConstant.CLASS_TIME:
                return source.substring(20, 22);
            case GAConstant.CLASSROOM_NO:
                return source.substring(14, 18);
            case GAConstant.COURSE_ATTR:
                return source.substring(19, 20);
            case GAConstant.COURSE_LOCATION:
                return source.substring(18, 19);
            default:
                return "";
        }
    }

    //计算适应度值
    public static double alculateExpectedValue(List<String> individualList) {
        double K1 = 0.5;// 专业课文化所占权重
        double K2 = 0.2;// 其它课所占权重
        double K5 = 0.3;// 课程离散程度所占权重
        int F1 = 0;// 专业课文化课期望总值
        int F2 = 0;// 其它课期望总值
        int F5;// 课程离散程度期望总值
        double Fx;// 适应度值

        for (String gene : individualList) {
            String courseAttr = cutGene(GAConstant.COURSE_ATTR, gene);//获得属性

            String classTime = cutGene(GAConstant.CLASS_TIME, gene);//获得该课程的开课时间
            if (courseAttr.equals(GAConstant.MAIN_CODE)) {
                F1 = F1 + calculateMainExpect(classTime);
            } else {
                F2 = F2 + calculateOtherExpect(classTime);
            }
        }
        F5 = calculateDiscreteExpect(individualList);
        Fx = K1 * F1 + K2 * F2 + K5 * F5;
        return Fx;
    }

    // 计算主课期望值
    private static int calculateMainExpect(String classTime) {
        String[] tenExpectValue = {"01", "05", "09", "13", "17"};//专业课期望值为10时的时间片值
        String[] eightExpectValue = {"02", "06", "10", "14", "18"};//专业课期望值为8时的时间片值
        String[] fourExpectValue = {"03", "07", "11", "15", "19"};//专业课期望值为4时的时间片值
//        String[] twoExpectValue = {"04", "08", "12", "16", "20"};//专业课期望值为2时的时间片值
        //String [] zeroExpectValue = {"05","10","15","20","25"};//专业课期望值为0时的时间片值

        if (ArrayUtils.contains(tenExpectValue, classTime)) {
            return 10;
        } else if (ArrayUtils.contains(eightExpectValue, classTime)) {
            return 8;
        } else if (ArrayUtils.contains(fourExpectValue, classTime)) {
            return 4;
        } else {
            return 2;
        }
    }

    // 计算其它课期望值
    private static int calculateOtherExpect(String classTime) {
        String[] tenExpectValue = {"04", "08", "12", "16", "20"};//体育课期望值为10时的时间片值
        String[] eightExpectValue = {"03", "07", "11", "15", "19"};//体育课期望值为8时的时间片值
        String[] fourExpectValue = {"02", "06", "10", "14", "18"};//体育课期望值为4时的时间片值
        //String [] zeroExpectValue = {"01", "05", "09", "13", "17"};//体育课期望值为0时的时间片值

        if (ArrayUtils.contains(tenExpectValue, classTime)) {
            return 10;
        } else if (ArrayUtils.contains(eightExpectValue, classTime)) {
            return 8;
        } else if (ArrayUtils.contains(fourExpectValue, classTime)) {
            return 4;
        } else {
            return 0;
        }
    }

    // 计算课程离散度期望值
    private static int calculateDiscreteExpect(List<String> individualList) {
        int F5 = 0;//离散程度期望值
        Map<String, List<String>> classTimeMap = courseGrouping(individualList);
        for (List<String> classTimeList : classTimeMap.values()) {
            if (classTimeList.size() > 1) {
                for (int i = 0; i < classTimeList.size() - 1; ++i) {
                    int temp = Integer.parseInt(classTimeList.get(++i)) - Integer.parseInt(classTimeList.get(i - 1));
                    F5 = F5 + judgingDiscreteValues(temp);
                }
            }
        }
        return F5;
    }

    /**
     * 将一个个体（班级课表）的同一门课程的所有上课时间进行一个统计，并且进行一个分组
     *
     * @param individualList
     * @return
     */
    public static Map<String, List<String>> courseGrouping(List<String> individualList) {
       /* Map<String, List<String>> classTimeMap = new HashMap<>();
        //先将一个班级课表所上的课程区分出来（排除掉重复的课程）
        for (String gene : individualList) {
            classTimeMap.put(cutGene(GAConstant.COURSE_NO, gene), null);
        }

        //遍历课程
        for (String courseNo : classTimeMap.keySet()) {
            List<String> classTimeList = new ArrayList<>();
            for (String gene : individualList) {
                //获得同一门课程的所有上课时间片
                if (cutGene(GAConstant.COURSE_NO, gene).equals(courseNo)) {
                    classTimeList.add(cutGene(GAConstant.CLASS_TIME, gene));
                }
            }
            //将课程的时间片进行排序
            Collections.sort(classTimeList);
            classTimeMap.put(courseNo, classTimeList);
        }*/

        // 使用Stream api 和 Lambda表达式比上面一段代码简洁
        Map<String, List<String>> classTimeMap = individualList.stream()
                .collect(Collectors.groupingBy(c ->
                        cutGene(GAConstant.COURSE_NO, c), mapping(t ->
                        cutGene(GAConstant.CLASS_TIME, t), Collectors.toList())));
        return classTimeMap;
    }

    // 判断两课时间差在那个区间并返回对于的期望值
    private static int judgingDiscreteValues(int temp) {
        int[] tenExpectValue = {4, 5, 6, 7, 8, 9, 10, 11};//期望值为10时两课之间的时间差
        int[] sixExpectValue = {12, 13, 14, 15};//期望值为6时两课之间的时间差
        int[] fourExpectValue = {16, 17, 18};//期望值为4时两课之间的时间差
        int[] twoExpectValue = {2, 3};//期望值为2时两课之间的时间差
        //int [] zeroExpectValue = {1,19};//期望值为0时两课之间的时间差
        if (ArrayUtils.contains(tenExpectValue, temp)) {
            return 10;
        } else if (ArrayUtils.contains(sixExpectValue, temp)) {
            return 6;
        } else if (ArrayUtils.contains(fourExpectValue, temp)) {
            return 4;
        } else if (ArrayUtils.contains(twoExpectValue, temp)) {
            return 2;
        } else {
            return 0;
        }
    }

    public Double countValue(HashMap<String, List<String>> individualMap){
        double count = 0;
        for (String key :
                individualMap.keySet()) {
            double value = GAUtils.alculateExpectedValue(individualMap.get(key));
            count += value;
            System.out.println(key + " 的适应度为：" + value);
        }
        System.out.println("适应度均值为：" + count/9);
        return count;
    }


}
