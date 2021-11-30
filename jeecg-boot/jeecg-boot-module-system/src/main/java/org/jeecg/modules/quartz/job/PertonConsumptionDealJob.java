package org.jeecg.modules.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.demo.ckproduct.entity.HteCkProduct;
import org.jeecg.modules.demo.ckproduct.mapper.HteCkProductMapper;
import org.jeecg.modules.quartz.entity.CkProduct;
import org.jeecg.modules.quartz.mapper.CkProductDealJobMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 药剂吨耗 定时处理； 每天凌晨1点
 * @Param:
 * @return:
 * @Author: lpf
 * @Date: 2021/11/29 14:57
**/
@Slf4j
@Component
public class PertonConsumptionDealJob implements Job {

    @Autowired
    private CkProductDealJob ckProductDealJob;
    @Autowired
    private CkProductDealJobMapper ckProductDealJobMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {



    }

}
