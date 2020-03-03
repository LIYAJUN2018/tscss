package com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.entity.Schedule;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.entity.Schedule1;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.mapper.ScheduleInfoMapper;
import com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.service.IScheduleInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("scheduleInfoService")
public class ScheduleInfoServiceImpl extends ServiceImpl<ScheduleInfoMapper, Schedule> implements IScheduleInfoService {


    @Override
    public List<Map> listUserPage() {
        List<Map> courses = baseMapper.selectScheduleList();
        return courses;
    }

    @Override
    public void removeAll() {
        baseMapper.removeAll();
    }
}
