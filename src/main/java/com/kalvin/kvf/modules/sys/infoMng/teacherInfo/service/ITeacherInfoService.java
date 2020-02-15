package com.kalvin.kvf.modules.sys.infoMng.teacherInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.sys.infoMng.teacherInfo.entity.Teacher;

public interface ITeacherInfoService extends IService<Teacher> {

	Page<Teacher> listTeacherPage(Teacher teacher);

}
