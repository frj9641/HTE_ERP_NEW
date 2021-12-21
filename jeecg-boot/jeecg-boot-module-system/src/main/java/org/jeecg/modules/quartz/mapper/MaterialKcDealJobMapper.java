package org.jeecg.modules.quartz.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.materialcktzd.entity.HteKcMaterialCkTzd;
import org.jeecg.modules.quartz.entity.MaterialKc;
import org.jeecg.modules.quartz.entity.Pertonconsumption;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description: 定时任务在线管理
 * @Author: jeecg-boot
 * @Date:  2019-01-02
 * @Version: V1.0
 */
@Component
@DS("multi-datasource1")
public interface MaterialKcDealJobMapper extends BaseMapper<MaterialKc> {

    void batchDeleteMaterialKc(@Param("staticYearMonth") String staticYearMonth);

    void saveMaterialKcAll(@Param("list") List<HteKcMaterialCkTzd> list, @Param("staticYearMonth") String staticYearMonth);

    void saveMaterialKcGeneral(@Param("staticYearMonth") String staticYearMonth);

    void batchDeleteMaterialKcGeneral(@Param("staticYearMonth") String staticYearMonth);

}
