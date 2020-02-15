package com.kalvin.kvf.modules.sys.infoMng.classInfo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.sys.infoMng.classInfo.entity.Clas;

public interface IClassInfoService extends IService<Clas> {
	
	public Page<Clas> listClassPage(Clas clas);

    public Clas findByClassroom(int id, int classroomId);
}
