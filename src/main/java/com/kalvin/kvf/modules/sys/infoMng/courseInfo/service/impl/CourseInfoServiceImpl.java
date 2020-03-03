package com.kalvin.kvf.modules.sys.infoMng.courseInfo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.entity.Course;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.mapper.CourseInfoMapper;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.service.ICourseInfoService;

@Service
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, Course> implements ICourseInfoService {

	@Override
	public Page<Course> listUserPage(Course course) {
		  Page<Course> page = new Page<>(course.getCurrent(), course.getSize());
	        List<Course> courses = baseMapper.selectCourseList(course, page);
	        return page.setRecords(courses);
	}

	@Override
	public List<Map<String, Object>> listCourseForCode() {
		return baseMapper.listCourseForCode();
	}


}
