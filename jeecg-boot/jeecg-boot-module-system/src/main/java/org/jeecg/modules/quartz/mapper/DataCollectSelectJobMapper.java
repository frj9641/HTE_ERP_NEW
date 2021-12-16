package org.jeecg.modules.quartz.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataCollectSelectJobMapper {
    @MapKey(value = "")
    List<Map<String, Object>> selectAllByDate(String from, String to);

    @MapKey(value = "")
    List<Map<String, Object>> selectNiByDate(String from, String to);

    @MapKey(value = "")
    List<Map<String, Object>> selectAllDataCollect();

}
