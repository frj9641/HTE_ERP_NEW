package org.jeecg.modules.quartz.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.ckproduct.entity.HteCkProduct;
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


	/**
	 * @Description: 汇总月份 厂站名称列表
	 * @Param: [staticYearMonth]
	 * @return: java.util.List<java.lang.String>
	 * @Author: lpf
	 * @Date: 2021/12/1 17:30
	**/
	List<String> getSiteList( @Param("staticYearMonth") String staticYearMonth);

	/**
	 * @Description: 修改 厂站的水、泥、产泥率  同比环比
	 * @Param: [projectType, staticYearMonth]
	 * @return: void
	 * @Author: lpf
	 * @Date: 2021/12/1 17:11
	**/
	void updateSiteCkProductGeneral(@Param("sitename") String sitename, @Param("staticYearMonth") String staticYearMonth);

	@MapKey("sitename")
	Map<String,Object> getSitenameProduct(@Param("staticYearMonth") String staticYearMonth);

	void batchDeleteSiteCollectPointWater(@Param("staticYearMonth") String staticYearMonth);

	void saveSiteCollectPointWater(@Param("list") List<HteCkProduct> list);

	void saveSiteCollectPointSludge(@Param("list") List<HteCkProduct> list);

	void saveSludgeGradeDetail(@Param("list") List<HteCkProduct> list, @Param("staticYearMonth") String staticYearMonth);

	void batchDeleteSludgeGradeDetail(@Param("staticYearMonth") String staticYearMonth);

	void saveSludgeGradeGeneral(@Param("staticYearMonth") String staticYearMonth);

	void batchDeleteSludgeGradeGeneral(@Param("staticYearMonth") String staticYearMonth);
}
