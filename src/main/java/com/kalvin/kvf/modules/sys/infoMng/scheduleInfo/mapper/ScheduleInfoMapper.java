package com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.entity.Schedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ScheduleInfoMapper extends BaseMapper<Schedule> {
    List<Map> selectScheduleList();

    List<Schedule> selectScheduleListByTeacher(@Param("teacherName") String teacherName);

    List<Schedule> selectScheduleListByClass(@Param("className") String className);

    void removeAll();

    List<Schedule> selectScheduleListByClassroom(@Param("classroom")String classroom);
}
