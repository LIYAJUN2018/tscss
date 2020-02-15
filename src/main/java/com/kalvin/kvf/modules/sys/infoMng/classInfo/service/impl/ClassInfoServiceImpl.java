package com.kalvin.kvf.modules.sys.infoMng.classInfo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kalvin.kvf.modules.sys.infoMng.classInfo.entity.Clas;
import com.kalvin.kvf.modules.sys.infoMng.classInfo.mapper.ClassInfoMapper;
import com.kalvin.kvf.modules.sys.infoMng.classInfo.service.IClassInfoService;
import com.kalvin.kvf.modules.sys.infoMng.classroomInfo.entity.Classroom;

@Service("classInfoService")
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoMapper, Clas> implements IClassInfoService {

	@Override
	public Page<Clas> listClassPage(Clas clas) {
		  Page<Clas> page = new Page<>(clas.getCurrent(), clas.getSize());
	        List<Clas> classes = baseMapper.selectClassList(clas, page);
	        return page.setRecords(classes);
	}

	@Override
	public Clas findByClassroom(int id, int classroomId) {
		return baseMapper.findByClassroom(id, classroomId);
	}

}
