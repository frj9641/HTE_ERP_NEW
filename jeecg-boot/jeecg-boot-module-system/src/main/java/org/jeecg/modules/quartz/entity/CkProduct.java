package org.jeecg.modules.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 定时任务在线管理
 * @Author: jeecg-boot
 * @Date:  2019-01-02
 * @Version: V1.0
 */
@Data
@TableName("sitename_product_month")
public class CkProduct implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**创建人*/
	private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**删除状态*/
	private Integer delFlag;
	/**修改人*/
	private String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**厂站类型：电镀，非电镀*/
	@Excel(name="类型",width=40)
	private String type;
	/**厂站名称*/
	@Excel(name="厂站名称",width=30)
	private String sitename;
	/**产出月份*/
	@Excel(name="产出月份",width=15)
	private String ckYearMonth;
	/**汇总水量*/
	@Excel(name="汇总水量",width=40)
	private String sumWater;
	/**汇总泥量*/
	@Excel(name="汇总泥量",width=15)
	private String sumSludge;
	/**产泥率(‰)*/
	@Excel(name="产泥率(‰)",width=15)
	private String sludgeRate;
	/**日均水量*/
	@Excel(name="日均水量",width=40)
	private String perDayWater;

}
