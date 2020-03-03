package com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.controller;

import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.sys.infoMng.common.GA;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.service.IScheduleInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @RequiresPermissions("info:schedule:arranging")
    @GetMapping(value = "arranging")
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED)
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
