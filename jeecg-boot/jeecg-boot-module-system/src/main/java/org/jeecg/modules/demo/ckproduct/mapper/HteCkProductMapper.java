package org.jeecg.modules.demo.ckproduct.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    List<Map<String, HteCkProduct>> getSumWater(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
    List<Map<String, HteCkProduct>> getSumSludge(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
}
