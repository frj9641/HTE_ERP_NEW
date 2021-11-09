package org.jeecg.modules.util;

import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.demo.site.entity.HteSite;
import org.jeecg.modules.demo.site.mapper.HteSiteMapper;
import org.jeecg.modules.demo.site.service.IHteSiteService;
import org.jeecg.modules.demo.site.service.impl.HteSiteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrderNoUtil {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private HteSiteServiceImpl hteSiteService;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    public String getOrderNo(String prefix, String id) {
        HteSite hteSite = hteSiteService.getById(id);
        String key = prefix + ":" + hteSite.getSiteNo() + ":" + simpleDateFormat.format(new Date());
        Object o = redisUtil.get(key);
        String result = prefix + castNumToStr(hteSite.getSiteNo()) + simpleDateFormat.format(new Date());
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
