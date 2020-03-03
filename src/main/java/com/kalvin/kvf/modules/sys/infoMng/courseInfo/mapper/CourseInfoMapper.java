package com.kalvin.kvf.modules.sys.infoMng.courseInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.entity.Course;

import java.util.List;
import java.util.Map;

public interface CourseInfoMapper extends BaseMapper<Course> {
	
	/**
	 * 
	  * 查询课程列表
	  * 本方法由 LYJ 创建于2020年2月2日 下午10:36:56
	 * @param course
	 * @param page
	 * @return List<Course>
	 */
	 List<Course> selectCourseList(Course course, Page page);

	 List<Map<String, Object>> listCourseForCode();

}
