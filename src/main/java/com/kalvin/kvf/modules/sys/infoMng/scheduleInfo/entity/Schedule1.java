package com.kalvin.kvf.modules.sys.infoMng.scheduleInfo.entity;

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
@TableName("class_course")
public class Schedule1 extends BaseEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String className;
    private Integer classId;

    private String T1;
    private String T2;
    private String T3;
    private String T4;
    private String T5;
    private String T6;
    private String T7;
    private String T8;
    private String T9;
    private String T10;
    private String T11;
    private String T12;
    private String T13;
    private String T14;
    private String T15;
    private String T16;
    private String T17;
    private String T18;
    private String T19;
    private String T20;

}
