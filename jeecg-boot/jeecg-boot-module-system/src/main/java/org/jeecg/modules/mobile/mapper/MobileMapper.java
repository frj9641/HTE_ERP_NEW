package org.jeecg.modules.mobile.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MobileMapper {

    List<String> selectDeparts2Work(String id);

    List<String> selectRoles(String id);

}
