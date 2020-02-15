package com.kalvin.kvf.modules.sys.infoMng.classroomInfo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.sys.infoMng.classroomInfo.entity.Classroom;
import com.kalvin.kvf.modules.sys.infoMng.classroomInfo.service.IClassroomInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("info/classroom")
public class ClassroomInfoController extends BaseController{

	@Autowired
	private IClassroomInfoService classroomInfoService;

	@RequiresPermissions("info:classroom:index")
	@GetMapping("index")
	public ModelAndView index() {
		return new ModelAndView("info/classroomInfo/classroom");
	}

	@GetMapping(value = "list/data")
	public R listData(Classroom classroom) {
		Page<Classroom> page = classroomInfoService.listUserPage(classroom);
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
		ModelAndView mv = new ModelAndView("info/classroomInfo/dialog/classroom_edit");
		Classroom classroom = new Classroom();
		if (id != null) {
			classroom = classroomInfoService.getById(id);
		}
		mv.addObject("classroom", classroom);
		return mv;
	}

	@RequiresPermissions("info:classroom:edit")
	@Transactional
	@PostMapping(value = "edit")
	public R edit(Classroom classroom) {
		classroomInfoService.updateById(classroom);
		return R.ok();
	}

	@RequiresPermissions("info:classroom:add")
	@Transactional
	@PostMapping(value = "add")
	public R add(Classroom classroom) {
		classroomInfoService.saveOrUpdate(classroom);
		return R.ok();
	}

	@RequiresPermissions("info:classroom:del")
	@PostMapping(value = "remove/{id}")
	public R remove(@PathVariable Integer id) {
		classroomInfoService.removeById(id);
		return R.ok();
	}

	@RequiresPermissions("info:classroom:del")
	@PostMapping(value = "removeBatch")
	public R removeBatch(@RequestParam("ids") List<Integer> ids) {
		classroomInfoService.removeByIds(ids);
		return R.ok();
	}

}
