package com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.sys.infoMng.classInfo.entity.Clas;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.entity.Schedule;

import java.util.List;
import java.util.Map;

public interface IScheduleInfoService extends IService<Schedule> {
    List<Map> listUserPage();

    List<Schedule> selectScheduleListByTeacher(String teacherName);

    List<Schedule> selectScheduleListByClass(String className);

    void removeAll();
}

