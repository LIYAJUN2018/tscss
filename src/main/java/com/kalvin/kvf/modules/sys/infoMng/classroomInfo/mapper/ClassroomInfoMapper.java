package com.kalvin.kvf.modules.sys.infoMng.classroomInfo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.modules.sys.infoMng.classroomInfo.entity.Classroom;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.entity.Course;

public interface ClassroomInfoMapper extends BaseMapper<Classroom> {
	
	/**
	 * 
	  * 查询教室列表
	  * 本方法由 LYJ 创建于2020年2月2日 下午10:36:56
	 * @param course
	 * @param page
	 * @return List<Course>
	 */
	 List<Classroom> selectClassroomList(Classroom classroom, Page page);

}
