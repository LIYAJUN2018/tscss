package com.kalvin.kvf.modules.sys.infoMng.teacherInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kalvin.kvf.common.entity.BaseEntity;
import com.kalvin.kvf.modules.sys.infoMng.courseInfo.entity.Course;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("teacher")
public class Teacher extends BaseEntity {
	
	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private int id;
	
	private String name;
	
	private String sex;
	
	private Integer age;
	
	private String tel;

	private String teacherNo;

}
