package org.jeecg.modules.demo.ckproduct.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.ckproduct.entity.HteCkProduct;

import java.util.List;
import java.util.Map;

/**
 * @Description: 产出品业务表
 * @Author: jeecg-boot
 * @Date: 2021-11-10
 * @Version: V1.0
 */
public interface HteCkProductMapper extends BaseMapper<HteCkProduct> {

    List<HteCkProduct> getSumWater(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    List<HteCkProduct> getSumSludge(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @MapKey("depart_name")
    Map<String,Object> getSumWaterMap(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    List<HteCkProduct> getNewProductList(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @MapKey("depart_name")
    Map<String,Map<String, Object>> getMaxCkProductWater(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @MapKey("depart_name")
    Map<String,Map<String, Object>> getSumCkProductTzWater(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
}
