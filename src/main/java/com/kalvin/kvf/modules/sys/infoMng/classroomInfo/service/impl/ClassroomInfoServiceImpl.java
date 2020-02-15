package com.kalvin.kvf.modules.sys.infoMng.classroomInfo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kalvin.kvf.modules.sys.infoMng.classroomInfo.entity.Classroom;
import com.kalvin.kvf.modules.sys.infoMng.classroomInfo.mapper.ClassroomInfoMapper;
import com.kalvin.kvf.modules.sys.infoMng.classroomInfo.service.IClassroomInfoService;

@Service("classroomInfoService")
public class ClassroomInfoServiceImpl extends ServiceImpl<ClassroomInfoMapper, Classroom> implements IClassroomInfoService{

	@Override
	public Page<Classroom> listUserPage(Classroom classroom) {
		  Page<Classroom> page = new Page<>(classroom.getCurrent(), classroom.getSize());
	        List<Classroom> classrooms = baseMapper.selectClassroomList(classroom, page);
	        return page.setRecords(classrooms);
	}
}
