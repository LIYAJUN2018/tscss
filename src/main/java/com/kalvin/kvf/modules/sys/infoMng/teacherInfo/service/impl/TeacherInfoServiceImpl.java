package com.kalvin.kvf.modules.sys.infoMng.teacherInfo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.entity.Course;
import com.kalvin.kvf.modules.sys.infoMng.teacherInfo.entity.Teacher;
import com.kalvin.kvf.modules.sys.infoMng.teacherInfo.mapper.TeacherInfoMapper;
import com.kalvin.kvf.modules.sys.infoMng.teacherInfo.service.ITeacherInfoService;

@Service("teacherInfoService")
public class TeacherInfoServiceImpl extends ServiceImpl<TeacherInfoMapper, Teacher> implements ITeacherInfoService {

	@Override
	public Page<Teacher> listTeacherPage(Teacher teacher) {
		Page<Teacher> page = new Page<>(teacher.getCurrent(), teacher.getSize());
        List<Teacher> teachers = baseMapper.selectTeacherList(teacher, page);
        return page.setRecords(teachers);
	}

	
} 
