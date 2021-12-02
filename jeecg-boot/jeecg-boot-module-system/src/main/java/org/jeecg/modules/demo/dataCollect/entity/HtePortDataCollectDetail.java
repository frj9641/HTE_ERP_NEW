package org.jeecg.modules.demo.dataCollect.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 质量数据采集明细表
 * @Author: jeecg-boot
 * @Date:   2021-12-02
 * @Version: V1.0
 */
@ApiModel(value="hte_port_data_collect_detail对象", description="质量数据采集明细表")
@Data
@TableName("hte_port_data_collect_detail")
public class HtePortDataCollectDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**质量数据采集概览表id*/
    @ApiModelProperty(value = "质量数据采集概览表id")
    private java.lang.String parentId;
	/**采样口*/
	@Excel(name = "采样口", width = 15, dictTable = "hte_collect_point", dicText = "collect_point", dicCode = "id")
    @ApiModelProperty(value = "采样口")
    private java.lang.String collectPointId;
	/**指标*/
	@Excel(name = "指标", width = 15, dictTable = "hte_test_index", dicText = "test_index", dicCode = "id")
    @ApiModelProperty(value = "指标")
    private java.lang.String testIndexId;
	/**指标值*/
	@Excel(name = "指标值", width = 15)
    @ApiModelProperty(value = "指标值")
    private java.lang.Double testValue;
	/**明细值*/
	@Excel(name = "明细值", width = 15)
    @ApiModelProperty(value = "明细值")
    private java.lang.String detailValue;
}
