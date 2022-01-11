package org.jeecg;

import org.jeecg.modules.demo.reportnewdb.mapper.PerTonComsumptionMapper;
import org.jeecg.modules.quartz.job.CkProductDealJob;
import org.jeecg.modules.demo.reportnewdb.mapper.DataCollectInsertJobMapper;
import org.jeecg.modules.demo.reportnewdb.mapper.DataCollectSelectJobMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class DataSaveTest {
    @Autowired
    CkProductDealJob ckProductDealJob;
    @Autowired
    private DataCollectInsertJobMapper dataCollectInsertJobMapper;
    @Autowired
    private DataCollectSelectJobMapper dataCollectSelectJobMapper;
    @Autowired
    private PerTonComsumptionMapper perTonComsumptionMapper;

    private final String[] index = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

    @Test
    public void saveDataCollect() throws ParseException {
        List<Map<String, Object>> list = dataCollectSelectJobMapper.selectAllDataCollect();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, String>> res = new ArrayList<>();
        for (Map<String, Object> l : list) {
            Map<String, String> map = new HashMap<>();
            String collect_date = l.get("collect_date").toString();
            String[] timeZone = ckProductDealJob.getTimeZone(simpleDateFormat.parse(collect_date));
            map.put("collectDate", collect_date);
            map.put("collectTime", l.get("collect_time") == null ? null : l.get("collect_time").toString());
            map.put("collectPoint", l.get("collect_point") == null ? null : l.get("collect_point").toString());
            map.put("departName", l.get("depart_name") == null ? null : l.get("depart_name").toString());
            map.put("testIndex", l.get("test_index") == null ? null : l.get("test_index").toString());
            map.put("testValue", l.get("test_value") == null ? null : l.get("test_value").toString());
            map.put("isOk", l.get("is_ok") == null ? null : l.get("is_ok").toString());
            map.put("staticYearMonth", timeZone[2]);
            map.put("staticYear", timeZone[2].split("-")[0]);
            map.put("staticMonth", String.valueOf(Integer.parseInt(timeZone[2].split("-")[1])));
            map.put("standardType", l.get("item_text") == null ? null : l.get("item_text").toString());
            map.put("standardMax", l.get("max") == null ? null : l.get("max").toString());
            res.add(map);
        }
        dataCollectInsertJobMapper.insertDataCollect(res);
    }

    @Test
    public void savePortDetailMonthly() throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = dateFormat1.parse("2018-08-01");
        Date endDate = dateFormat1.parse("2021-11-01");
        Date date = beginDate;
        while (!date.equals(endDate)) {
            String[] timeZone = ckProductDealJob.getTimeZone(date);
            List<Map<String, Object>> list = dataCollectSelectJobMapper.selectAllByDate(timeZone[0], timeZone[1]);
            if (list.size() > 0) {
                for (Map<String, Object> i : list) {
                    Map<String, String> map = new HashMap<>();
                    map.put("departName", i.get("depart_name").toString());
                    map.put("time", timeZone[2]);
                    map.put("type", "总水量");
                    map.put("indicator", "达标率");
                    map.put("value", i.get("value").toString());
                    dataCollectInsertJobMapper.insertMonthly(map);
                }
            }
            c.setTime(date);
            c.add(Calendar.MONTH, 1); // 月份加1天
            date = c.getTime();
        }
    }

    /**
     * 字符串匹配的理想情况
     *
     * @throws ParseException
     */
    @Test
    public void saveNiPortDetailMonthly() throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = dateFormat1.parse("2018-08-01");
        Date endDate = dateFormat1.parse("2021-11-01");
        Date date = beginDate;
        while (!date.equals(endDate)) {
            String[] timeZone = ckProductDealJob.getTimeZone(date);
            List<Map<String, Object>> list = dataCollectSelectJobMapper.selectNiByDate(timeZone[0], timeZone[1]);
            if (list.size() > 0) {
                Map<String, String> map = matchLastCollectPoint(list);
                Map<String, Double> ratio = calculateRatio(list, map);
                for (Map.Entry<String, Double> r : ratio.entrySet()) {
                    Map<String, String> m = new HashMap<>();
                    m.put("departName", r.getKey());
                    m.put("time", timeZone[2]);
                    m.put("type", "含铬水");
                    m.put("indicator", "达标率");
                    m.put("value", String.valueOf(r.getValue()));
                    dataCollectInsertJobMapper.insertMonthly(m);
                }
            }
            c.setTime(date);
            c.add(Calendar.MONTH, 1); // 月份加1天
            date = c.getTime();
        }
    }

    /**
     * @Description: 镍、铬最后一级出水口质量数据筛选 保存
     * @Param: [] 镍 铬
     * @return: void
     * @Author: lpf
     * @Date: 2021/12/24 16:49
    **/
    @Test
    public void saveCollectPortDetail() throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = dateFormat1.parse("2018-08-01");
        Date endDate = dateFormat1.parse("2021-11-01");
        Date date = beginDate;
        String collectPoint = "铬";
        while (!date.equals(endDate)) {
            String[] timeZone = ckProductDealJob.getTimeZone(date);
            List<Map<String, Object>> list = dataCollectSelectJobMapper.getCollectPointDataByDate(timeZone[0], timeZone[1], collectPoint);
            if (list.size() > 0) {
                Map<String, String> map = matchLastCollectPoint(list);
                List<Map<String, Object>>  subList = selectLastCollectPointData(list, map);
                if (subList.size() > 0) {
                    dataCollectInsertJobMapper.saveCollectPointData(subList);
                }
            }
            c.setTime(date);
            c.add(Calendar.MONTH, 1); // 月份加1天
            date = c.getTime();
        }
    }

    /**
     * 返回优先级最高的采样口
     *
     * @param list
     * @return
     */
    public Map<String, String> matchLastCollectPoint(List<Map<String, Object>> list) {
        Map<String, String> map = new HashMap<>();
        for (Map<String, Object> m : list) {
            String port = m.get("collect_point").toString();
            if (!map.containsKey(port)) {
                for (int i = index.length - 1; i >= 0; i--) {
                    if (port.contains(index[i])) {
                        map.put(m.get("depart_name").toString(), port);
                        break;
                    }
                }
            }
        }
        return map;
    }

    /**
     * 计算达标率
     *
     * @param list
     * @return
     */
    public Map<String, Double> calculateRatio(List<Map<String, Object>> list, Map<String, String> map) {
        Map<String, Double> res = new HashMap<>();
        for (Map.Entry<String, String> m : map.entrySet()) {
            double count = 0;
            int sum = 0;
            String site = m.getKey();
            String port = m.getValue();
            for (Map<String, Object> l : list) {
                String collectPoint = l.get("collect_point").toString();
                String departName = l.get("depart_name").toString();
                if (departName.equals(site) && collectPoint.equals(port)) {
                    count++;
                    sum += Integer.parseInt(l.get("is_ok").toString());
                }
            }
//            res.put(site, sum / count);
        }
        return res;
    }


    public List<Map<String, Object>>  selectLastCollectPointData(List<Map<String, Object>> list, Map<String, String> map) {
        List<Map<String, Object>>  res = new ArrayList<>();
        for (Map.Entry<String, String> m : map.entrySet()) {
            String site = m.getKey();
            String port = m.getValue();
            for (Map<String, Object> l : list) {
                String collectPoint = l.get("collect_point").toString();
                String departName = l.get("depart_name").toString();
                if (departName.equals(site) && collectPoint.equals(port)) {
                    res.add(l);
                }
            }
        }
        return res;
    }

    @Test
    public void savePerTonConsumption() {
        List<Map<String, String>> list = perTonComsumptionMapper.selectAll();
        for (Map<String, String> l : list) {
            String material1 = l.get("material");
            String material = DataDealTest.materialZnToEn(material1);
            String sitename = l.get("sitename");
            String staticYearMonth = l.get("static_year_month");
            String value = perTonComsumptionMapper.selectByParams(sitename, staticYearMonth, material);
            perTonComsumptionMapper.updateByParams(sitename, staticYearMonth, material1, value);
        }
    }
}
