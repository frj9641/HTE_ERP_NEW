package org.jeecg.modules.quartz.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.quartz.entity.CkProduct;
import org.jeecg.modules.quartz.entity.Pertonconsumption;
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
public interface PertonconsumptionDealJobMapper extends BaseMapper<Pertonconsumption> {

	void deleteMedicamentUseData(@Param("staticYearMonth") String staticYearMonth, @Param("sitename") String sitename);

	void insertData(@Param("list") List<Map<String,String>> list);

	void savePertonconsumption(@Param("list") List<Map<String,String>> list);

	@MapKey("")
	List<Map<String, String>> calculatePertonconsumption();

	void truncatePertonconsumption();

	void updateNewColumn();

	void saveSitePertonconsumption(@Param("staticYearMonth") String staticYearMonth, @Param("sitename") String sitename);

	List<String> getSiteList(@Param("staticYearMonth") String staticYearMonth);

	void deleteSitePertonconsumption(@Param("staticYearMonth") String staticYearMonth, @Param("sitename") String sitename);

	void updateSitePertonconsumptionNewColumn(@Param("staticYearMonth") String staticYearMonth, @Param("sitename") String sitename);

}
