package com.kalvin.kvf.modules.sys.infoMng.classroomInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.sys.infoMng.classroomInfo.entity.Classroom;

public interface IClassroomInfoService extends IService<Classroom> {

	Page<Classroom> listUserPage(Classroom classroom);

}
