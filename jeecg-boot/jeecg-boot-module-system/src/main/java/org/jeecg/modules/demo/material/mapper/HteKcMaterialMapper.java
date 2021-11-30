package org.jeecg.modules.demo.material.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.material.entity.HteKcMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 库存原料表
 * @Author: jeecg-boot
 * @Date:   2021-11-29
 * @Version: V1.0
 */
public interface HteKcMaterialMapper extends BaseMapper<HteKcMaterial> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

}
