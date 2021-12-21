package org.jeecg.modules.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.quartz.mapper.CkProductDealJobMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 物料库存处理
 * @Param:
 * @return:
 * @Author: lpf
 * @Date: 2021/11/29 14:57
**/
@Slf4j
@Component
public class MaterialKcDealJob implements Job {

    @Autowired
    private CkProductDealJob ckProductDealJob;
    @Autowired
    private CkProductDealJobMapper ckProductDealJobMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {



    }

}
