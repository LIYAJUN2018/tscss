package com.kalvin.kvf;

import com.kalvin.kvf.modules.generator.utils.VelocityKit;
import com.kalvin.kvf.modules.sys.infoMng.common.GA;
import com.kalvin.kvf.modules.sys.infoMng.common.GAUtils;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.service.ICourseInfoService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.velocity.VelocityContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * create by Kalvin on 2019/06/13 20:21
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {

    @Autowired
    ICourseInfoService courseInfoService;

    @Autowired
    GA ga;

    private final static Logger LOGGER = LoggerFactory.getLogger(MyTest.class);

    public static void main(String[] args) throws Exception {
        velocity();
    }

    public static void velocity() {
        VelocityContext ctx = new VelocityContext();
        ctx.put("name", "velocity");
        String destPath = "D:\\vm\\";
        VelocityKit.toFile("table.vm", ctx, destPath + "_table.html");
//        String path = AuxiliaryKit.getGenerateCodePath(TemplateTypeEnum.ENTITY, "user");
//        System.out.println("path = " + path);
    }

    @Test
    public void test1() {
        String str = "abc";
        System.out.println(str);
        this.StringTest(str);
        System.out.println(str);
    }

    public void StringTest(String str) {
        str.toUpperCase();
    }

    @Test
    public void courseTest() {

        List<Map<String, Object>> maps = courseInfoService.listCourseForCode();
        for (Map<String, Object> map :
                maps) {
            for (String key:
                 map.keySet()) {
                System.out.println(key + ": " + map.get(key));
            }

        }

    }

    @Test
    public void GATest(){

        ga.classScheduling();

    }

    @Test
    public void courseGroupingTest(){
        List<String> stringList = Arrays.asList("00030000060001010711", "00030000060001010718", "00030000060001010709", "00230000090001010705", "00230000090001010701", "00230000090001010716", "00050000120001010708", "00050000120001010702", "00050000120001010703", "00060000150001010719", "00060000150001010704", "00060000150001010706", "00070000180001010715", "00070000180001010710", "00070000180001010713", "00080000210001010714", "00090000240001010712", "00100000270001010717", "00100000270001010707", "00110000300001010720");
        Map<String, List<String>> stringListMap = GAUtils.courseGrouping(stringList);
        for (String key : stringListMap.keySet()) {
            System.out.println(key + " : " + stringListMap.get(key));
        }
    }

    @Test
    public void strTest(){
        String a = "09";
        String b = "0" + 9;
        List<String> list = new ArrayList<>();
        list.add(a);
        System.out.println(list.contains(b));
    }

    @Test
    public void listTest(){
        List<String> list=new ArrayList<>();
        list.add("zzz");
        list.add("aaa");
        list.add("bbb");
        list.add("bbb");
        list.add("zzz");
        Set<String> set=new HashSet<String>(list);
        boolean  result= list.size() == set.size() ? true : false;
        System.out.println( result);
    }

    @Test
    public void codingClassroomTest() {
        List<String> list = new ArrayList<>();
        ga.codingClassroom(list);
    }

    @Test
    public void stringTest(){
        String str = "0009000078000701040215";
        System.out.println(str.substring(0, 14) + "0201" + str.substring(18,22));
    }

}
