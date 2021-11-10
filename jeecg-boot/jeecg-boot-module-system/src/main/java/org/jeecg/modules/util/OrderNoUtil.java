package org.jeecg.modules.util;

import org.jeecg.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrderNoUtil {
    @Autowired
    private RedisUtil redisUtil;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    public String getOrderNo(String prefix, String id) {
        String key = prefix + ":" + id + ":" + simpleDateFormat.format(new Date());
        Object o = redisUtil.get(key);
        String result = prefix + id + simpleDateFormat.format(new Date());
        if (o == null || o.equals("")) {
            redisUtil.set(key, 1, 60 * 60 * 24);
            result += castNumToStr(1);
        } else {
            long incr = redisUtil.incr(key, 1);
            result += castNumToStr(incr);
        }
        return result;
    }

    private String castNumToStr(long num) {
        int length = String.valueOf(num).length();
        return length == 1 ? "00" + num : length == 2 ? "0" + num : "" + num;
    }
}
