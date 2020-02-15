package com.kalvin.kvf.modules.sys.infoMng.classInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kalvin.kvf.common.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("class")
public class Clas extends BaseEntity {
	
	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	private String className;
	
	private Integer classRs;

	private String classroomName;

	private Integer classroomId;

}
