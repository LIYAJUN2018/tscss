package com.kalvin.kvf.modules.sys.infoMng.classroomInfo.entity;

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
@TableName("classroom")
public class Classroom extends BaseEntity{
	
	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private int id;
	
	private String ClassroomName;
	
	private String ClassroomType;
	
	/**
	 * 容量
	 */
	private String ClassroomRs;


	private String ClassroomNo;

}
