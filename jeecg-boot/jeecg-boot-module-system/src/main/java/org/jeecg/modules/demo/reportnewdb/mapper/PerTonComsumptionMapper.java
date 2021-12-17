package org.jeecg.modules.demo.reportnewdb.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
@DS("multi-datasource1")
public interface PerTonComsumptionMapper {

    @MapKey(value = "")
    List<Map<String, String>> selectAll();

    String selectByParams(String site, String time, String material);

    void updateByParams(String site, String time, String material, String value);
}
