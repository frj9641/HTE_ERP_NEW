package org.jeecg.modules.demo.dataCollect.service;

import org.jeecg.modules.demo.dataCollect.entity.HtePortDataCollectDetail;
import org.jeecg.modules.demo.dataCollect.entity.HtePortDataCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 质量数据采集概览表
 * @Author: jeecg-boot
 * @Date:   2021-12-02
 * @Version: V1.0
 */
public interface IHtePortDataCollectService extends IService<HtePortDataCollect> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(HtePortDataCollect htePortDataCollect,List<HtePortDataCollectDetail> htePortDataCollectDetailList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(HtePortDataCollect htePortDataCollect,List<HtePortDataCollectDetail> htePortDataCollectDetailList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
