package org.jeecg;

import com.google.gson.internal.$Gson$Preconditions;
import org.apache.poi.hpsf.Decimal;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.demo.ckproduct.mapper.HteCkProductMapper;
import org.jeecg.modules.demo.materialcktzd.entity.HteKcMaterialCkTzd;
import org.jeecg.modules.demo.materialcktzd.mapper.HteKcMaterialCkTzdMapper;
import org.jeecg.modules.excel.ExcelWorker;
import org.jeecg.modules.quartz.entity.CkProduct;
import org.jeecg.modules.quartz.job.CkProductDealJob;
import org.jeecg.modules.quartz.mapper.CkProductDealJobMapper;
import org.jeecg.modules.quartz.mapper.PertonconsumptionDealJobMapper;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class DataDealTest {
    @Autowired
    ExcelWorker excelWorker;
    @Autowired
    CkProductDealJobMapper ckProductDealJobMapper;
    @Autowired
    CkProductDealJob ckProductDealJob;
    @Autowired
    HteKcMaterialCkTzdMapper hteKcMaterialCkTzdMapper;
    @Autowired
    PertonconsumptionDealJobMapper pertonconsumptionDealJobMapper;
    @Autowired
    HteCkProductMapper hteCkProductMapper;

    /**
     * @Description: 保存jeecg-boot中的历史产出品数据 至 sitename_product_month, 并汇总test_ckproduct_general
     * (1) 计算历史数据的同比环比时，考虑空值 & 0值 情况
     * (2) 当前jeecg-boot中只有10月份的产出品数据，无法统计
     * @Param: []
     * @return: void
     * @Author: lpf
     * @Date: 2021/11/29 10:28
     **/
    @Test
    public void batchSaveCkProjuctGenaral() throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = dateFormat1.parse("2019-01-01");
        Date endDate = dateFormat1.parse("2021-11-01");
        Date date = beginDate;
        while (!date.equals(endDate)) {
            System.out.println("统计日期----->" + DateUtils.date2Str(date, dateFormat1));
            String[] timeZone = ckProductDealJob.getTimeZone(date);
            List<CkProduct> productList = ckProductDealJob.getProductList(timeZone);
            // sitename_product_month: 删除当月已有汇总数据
            ckProductDealJobMapper.batchDelete(timeZone[2]);
            // sitename_product_month: 保存最新的汇总数据
            if (productList.size() > 0) {
                ckProductDealJobMapper.saveCkProduct(productList);
                // 删除汇总数据
                ckProductDealJobMapper.batchDeleteCkProductGeneral(timeZone[2]);
                // 保存汇总数据
                ckProductDealJobMapper.saveCkProductGenaral(timeZone[2]);
            }
            c.setTime(date);
            c.add(Calendar.MONTH, 1); // 月份加1天
            date = c.getTime();
        }
    }

    /**
     * @Description: 保存jeecg-boot中的药剂使用数据 至 medicament_use_data, 并汇总 test_ckproduct_general
     *      * (1) 计算历史数据的同比环比时，考虑空值 & 0值 情况
     *      * (2) 当前jeecg-boot中只有10月份的产出品数据，无法统计
     * @Param: []
     * @return: void
     * @Author: lpf
     * @Date: 2021/11/30 14:15
    **/
    @Test
    public void perconsumptionTest() throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = dateFormat1.parse("2021-10-01");
        Date endDate = dateFormat1.parse("2021-11-01");
        Date date = beginDate;
        while (!date.equals(endDate)) {
            System.out.println("统计日期----->" + DateUtils.date2Str(date, dateFormat1));
            String[] timeZone = ckProductDealJob.getTimeZone(date);
            // 汇总水量
            Map<String, Object> productMap = hteCkProductMapper.getSumWaterMap(timeZone[0], timeZone[1]);
            // 药剂 汇总用量
            Map<String, Map<String, Object>> map = hteKcMaterialCkTzdMapper.getMaterialConsumptionBySite(timeZone[0], timeZone[1]);
            // 循环处理 药剂用量
            Map<String, Map<String, Object>> map1 = new HashMap<>();
            for (Map.Entry<String, Map<String, Object>> siteMaterialMap : map.entrySet()) {
                String siteName = (String)siteMaterialMap.getValue().get("depart_name");
                String material = (String) siteMaterialMap.getValue().get("material");
                double slKg = (Double) siteMaterialMap.getValue().get("sl_kg");
                material = materialZnToEn(material);
                Map<String, Object> productDetail = (Map)productMap.get(siteName);
                String sumWater = "0";
                if (productDetail != null) {
                    sumWater = Double.toString((Double) productDetail.get("sl_t"));
                }
                    Map<String, Object> insertDataMap = new HashMap<>();
                if (!material.equals("0") ) { // && sumWater > 0
//                    slKg = slKg / sumWater;
                    if (!map1.containsKey(siteName)) {
                        insertDataMap.put("sitename", siteName);
                        insertDataMap.put("static_year_month", timeZone[2]);//
                        insertDataMap.put("static_year", timeZone[2].substring(0, 4));
                        insertDataMap.put("static_month", String.valueOf(timeZone[2].substring(5, 7)));
                        insertDataMap.put("sum_water", sumWater);
                        insertDataMap.put(material, Double.toString(slKg));
                    } else {
                        insertDataMap = map1.get(siteName);
                        insertDataMap.put(material, Double.toString(slKg));
                    }
                    map1.put(siteName, insertDataMap);
                }
            }

            for (Map.Entry<String, Map<String, Object>> map2 : map1.entrySet()) {
                List<Map<String, String>> list = new ArrayList<>();
                Map<String,Object> map3 = new HashMap<>();

                map3 = map2.getValue();
                for (Map.Entry<String, Object> map4 : map3.entrySet()) {
                    Map<String,String> map5 = new HashMap<>();
                    map5.put("key", map4.getKey());
                    map5.put("value", map4.getValue().toString());
                    list.add(map5);

                }
                Map<String,Object> maps=new HashMap<String,Object>();
                maps.put("list", list);
                pertonconsumptionDealJobMapper.insertData(list);
                System.out.println();
            }
            c.setTime(date);
            c.add(Calendar.MONTH, 1); // 月份加1
            date = c.getTime();
        }
    }

    /**
     * @Description: 中文物料名称转变为英文
     * @Param:
     * @return:
     * @Author: lpf
     * @Date: 2021/11/30 9:56
     **/
    public String materialZnToEn(String material) {
        if (material.equals("石灰")) {
            material = "shihui";
        } else if (material.equals("次钠")) {
            material = "cina";
        } else if (material.equals("氧化剂")) {
            material = "yanghuaji";
        } else if (material.equals("焦钠")) {
            material = "jiaona";
        } else if (material.equals("硫酸")) {
            material = "liusuan";
        } else if (material.equals("稀硫酸")) {
            material = "xi_liusuan";
        } else if (material.equals("硫化钠")) {
            material = "liuhuana";
        } else if (material.equals("浓硫酸")) {
            material = "nong_liusuan";
        } else if (material.equals("盐酸")) {
            material = "yansuan";
        } else if (material.equals("液碱")) {
            material = "yejian";
        } else if (material.equals("纯碱")) {
            material = "chunjian";
        } else if (material.equals("NaOH")) {
            material = "NaOH";
        } else if (material.equals("片碱")) {
            material = "pianjian";
        } else if (material.equals("PAC")) {
            material = "PAC";
        } else if (material.equals("PAM")) {
            material = "PAM";
        } else if (material.equals("阳离子絮凝剂")) {
            material = "yanglizixuningji";
        } else if (material.equals("消泡剂")) {
            material = "xiaopaoji";
        } else if (material.equals("氯化钙")) {
            material = "lvhuagai";
        } else if (material.equals("活性炭")) {
            material = "huoxingtan";
        } else if (material.equals("磷酸二氢钾")) {
            material = "linsuanerqingjia";
        } else if (material.equals("氯化亚铁")) {
            material = "lvhuayatie";
        } else if (material.equals("硫酸亚铁")) {
            material = "liusuanyatie";
        } else if (material.equals("葡萄糖")) {
            material = "putaotang";
        } else if (material.equals("生物酶")) {
            material = "shengwumei";
        } else if (material.equals("倍增生物活性炭")) {
            material = "beizeng_shengwuhuoxingtan";
        } else if (material.equals("重捕集")) {
            material = "zhongbuji";
        } else if (material.equals("乙酸钠")) {
            material = "yisuanna";
        } else if (material.equals("除镍剂")) {
            material = "chunieji";
        } else if (material.equals("柠檬酸")) {
            material = "ningmengsuan";
        } else if (material.equals("除磷剂")) {
            material = "chulinji";
        } else if (material.equals("尿素")) {
            material = "niaosu";
        } else if (material.equals("碳酸氢钠")) {
            material = "tansuanqinna";
        } else if (material.equals("聚合硫酸铁")) {
            material = "juheliusuantie";
        } else if (material.equals("吨袋")) {
            material = "dundai";
        } else if (material.equals("破乳剂")) {
            material = "poruji";
        } else if (material.equals("亚硝酸钠")) {
            material = "yaxiaosuanna";
        } else if (material.equals("工业盐")) {
            material = "gongyeyan";
        } else if (material.equals("用电")) {
            material = "yongdian";
        } else if (material.equals("自来水")) {
            material = "zilaishui";
        } else if (material.equals("汇总水量")) {
            material = "sum_water";
        } else if (material.equals("汇总泥量")) {
            material = "sum_sludge";
        } else if (material.equals("生物活性炭")) {
            material = "shengwuhuoxingtanyuan";
        } else {
            material = "0";
        }
        return material;
    }
}
