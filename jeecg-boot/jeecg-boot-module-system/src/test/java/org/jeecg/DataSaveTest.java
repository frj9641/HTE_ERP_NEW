package org.jeecg;

import org.jeecg.modules.quartz.job.CkProductDealJob;
import org.jeecg.modules.quartz.mapper.DataCollectInsertJobMapper;
import org.jeecg.modules.quartz.mapper.DataCollectSelectJobMapper;
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

    @Test
    public void savePortDetailMonthly(String key1, String type, String key2) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = dateFormat1.parse("2018-08-01");
        Date endDate = dateFormat1.parse("2021-11-01");
        Date date = beginDate;
        while (!date.equals(endDate)) {
            String[] timeZone = ckProductDealJob.getTimeZone(date);
            List<Map<String, Object>> list = dataCollectSelectJobMapper.selectAllByDate(timeZone[0], timeZone[1]);
            save(list, timeZone[2], "depart_name", "总水量", "value");
            c.setTime(date);
            c.add(Calendar.MONTH, 1); // 月份加1天
            date = c.getTime();
        }
    }

    /**
     * 保存detail_port_maonthly表的封装方法
     *
     * @param key1 厂站名称的key
     * @param type 保存的数据类型
     * @param key2 数据值的key
     */
    public void save(List<Map<String, Object>> list, String month, String key1, String type, String key2) {
        if (list.size() > 0) {
            for (Map<String, Object> i : list) {
                Map<String, String> map = new HashMap<>();
                map.put("departName", i.get(key1).toString());
                map.put("time", month);
                map.put("type", type);
                map.put("indicator", "达标率");
                map.put("value", i.get(key2).toString());
                dataCollectInsertJobMapper.insertMonthly(map);
            }
        }
    }
}
