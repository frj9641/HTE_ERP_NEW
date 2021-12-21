package org.jeecg.modules.demo.reportnewdb.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
@DS("multi-datasource1")
public interface DataCollectInsertJobMapper {

    void insertMonthly(Map<String, String> map);

    void insertDataCollect(List<Map<String, String>> list);
}
