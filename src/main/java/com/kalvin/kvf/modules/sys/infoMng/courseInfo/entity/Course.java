package com.kalvin.kvf.modules.sys.infoMng.courseInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kalvin.kvf.common.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @ClassName: Course
 * @Description: 课程计划表
 * @date 2020年2月2日 下午10:23:01
 * @author LYJ
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("course")
public class Course extends BaseEntity {
	
	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private int id;
	
	/**
	 * 课程名称
	 */
	private String courseName;
	
	/**
	 * 每周课时
	 */
	private Integer courseTimes;
	
	/**
	 * 课程类型
	 */
	private String courseType;
	
	private String className;
	
	private String teacher;
	
	private Integer classId;
	
	private Integer teacherId;

	@TableField(exist = false)
	private String location;

	private String courseNo;

	private String attr;


}
