package org.jeecg.modules.demo.materialcktzd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 出库_调整单表
 * @Author: jeecg-boot
 * @Date:   2021-11-11
 * @Version: V1.0
 */
@Data
@TableName("hte_kc_material_ck_tzd")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="hte_kc_material_ck_tzd对象", description="出库_调整单表")
public class HteKcMaterialCkTzd implements Serializable {
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
	/**单据类型*/
	@Excel(name = "单据类型", width = 15, dicCode = "ck_type")
	@Dict(dicCode = "ck_type")
    @ApiModelProperty(value = "单据类型")
    private java.lang.Integer type;
	/**入库单号*/
	@Excel(name = "入库单号", width = 15, dictTable = "hte_kc_material_rk", dicText = "djh_desc", dicCode = "id")
	@Dict(dictTable = "hte_kc_material_rk", dicText = "djh_desc", dicCode = "id")
    @ApiModelProperty(value = "入库单号")
    private java.lang.String rkDjhDesc;
	/**单据号*/
	@Excel(name = "单据号", width = 15)
    @ApiModelProperty(value = "单据号")
    private java.lang.String djh;
	/**厂站*/
	@Excel(name = "厂站", width = 15, dictTable = "sys_depart", dicText = "depart_name", dicCode = "org_code")
	@Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "org_code")
    @ApiModelProperty(value = "厂站")
    private java.lang.String siteId;
	/**物料*/
	@Excel(name = "物料", width = 15, dictTable = "hte_kc_material", dicText = "material", dicCode = "id")
	@Dict(dictTable = "hte_kc_material", dicText = "material", dicCode = "id")
    @ApiModelProperty(value = "物料")
    private java.lang.String materialId;
	/**供应商*/
	@Excel(name = "供应商", width = 15, dictTable = "hte_supply", dicText = "supply_name", dicCode = "id")
	@Dict(dictTable = "hte_supply", dicText = "supply_name", dicCode = "id")
    @ApiModelProperty(value = "供应商")
    private java.lang.String supplyId;
	/**数量(千克)*/
	@Excel(name = "数量(千克)", width = 15)
    @ApiModelProperty(value = "数量(千克)")
    private java.lang.Double slKg;
	/**审核标识*/
	@Excel(name = "审核标识", width = 15, dicCode = "check_flag")
	@Dict(dicCode = "check_flag")
    @ApiModelProperty(value = "审核标识")
    private java.lang.Integer checkFlag;
	/**审核日期*/
	@Excel(name = "审核日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "审核日期")
    private java.util.Date checkDate;
	/**操作员*/
	@Excel(name = "操作员", width = 15)
    @ApiModelProperty(value = "操作员")
    private java.lang.String creater;
}
