package com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.controller;

import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.sys.infoMng.Utils;
import com.kalvin.kvf.modules.sys.infoMng.common.GA;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.entity.Schedule;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.service.IScheduleInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("info/schedule")
public class ScheduleInfoController {

//    @Autowired
//    private Arranging arranging;

    @Autowired
    GA ga;


    @Autowired
    private IScheduleInfoService scheduleInfoService;

    @RequiresPermissions("info:schedule:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("info/scheduleInfo/schedule");
    }

    @GetMapping(value = "list/data")
    public R listData() {
        List<Map> page = scheduleInfoService.listUserPage();
        return R.ok(page);
    }

    @GetMapping(value = "list/dataForTeacher")
    public R listDataForTeacher(String teacherName) {
        if (null == teacherName) {
            return R.ok();
        }
        List<Schedule> schedules = scheduleInfoService.selectScheduleListByTeacher(teacherName);
        List list = arrayToList(schedules, false);
        return R.ok(list);
    }

    @GetMapping(value = "list/dataForClass")
    public R listDataForClass(String className) {
        if (null == className) {
            return R.ok();
        }
        List<Schedule> schedules = scheduleInfoService.selectScheduleListByClass(className);
        List list = arrayToList(schedules, true);
        return R.ok(list);
    }

    /**
     * 把数据库中课表形式转换成通用的课表形式
     *
     * @return
     */
    private List arrayToList(List<Schedule> schedules, boolean type) {
        Schedule[] scheduleArray = new Schedule[20];
        for (int i = 0; i < scheduleArray.length; i++) {
            scheduleArray[i] = null;
        }
        for (int i = 0; i < schedules.size(); i++) {
            Schedule schedule = schedules.get(i);
            int number = Utils.getNumber(schedule.getTime());
            scheduleArray[number - 1] = schedule;
        }

        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < scheduleArray.length; i++) {
            if (i != 0 && i % 5 == 0) {
                list.add(map);
                map = new HashMap<>();
            }
            if (null == scheduleArray[i]) {
                map.put("z" + (i % 5 + 1), null);
            } else {
                if (true) {
                    map.put("z" + (i % 5 + 1), scheduleArray[i].getClassroom()
                            + "-" + scheduleArray[i].getCourse()
                    );
                } else {
                    map.put("z" + (i % 5 + 1), scheduleArray[i].getClassName()
                            + "-" + scheduleArray[i].getClassroom()
                            + "-" + scheduleArray[i].getCourse()
                    );
                }
            }
        }
        list.add(map);
        return list;
    }


    @RequiresPermissions("info:schedule:arranging")
    @GetMapping(value = "arranging")
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public R arranging() {
//        ArrayList<Schedule> schedules = arranging.arranging();
//        this.scheduleInfoService.saveBatch(schedules);
        scheduleInfoService.removeAll();
        ga.classScheduling();
        return R.ok();
    }

    @RequiresPermissions("info:schedule:del")
    @PostMapping(value = "remove/{id}")
    public R remove(@PathVariable Integer id) {
        scheduleInfoService.removeById(id);
        return R.ok();
    }

    @RequiresPermissions("info:schedule:del")
    @PostMapping(value = "removeBatch")
    public R removeBatch(@RequestParam("ids") List<Integer> ids) {
        scheduleInfoService.removeByIds(ids);
        return R.ok();
    }
}
