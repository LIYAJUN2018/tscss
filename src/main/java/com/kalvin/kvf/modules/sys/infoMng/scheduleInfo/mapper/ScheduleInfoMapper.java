package com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.entity.Schedule;

import java.util.List;
import java.util.Map;

public interface ScheduleInfoMapper extends BaseMapper<Schedule> {
    List<Map> selectScheduleList();

    void removeAll();
}
