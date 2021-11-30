package org.jeecg.modules.quartz.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.quartz.entity.CkProduct;
import org.jeecg.modules.quartz.entity.QuartzJob;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description: 定时任务在线管理
 * @Author: jeecg-boot
 * @Date:  2019-01-02
 * @Version: V1.0
 */
@Component
@DS("multi-datasource1")
public interface CkProductDealJobMapper extends BaseMapper<CkProduct> {

	/**
	 * @Description: 删除已经保存的当月产出品汇总数据（厂站类型、厂站明细 @ 水量、泥量）
	 * @Param: [ckYearMonth]
	 * @return: void
	 * @Author: lpf
	 * @Date: 2021/11/29 10:23
	**/
	void batchDelete(@Param("ckYearMonth") String ckYearMonth);

	/**
	 * @Description: 保存当月产出品汇总数据（厂站类型、厂站明细 @ 水量、泥量）
	 * @Param: [list]
	 * @return: void
	 * @Author: lpf
	 * @Date: 2021/11/29 10:27
	**/
	void saveCkProduct(@Param("list") List<CkProduct> list);

	@MapKey("id")
	List<Map<String, String>> getCkProductAll(@Param("static_month") String static_month);

	/**
	 * @Description: 删除已经保存的当月产出品汇总数据（电镀、非电镀、所有项目 @ 水量、泥量、产泥率、各同比环比）
	 * @Param: [ckYearMonth]
	 * @return: void
	 * @Author: lpf
	 * @Date: 2021/11/29 10:22
	**/
	void batchDeleteCkProductGeneral(@Param("ckYearMonth") String ckYearMonth);

	/**
	 * @Description: 保存当月产出品汇总数据（电镀、非电镀、所有项目 @ 水量、泥量、产泥率、各同比环比）
	 * @Param: [ckYearMonth]
	 * @return: void
	 * @Author: lpf
	 * @Date: 2021/11/29 10:22
	**/
	void saveCkProductGenaral(@Param("ckYearMonth") String ckYearMonth);

	@MapKey("project_type")
	Map<String, Map<String, String>> getCkProductGeneralByProjectType(@Param("projectType") String projectType, @Param("staticYearMonth") String staticYearMonth);

}
