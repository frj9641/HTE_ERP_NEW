package org.jeecg.modules.demo.materialcktzd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.ckproduct.entity.HteCkProduct;
import org.jeecg.modules.demo.material.entity.HteKcMaterial;
import org.jeecg.modules.demo.materialcktzd.entity.HteKcMaterialCkTzd;

import java.util.List;
import java.util.Map;

/**
 * @Description: 出库_调整单表
 * @Author: jeecg-boot
 * @Date: 2021-11-11
 * @Version: V1.0
 */
public interface HteKcMaterialCkTzdMapper extends BaseMapper<HteKcMaterialCkTzd> {

    @MapKey("material")
    List<Map<String, String>> getMaterialConsumption(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @MapKey("description")
    Map<String, Map<String, Object>> getMaterialConsumptionBySite(@Param("fromDate") String fromDate, @Param("toDate") String toDate);


    List<HteKcMaterialCkTzd> getKcMaterialAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
}
