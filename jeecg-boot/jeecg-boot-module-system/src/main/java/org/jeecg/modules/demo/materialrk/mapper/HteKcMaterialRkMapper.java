package org.jeecg.modules.demo.materialrk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.materialrk.entity.HteKcMaterialRk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 入库业务表
 * @Author: jeecg-boot
 * @Date:   2021-11-10
 * @Version: V1.0
 */
public interface HteKcMaterialRkMapper extends BaseMapper<HteKcMaterialRk> {

    void updateKc(String id, String kcSlT);
}
