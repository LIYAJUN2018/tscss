package com.kalvin.kvf.modules.sys.infoMng.courseInfo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.sys.infoMng.classInfo.entity.Clas;
import com.kalvin.kvf.modules.sys.infoMng.classInfo.service.IClassInfoService;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.entity.Course;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.service.ICourseInfoService;
import com.kalvin.kvf.modules.sys.infoMng.teacherInfo.entity.Teacher;
import com.kalvin.kvf.modules.sys.infoMng.teacherInfo.service.ITeacherInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("info/course")
public class CourseInfoController extends BaseController {

	@Autowired
	private ICourseInfoService CourseInfoService;
	
	@Autowired
	private ITeacherInfoService teacherInfoservice;
	
	@Autowired
	private IClassInfoService classInfoService;


	@RequiresPermissions("info:course:index")
	@GetMapping("index")
	public ModelAndView index() {
		return new ModelAndView("info/courseInfo/course");
	}

	@GetMapping(value = "list/data")
	public R listData(Course course) {
		Page<Course> page = CourseInfoService.listUserPage(course);
		return R.ok(page);
	}
	
	/**
	 * 
	  * 编辑课程信息
	  * 本方法由 LYJ 创建于2020年2月14日 下午4:16:40
	 * @param id
	 * @return ModelAndView
	 */
	 @GetMapping(value = "edit")
	    public ModelAndView edit(Long id) {
	        ModelAndView mv = new ModelAndView("info/courseInfo/dialog/course_edit");
	        Course course = new Course();
	        if (id != null) {
	            course = CourseInfoService.getById(id);
	        }
	        List<Teacher> teachers = teacherInfoservice.list();
	        List<Clas> classes = classInfoService.list();
	        mv.addObject("teacher", teachers);
	        mv.addObject("classes", classes);
	        mv.addObject("course", course);
	        return mv;
	    }
	 
	 @RequiresPermissions("info:course:edit")
	    @Transactional
	    @PostMapping(value = "edit")
	    public R edit(Course course) {
	     CourseInfoService.updateById(course);
	        return R.ok();
	    }
	 
	 @RequiresPermissions("info:course:add")
	    @Transactional
	    @PostMapping(value = "add")
	    public R add(Course course) {
	       CourseInfoService.saveOrUpdate(course);
	        return R.ok();
	    }
	 
	 @RequiresPermissions("info:course:del")
	    @PostMapping(value = "remove/{id}")
	    public R remove(@PathVariable Integer id) {
		 CourseInfoService.removeById(id);
	        return R.ok();
	    }
	 
	  @RequiresPermissions("info:course:del")
	    @PostMapping(value = "removeBatch")
	    public R removeBatch(@RequestParam("ids") List<Integer> ids) {
		  CourseInfoService.removeByIds(ids);
	        return R.ok();
	    }


}
