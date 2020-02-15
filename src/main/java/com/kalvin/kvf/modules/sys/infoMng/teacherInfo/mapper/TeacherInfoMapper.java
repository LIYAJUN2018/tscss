package com.kalvin.kvf.modules.sys.infoMng.teacherInfo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.modules.sys.infoMng.teacherInfo.entity.Teacher;

public interface TeacherInfoMapper extends BaseMapper<Teacher> {

	List<Teacher> selectTeacherList(Teacher teacher, Page<Teacher> page);

}
