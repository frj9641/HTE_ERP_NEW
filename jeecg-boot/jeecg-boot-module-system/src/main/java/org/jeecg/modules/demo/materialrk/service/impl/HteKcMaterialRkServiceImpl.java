package org.jeecg.modules.demo.materialrk.service.impl;

import org.jeecg.modules.demo.materialkclog.entity.HteKcLog;
import org.jeecg.modules.demo.materialkclog.service.IHteKcLogService;
import org.jeecg.modules.demo.materialkclog.service.impl.HteKcLogServiceImpl;
import org.jeecg.modules.demo.materialrk.entity.HteKcMaterialRk;
import org.jeecg.modules.demo.materialrk.mapper.HteKcMaterialRkMapper;
import org.jeecg.modules.demo.materialrk.service.IHteKcMaterialRkService;
import org.jeecg.modules.demo.purchase.entity.HteKcMaterialPurchase;
import org.jeecg.modules.demo.purchase.service.IHteKcMaterialPurchaseService;
import org.jeecg.modules.demo.purchase.service.impl.HteKcMaterialPurchaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;

/**
 * @Description: 入库业务表
 * @Author: jeecg-boot
 * @Date: 2021-11-10
 * @Version: V1.0
 */
@Service
public class HteKcMaterialRkServiceImpl extends ServiceImpl<HteKcMaterialRkMapper, HteKcMaterialRk> implements IHteKcMaterialRkService {

    @Autowired
    IHteKcMaterialPurchaseService iHteKcMaterialPurchaseService;
    @Autowired
    IHteKcLogService iHteKcLogService;
    /**
     * @Description: 入库单审核：修改入库check信息，修改采购rk_flag，保存入库单库存记录
     * @Param: [id]
     * @return: void
     * @Author: lpf
     * @Date: 2021/11/12 13:31
    **/
    public void checkRK(String id) {
        // 修改入库 入库check_flag ,check_date, kc_sl_t
        HteKcMaterialRk hteKcMaterialRk = getById(id);
        hteKcMaterialRk.setKcSlT(hteKcMaterialRk.getSlT()); // 赋值实时库存
        hteKcMaterialRk.setCheckFlag(2);
        hteKcMaterialRk.setCheckDate(new Date());
        updateById(hteKcMaterialRk);
        // 修改采购rk_flag
        HteKcMaterialPurchase hteKcMaterialPurchase = iHteKcMaterialPurchaseService.getById(hteKcMaterialRk.getCgDjh());
        hteKcMaterialPurchase.setRkFlag(1);
        iHteKcMaterialPurchaseService.updateById(hteKcMaterialPurchase);
        // 保存入库单库存记录
        String djh = hteKcMaterialRk.getDjh();
        Double slT = hteKcMaterialRk.getSlT();
        HteKcLog hteKcLog = new HteKcLog();
        hteKcLog.setRkDjh(djh);
        hteKcLog.setRelatedDjh(djh);
        hteKcLog.setSlT(slT);
        hteKcLog.setKcSlT(slT);
        iHteKcLogService.save(hteKcLog);
    }


}
