package com.kalvin.kvf.modules.sys.infoMng.teacherInfo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.sys.infoMng.teacherInfo.entity.Teacher;
import com.kalvin.kvf.modules.sys.infoMng.teacherInfo.service.ITeacherInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("info/teacher")
public class TeacherInfoController extends BaseController {
	
	@Autowired
	ITeacherInfoService teacherInfoService;
	
	@RequiresPermissions("info:teacher:index")
	@GetMapping("index")
	public ModelAndView index() {
		return new ModelAndView("info/teacherInfo/teacher");
	}

	@GetMapping(value = "list/data")
	public R listData(Teacher teacher) {
		Page<Teacher> page = teacherInfoService.listTeacherPage(teacher);
		return R.ok(page);
	}

	@GetMapping(value = "list/getAllTeacherName")
	public R getAllTeacherName() {
		List<Teacher> list = teacherInfoService.list();
		List<String> collect = list.stream().map(Teacher::getName).collect(Collectors.toList());
		return R.ok(collect);
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
		ModelAndView mv = new ModelAndView("info/teacherInfo/dialog/teacher_edit");
		Teacher teacher = new Teacher();
		if (id != null) {
			teacher = teacherInfoService.getById(id);
		}
		mv.addObject("teacher", teacher);
		return mv;
	}

	@RequiresPermissions("info:teacher:edit")
	@Transactional
	@PostMapping(value = "edit")
	public R edit(Teacher teacher) {
		teacherInfoService.updateById(teacher);
		return R.ok();
	}

	@RequiresPermissions("info:teacher:add")
	@Transactional
	@PostMapping(value = "add")
	public R add(Teacher teacher) {
		teacherInfoService.saveOrUpdate(teacher);
		return R.ok();
	}

	@RequiresPermissions("info:teacher:del")
	@PostMapping(value = "remove/{id}")
	public R remove(@PathVariable Integer id) {
		teacherInfoService.removeById(id);
		return R.ok();
	}

	@RequiresPermissions("info:teacher:del")
	@PostMapping(value = "removeBatch")
	public R removeBatch(@RequestParam("ids") List<Integer> ids) {
		teacherInfoService.removeByIds(ids);
		return R.ok();
	}

}
