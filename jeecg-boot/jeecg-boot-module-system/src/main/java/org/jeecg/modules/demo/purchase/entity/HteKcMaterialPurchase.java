package org.jeecg.modules.demo.purchase.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 采购表
 * @Author: jeecg-boot
 * @Date:   2021-11-05
 * @Version: V1.0
 */
@Data
@TableName("hte_kc_material_purchase")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="hte_kc_material_purchase对象", description="采购表")
public class HteKcMaterialPurchase implements Serializable {
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
	/**单据号*/
	@Excel(name = "单据号", width = 15)
    @ApiModelProperty(value = "单据号")
    private java.lang.String djh;
	/**厂站*/
	@Excel(name = "厂站", width = 15, dictTable = "hte_site", dicText = "site_name", dicCode = "id")
	@Dict(dictTable = "hte_site", dicText = "site_name", dicCode = "id")
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
	/**数量（吨）*/
	@Excel(name = "数量（吨）", width = 15)
    @ApiModelProperty(value = "数量（吨）")
    private java.lang.Double slT;
	/**采购单价*/
	@Excel(name = "采购单价", width = 15)
    @ApiModelProperty(value = "采购单价")
    private java.lang.Double dj;
	/**运费单价*/
	@Excel(name = "运费单价", width = 15)
    @ApiModelProperty(value = "运费单价")
    private java.lang.Double yfdj;
	/**总金额*/
	@Excel(name = "总金额", width = 15)
    @ApiModelProperty(value = "总金额")
    private java.lang.String zje;
	/**采购员*/
	@Excel(name = "采购员", width = 15)
    @ApiModelProperty(value = "采购员")
    private java.lang.String creater;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**审核状态*/
	@Excel(name = "审核状态", width = 15, dicCode = "check_flag")
	@Dict(dicCode = "check_flag")
    @ApiModelProperty(value = "审核状态")
    private java.lang.Integer checkFlag;
	/**审核日期*/
	@Excel(name = "审核日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "审核日期")
    private java.util.Date checkDate;
	/**入库状态*/
	@Excel(name = "入库状态", width = 15, dicCode = "rk_flag")
	@Dict(dicCode = "rk_flag")
    @ApiModelProperty(value = "入库状态")
    private java.lang.Integer rkFlag;
	/**采购方式*/
	@Excel(name = "采购方式", width = 15, dicCode = "purchase_way")
	@Dict(dicCode = "purchase_way")
    @ApiModelProperty(value = "采购方式")
    private java.lang.Integer purchaseWay;
}
