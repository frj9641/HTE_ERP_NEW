package org.jeecg.modules.demo.dataCollect.service.impl;

import org.jeecg.modules.demo.dataCollect.entity.HtePortDataCollectDetail;
import org.jeecg.modules.demo.dataCollect.mapper.HtePortDataCollectDetailMapper;
import org.jeecg.modules.demo.dataCollect.service.IHtePortDataCollectDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 质量数据采集明细表
 * @Author: jeecg-boot
 * @Date:   2021-12-02
 * @Version: V1.0
 */
@Service
public class HtePortDataCollectDetailServiceImpl extends ServiceImpl<HtePortDataCollectDetailMapper, HtePortDataCollectDetail> implements IHtePortDataCollectDetailService {
	
	@Autowired
	private HtePortDataCollectDetailMapper htePortDataCollectDetailMapper;
	
	@Override
	public List<HtePortDataCollectDetail> selectByMainId(String mainId) {
		return htePortDataCollectDetailMapper.selectByMainId(mainId);
	}
}
