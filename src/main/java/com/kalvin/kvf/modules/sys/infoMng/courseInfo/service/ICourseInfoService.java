package com.kalvin.kvf.modules.sys.infoMng.courseInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.entity.Course;

import java.util.List;
import java.util.Map;

public interface ICourseInfoService extends IService<Course>{

	/**
	 * 
	  * 获取课程列表，分页
	  * 本方法由 LYJ 创建于2020年2月2日 下午10:34:47
	 * @param course
	 * @return Page<User>
	 */
	Page<Course> listUserPage(Course course);

	List<Map<String, Object>> listCourseForCode();

}
