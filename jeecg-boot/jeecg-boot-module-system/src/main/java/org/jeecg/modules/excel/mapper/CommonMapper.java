package org.jeecg.modules.excel.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CommonMapper {
    //示例方法，每个表应有一个单独的接口
    void insertExcel(Map<String, String> map);
}
