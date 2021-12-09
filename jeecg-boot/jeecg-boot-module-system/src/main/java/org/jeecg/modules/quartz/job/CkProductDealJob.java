package org.jeecg.modules.quartz.job;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.util.StringUtils;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.demo.ckproduct.entity.HteCkProduct;
import org.jeecg.modules.demo.ckproduct.mapper.HteCkProductMapper;
import org.jeecg.modules.demo.ckproduct.service.IHteCkProductService;
import org.jeecg.modules.quartz.entity.CkProduct;
import org.jeecg.modules.quartz.mapper.CkProductDealJobMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 产出品 定时处理：每天凌晨0点执行
 * @Param:
 * @return:
 * @Author: lpf
 * @Date: 2021/11/29 14:57
**/
@Slf4j
@Component
public class CkProductDealJob implements Job {

    @Autowired
    private CkProductDealJobMapper ckProductDealJobMapper;

    @Autowired
    private HteCkProductMapper hteCkProductMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 每天计算厂站月份的产出品总量
        /*(1) 计算*/
//        Date curruntDay = DateUtils.getPreDate(new Date(), 1); // 往前推一天
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
        Date curruntDay = DateUtils.str2Date("2021-10-24",sdf);
        String[] timeZone = getTimeZone(curruntDay);

        List<CkProduct> productList = getProductList(timeZone);
        // 删除当月已有汇总数据
        ckProductDealJobMapper.batchDelete(timeZone[2]);
        // 保存最新的汇总数据
        if (productList.size() > 0) {
            ckProductDealJobMapper.saveCkProduct(productList);
        }
        ckProductDealJobMapper.batchDeleteCkProductGeneral(timeZone[2]);
        ckProductDealJobMapper.saveCkProductGenaral(timeZone[2]);
        log.info(String.format(" Jeecg-Boot 普通定时任务 CkProductDealJob !  时间:" + DateUtils.getTimestamp()));
    }

    /**
     * @Description: 获取时间区间，及月份 [fromDate, toDate, staticMonth]
     * @Param: [today]
     * @return: java.lang.String[]
     * @Author: lpf
     * @Date: 2021/11/24 11:08
     **/
    public String[] getTimeZone(Date curruntDay) {
        String[] timeZone = new String[3];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curruntDay);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        if (month == 12 && day >= 26) { // 12-26~12-31
            timeZone[0] = Integer.toString(year) + "-12-26";
            timeZone[1] = Integer.toString(year + 1) + "-01-25";
            timeZone[2] = Integer.toString(year + 1) + "-01-01";
        } else if (month == 1 && day <= 25) { // 1-1~1-25
            timeZone[0] = Integer.toString(year - 1) + "-12-26";
            timeZone[1] = Integer.toString(year) + "-01-25";
            timeZone[2] = Integer.toString(year) + "-01-01";
        } else if (day >= 26) { // 26~31
            if (month < 10) {
                timeZone[0] = Integer.toString(year) + "-0" + Integer.toString(month) + "-26";
                if (month < 9) { // month <9
                    timeZone[1] = Integer.toString(year) + "-0" + Integer.toString(month + 1) + "-25";
                    timeZone[2] = Integer.toString(year) + "-0" + Integer.toString(month + 1) + "-01";
                } else { // month =9
                    timeZone[1] = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-25";
                    timeZone[2] = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-01";
                }
            } else { // month =10,11
                timeZone[0] = Integer.toString(year) + "-" + Integer.toString(month) + "-26";
                timeZone[1] = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-25";
                timeZone[2] = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-01";
            }
        } else { // day >=1 && day <=25 && month
            if (month < 11) {
                timeZone[0] = Integer.toString(year) + "-0" + Integer.toString(month - 1) + "-26";
                if (month < 10) { // month < 10
                    timeZone[1] = Integer.toString(year) + "-0" + Integer.toString(month) + "-25";
                    timeZone[2] = Integer.toString(year) + "-0" + Integer.toString(month) + "-01";
                } else { // month = 10
                    timeZone[1] = Integer.toString(year) + "-" + Integer.toString(month) + "-25";
                    timeZone[2] = Integer.toString(year) + "-" + Integer.toString(month) + "-01";
                }
            } else { // month = 11
                timeZone[0] = Integer.toString(year) + "-" + Integer.toString(month - 1) + "-26";
                timeZone[1] = Integer.toString(year) + "-" + Integer.toString(month) + "-25";
                timeZone[2] = Integer.toString(year) + "-" + Integer.toString(month) + "-01";
            }
        }
        return timeZone;
    }

    /**
     * @Description: 获取产出品汇总数据
     * @Param: [timeZone]
     * @return: java.util.List<org.jeecg.modules.quartz.entity.CkProduct>
     * @Author: lpf
     * @Date: 2021/11/29 10:42
    **/
    public List<CkProduct> getProductList(String[] timeZone){

        // 厂站 排放口水量汇总、非排放口水量汇总、调整单不区分采样点汇总
        List<HteCkProduct> aa = hteCkProductMapper.getNewProductList(timeZone[0], timeZone[1]);
        Map<String,Map<String, Object>> maxWater = hteCkProductMapper.getMaxCkProductWater(timeZone[0], timeZone[1]);
        Map<String,Map<String, Object>> sumTzWater = hteCkProductMapper.getSumCkProductTzWater(timeZone[0], timeZone[1]);
//        List<HteCkProduct> aaa = getNewProductList(maxWater, sumTzWater);
        // max(排放口水量汇总, 非排放口水量汇总) + 调整单不区分采样点汇总
        List<HteCkProduct> sumWaterList = new ArrayList<>();
//        sumWaterList = hteCkProductMapper.getSumWater(timeZone[0], timeZone[1]);
        sumWaterList = getNewProductList(maxWater, sumTzWater);
        List<HteCkProduct> sumSludgeList = new ArrayList<>();
        sumSludgeList = hteCkProductMapper.getSumSludge(timeZone[0], timeZone[1]);
        Map<String, CkProduct> productMap = new HashMap();
        for (HteCkProduct product : sumWaterList) {
            String sitename = product.getDepartName();
            CkProduct reportProduct = new CkProduct();
            if (!productMap.containsKey(sitename)) { // 不存在该厂站
                if (sitename.equals("萍乡应急") || sitename.equals("萍乡二期")) {
                    reportProduct.setType("非电镀");
                } else {
                    reportProduct.setType("电镀");
                }
                reportProduct.setSitename(sitename);
                reportProduct.setCkYearMonth(timeZone[2]);
                reportProduct.setSumWater(product.getSlT().toString());
            } else { // 存在该厂站
                log.error("厂站：" + sitename + " 存在重复的汇总水量数据");
            }
            productMap.put(sitename, reportProduct);
        }
        for (HteCkProduct product : sumSludgeList) { // 汇总泥量
            String sitename = product.getDepartName();
            CkProduct reportProduct = new CkProduct();
            if (!productMap.containsKey(sitename)) { // 不存在该厂站
                if (sitename.equals("萍乡应急") || sitename.equals("萍乡二期")) {
                    reportProduct.setType("非电镀");
                } else {
                    reportProduct.setType("电镀");
                }
                reportProduct.setSitename(sitename);
                reportProduct.setCkYearMonth(timeZone[2]);
                reportProduct.setSumSludge(product.getSlT().toString());
            } else { // 存在该厂站
                reportProduct = productMap.get(sitename);
                reportProduct.setSumSludge(product.getSlT().toString());
            }
            productMap.put(sitename, reportProduct);
        }
        List<CkProduct> productList = new ArrayList<CkProduct>();
        Iterator<Map.Entry<String, CkProduct>> it = productMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, CkProduct> entry = it.next();
            productList.add(entry.getValue());
        }
        return productList;
    }

    /**
     * @Description:  max(排放口水量汇总, 非排放口水量汇总) + 调整单不区分采样点汇总
     * @Param: [list]
     * @return: java.util.List<org.jeecg.modules.quartz.entity.CkProduct>
     * @Author: lpf
     * @Date: 2021/12/7 17:03
    **/
    public List<HteCkProduct> getNewProductList(Map<String,Map<String, Object>> maxWater, Map<String,Map<String, Object>> sumTzWater){
        List<HteCkProduct> productList = new ArrayList<>();
        Map<String, Double> map = new HashMap<>();
        Iterator<Map.Entry<String, Map<String, Object>>> it = maxWater.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Map<String, Object>> entry = it.next();
            String sitename = entry.getKey();
            Double slT = (Double)entry.getValue().get("sl_t");
            map.put(sitename, slT);
        }
        Iterator<Map.Entry<String, Map<String, Object>>> it1 = sumTzWater.entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry<String, Map<String, Object>> entry = it1.next();
            String sitename = entry.getKey();
            Double slT = (Double)entry.getValue().get("sl_t");
            if (map.containsKey(sitename)) {
                map.put(sitename, map.get(sitename)+slT);
            } else {
                map.put(sitename, slT);
            }
        }
        Iterator<Map.Entry<String, Double>> it2 = map.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<String, Double> entry = it2.next();
            String sitename = entry.getKey();
            Double slT = entry.getValue();
            HteCkProduct product =  new HteCkProduct();
            product.setDepartName(sitename);
            product.setSlT(slT);
            productList.add(product);
        }
        return productList;
    }
}
