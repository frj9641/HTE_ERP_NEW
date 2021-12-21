package org.jeecg.modules.mobile.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.demo.materialrk.entity.Site;

import java.util.List;
import java.util.Map;

@Mapper
public interface MobileMapper {

    List<Site> selectDeparts2Work(String id);

    List<Site> selectDeparts2Manage(String[] ids);

    List<String> selectRoles(String id);

}
