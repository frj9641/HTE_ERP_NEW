package org.jeecg.modules.demo.dataCollect.service;

import org.jeecg.modules.demo.dataCollect.entity.HtePortDataCollectDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 质量数据采集明细表
 * @Author: jeecg-boot
 * @Date:   2021-12-02
 * @Version: V1.0
 */
public interface IHtePortDataCollectDetailService extends IService<HtePortDataCollectDetail> {

	public List<HtePortDataCollectDetail> selectByMainId(String mainId);
}
