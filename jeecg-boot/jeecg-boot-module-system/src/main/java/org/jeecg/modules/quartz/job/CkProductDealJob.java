package org.jeecg.modules.quartz.job;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 示例不带参定时任务
 *
 * @Author Scott
 */
@Slf4j
public class CkProductDealJob implements Job {

    @Autowired
    private CkProductDealJobMapper ckProductDealJobMapper;

    @Autowired
    private HteCkProductMapper hteCkProductMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 每天计算厂站月份的产出品总量
        /*(1) 计算*/
        Date today = new Date();
        String[] timeZone = getTimeZone(today);
//        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
//        Date today = DateUtils.str2Date("2022-11-26",sdf);
        List<Map<String, HteCkProduct>> mapSumWater = new ArrayList<>();
        mapSumWater = hteCkProductMapper.getSumWater(timeZone[0], timeZone[1]);
        List<Map<String, HteCkProduct>> mapSumSludge = new ArrayList<>();
        mapSumSludge = hteCkProductMapper.getSumSludge(timeZone[0], timeZone[1]);
        Map<String, CkProduct> productMap = new HashMap();
        for(Map map:mapSumWater) {
            //使用迭代器迭代
            for (Iterator iterator = map.keySet().iterator(); iterator.hasNext(); ) {
                //map集合主键
                String key = (String) iterator.next();
                //通过键自然就可以得到map集合存储的对应的值了
                HteCkProduct product = (HteCkProduct)map.get(key);
                String sitename = product.getDepartName();
                if (!productMap.containsKey(sitename)) { // 不存在该厂站
                    CkProduct reportProduct = new CkProduct();
                    if (sitename.equals("萍乡应急") || sitename.equals("萍乡二期") ) {
                        reportProduct.setType("非电镀");
                    } else {
                        reportProduct.setType("电镀");
                    }
                    reportProduct.setSitename(sitename);
                    reportProduct.setCkYearMonth(timeZone[2]);
                    reportProduct.setSumWater(product.getSlT().toString());
                } else { // 存在该厂站
//                    log.error("")
                }
            }
        }
        log.info(String.format(" Jeecg-Boot 普通定时任务 SampleJob !  时间:" + DateUtils.getTimestamp()));

    }


   /* public List<Map<String, Object>> selectByCondition() {
        return jdbcTemplate.queryForList("SELECT * FROM sitename_product_month");
    }*/

    /**
     * @Description: 获取时间区间，及月份
     * @Param: [today]
     * @return: java.lang.String[]
     * @Author: lpf
     * @Date: 2021/11/24 11:08
    **/
    public String[] getTimeZone(Date today) {
        String[] output = new String[3];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        if (month == 12 && day >= 26) { // 12-26~12-31
            output[0] = Integer.toString(year) + "-12-26";
            output[1] = Integer.toString(year+1) + "-01-25";
            output[2] = Integer.toString(year+1) + "-01-01";
        } else if (month == 1 && day <= 25) { // 1-1~1-25
            output[0] = Integer.toString(year-1) + "-12-26";
            output[1] = Integer.toString(year) + "-01-25";
            output[2] = Integer.toString(year) + "-01-01";
        } else if (day >= 26) { // 26~31
            if (month < 10) {
                output[0] = Integer.toString(year) + "-0" + Integer.toString(month) +"-26";
                if (month <9) { // month <9
                    output[1] = Integer.toString(year) + "-0" + Integer.toString(month+1) +"-25";
                    output[2] = Integer.toString(year) + "-0" + Integer.toString(month+1) +"-01";
                } else { // month =9
                    output[1] = Integer.toString(year) + "-" + Integer.toString(month+1) +"-25";
                    output[2] = Integer.toString(year) + "-" + Integer.toString(month+1) +"-01";
                }
            } else { // month =10,11
                output[0] = Integer.toString(year) + "-" + Integer.toString(month) +"-26";
                output[1] = Integer.toString(year) + "-" + Integer.toString(month+1) +"-25";
                output[2] = Integer.toString(year) + "-" + Integer.toString(month+1) +"-01";
            }
        } else { // day >=1 && day <=25 && month
            if (month < 11) {
                output[0] = Integer.toString(year) + "-0" + Integer.toString(month-1) +"-26";
                if (month <10) { // month < 10
                    output[1] = Integer.toString(year) + "-0" + Integer.toString(month) +"-25";
                    output[2] = Integer.toString(year) + "-0" + Integer.toString(month) +"-01";
                } else { // month = 10
                    output[1] = Integer.toString(year) + "-" + Integer.toString(month) +"-25";
                    output[2] = Integer.toString(year) + "-" + Integer.toString(month) +"-01";
                }
            } else { // month = 11
                output[0] = Integer.toString(year) + "-" + Integer.toString(month-1) +"-26";
                output[1] = Integer.toString(year) + "-" + Integer.toString(month) +"-25";
                output[2] = Integer.toString(year) + "-" + Integer.toString(month) +"-01";
            }
        }
        return output;
    }

}
