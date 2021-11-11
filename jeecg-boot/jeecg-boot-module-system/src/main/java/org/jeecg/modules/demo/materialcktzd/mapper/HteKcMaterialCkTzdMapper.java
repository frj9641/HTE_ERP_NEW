package org.jeecg.modules.demo.materialcktzd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.materialcktzd.entity.HteKcMaterialCkTzd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 出库_调整单表
 * @Author: jeecg-boot
 * @Date:   2021-11-11
 * @Version: V1.0
 */
public interface HteKcMaterialCkTzdMapper extends BaseMapper<HteKcMaterialCkTzd> {

    void updateCheckFlag(String id, String checkFlag);

}
