package org.jeecg.modules.demo.dataCollect.mapper;

import java.util.List;
import org.jeecg.modules.demo.dataCollect.entity.HtePortDataCollectDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 质量数据采集明细表
 * @Author: jeecg-boot
 * @Date:   2021-12-02
 * @Version: V1.0
 */
public interface HtePortDataCollectDetailMapper extends BaseMapper<HtePortDataCollectDetail> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<HtePortDataCollectDetail> selectByMainId(@Param("mainId") String mainId);
}
