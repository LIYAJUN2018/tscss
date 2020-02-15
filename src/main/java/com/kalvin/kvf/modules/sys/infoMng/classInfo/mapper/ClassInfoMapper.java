package com.kalvin.kvf.modules.sys.infoMng.classInfo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.modules.sys.infoMng.classInfo.entity.Clas;
import org.apache.ibatis.annotations.Param;

public interface ClassInfoMapper extends BaseMapper<Clas> {

	List<Clas> selectClassList(Clas clas, Page<Clas> page);

    Clas findByClassroom(@Param("id") int id, @Param("classroomId") int classroomId);
}
