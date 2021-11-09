package org.jeecg.modules.demo.purchase.service.impl;

import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.demo.purchase.entity.HteKcMaterialPurchase;
import org.jeecg.modules.demo.purchase.mapper.HteKcMaterialPurchaseMapper;
import org.jeecg.modules.demo.purchase.service.IHteKcMaterialPurchaseService;
import org.jeecg.modules.demo.site.entity.HteSite;
import org.jeecg.modules.demo.site.mapper.HteSiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 采购表
 * @Author: jeecg-boot
 * @Date: 2021-11-05
 * @Version: V1.0
 */
@Service
public class HteKcMaterialPurchaseServiceImpl extends ServiceImpl<HteKcMaterialPurchaseMapper, HteKcMaterialPurchase> implements IHteKcMaterialPurchaseService {

}
