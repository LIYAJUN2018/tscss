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
public class Schedule extends BaseEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String className;
    private Integer classId;

    private Integer teacherId;

    private String teacher;

    private Integer classroomId;

    private String classroom;

    private Integer courseId;

    private String course;

    private String attr;

    private String time;
}
