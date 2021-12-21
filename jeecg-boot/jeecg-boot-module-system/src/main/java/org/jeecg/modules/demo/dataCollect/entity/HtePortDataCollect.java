package org.jeecg.modules.demo.dataCollect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 质量数据采集概览表
 * @Author: jeecg-boot
 * @Date:   2021-12-02
 * @Version: V1.0
 */
@ApiModel(value="hte_port_data_collect对象", description="质量数据采集概览表")
@Data
@TableName("hte_port_data_collect")
public class HtePortDataCollect implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**厂站*/
	@Excel(name = "厂站", width = 15, dictTable = "sys_depart", dicText = "depart_name", dicCode = "org_code")
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "org_code")
    @ApiModelProperty(value = "厂站")
    private String siteId;
	/**采样日期*/
	@Excel(name = "采样日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "采样日期")
    private Date collectDate;
	/**采样时间*/
	@Excel(name = "采样时间", width = 15)
    @ApiModelProperty(value = "采样时间")
    private String collectTime;
	/**审核状态*/
	@Excel(name = "审核状态", width = 15, dicCode = "check_flag")
    @Dict(dicCode = "check_flag")
    @ApiModelProperty(value = "审核状态")
    private Integer checkFlag;
	/**审核日期*/
	@Excel(name = "审核日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "审核日期")
    private Date checkDate;
	/**操作员*/
	@Excel(name = "操作员", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "id")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "id")
    @ApiModelProperty(value = "操作员")
    private String creater;
}
