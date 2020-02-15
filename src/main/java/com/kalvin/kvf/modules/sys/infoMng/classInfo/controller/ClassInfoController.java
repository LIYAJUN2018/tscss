package com.kalvin.kvf.modules.sys.infoMng.classInfo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.sys.infoMng.classInfo.entity.Clas;
import com.kalvin.kvf.modules.sys.infoMng.classInfo.service.IClassInfoService;
import com.kalvin.kvf.modules.sys.infoMng.classroomInfo.entity.Classroom;
import com.kalvin.kvf.modules.sys.infoMng.classroomInfo.service.IClassroomInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("info/class")
public class ClassInfoController extends BaseController {
	
	@Autowired
	IClassInfoService classInfoService;

	@Autowired
	IClassroomInfoService classroomInfoService;
	
	@RequiresPermissions("info:class:index")
	@GetMapping("index")
	public ModelAndView index() {
		return new ModelAndView("info/classInfo/class");
	}

	@GetMapping(value = "list/data")
	public R listData(Clas clas) {
		Page<Clas> page = classInfoService.listClassPage(clas);
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
		ModelAndView mv = new ModelAndView("info/classInfo/dialog/class_edit");
		Clas clas = new Clas();
		if (id != null) {
			clas = classInfoService.getById(id);
		}
		List<Classroom> classrooms = classroomInfoService.list();
		mv.addObject("class", clas);
		mv.addObject("classrooms", classrooms);
		return mv;
	}

	@RequiresPermissions("info:class:edit")
	@Transactional
	@PostMapping(value = "edit")
	public R edit(Clas clas) {

		Clas clas1 = classInfoService.findByClassroom(clas.getId(), clas.getClassroomId());
		if(null != clas1){
			return  R.fail("当前教室已被占用，请重新选择教室");
		}
		classInfoService.updateById(clas);
		return R.ok();
	}

	@RequiresPermissions("info:class:add")
	@Transactional
	@PostMapping(value = "add")
	public R add(Clas clas) {
		int id = null == clas.getId() ? 0 : clas.getId();
		int classroomId = null == clas.getClassroomId() ? 0 : clas.getClassroomId();
		Clas clas1 = classInfoService.findByClassroom(id, classroomId);
		if(null != clas1){
			return  R.fail("当前教室已被占用，请重新选择教室");
		}
		classInfoService.save(clas);
		return R.ok();
	}

	@RequiresPermissions("info:class:del")
	@PostMapping(value = "remove/{id}")
	public R remove(@PathVariable Integer id) {
		classInfoService.removeById(id);
		return R.ok();
	}

	@RequiresPermissions("info:class:del")
	@PostMapping(value = "removeBatch")
	public R removeBatch(@RequestParam("ids") List<Integer> ids) {
		classInfoService.removeByIds(ids);
		return R.ok();
	}

}
