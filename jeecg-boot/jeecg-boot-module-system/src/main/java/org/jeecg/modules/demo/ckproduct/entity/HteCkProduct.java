package org.jeecg.modules.demo.ckproduct.entity;

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
 * @Description: 产出品业务表
 * @Author: jeecg-boot
 * @Date: 2021-11-10
 * @Version: V1.0
 */
@Data
@TableName("hte_ck_product")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "hte_ck_product对象", description = "产出品业务表")
public class HteCkProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**
     * 单据号
     */
    @Excel(name = "单据号", width = 15)
    @ApiModelProperty(value = "单据号")
    private java.lang.String djh;
    /**
     * 厂站
     */
    @Excel(name = "厂站", width = 15, dictTable = "sys_depart", dicText = "depart_name", dicCode = "org_code")
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "org_code")
    @ApiModelProperty(value = "厂站")
    private java.lang.String siteId;
    /**
     * 产出品
     */
    @Excel(name = "产出品", width = 15, dictTable = "hte_product", dicText = "product_name", dicCode = "id")
    @Dict(dictTable = "hte_product", dicText = "product_name", dicCode = "id")
    @ApiModelProperty(value = "产出品")
    private java.lang.String productId;
    /**
     * 采样口
     */
    @Excel(name = "采样口", width = 15, dictTable = "hte_collect_point", dicText = "collect_point", dicCode = "id")
    @Dict(dictTable = "hte_collect_point", dicText = "collect_point", dicCode = "id")
    @ApiModelProperty(value = "采样口")
    private java.lang.String collectId;
    /**
     * 数量（吨）
     */
    @Excel(name = "数量（吨）", width = 15)
    @ApiModelProperty(value = "数量（吨）")
    private java.lang.Double slT;
    /**
     * 调整日期
     */
    @Excel(name = "调整日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "调整日期")
    private java.util.Date tzDate;
    /**
     * 备注
     */
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
    /**
     * 审核状态
     */
    @Excel(name = "审核状态", width = 15, dicCode = "check_flag")
    @Dict(dicCode = "check_flag")
    @ApiModelProperty(value = "审核状态")
    private java.lang.Integer checkFlag;
    /**
     * 审核日期
     */
    @Excel(name = "审核日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "审核日期")
    private java.util.Date checkDate;
    /**
     * 类型
     */
    @Excel(name = "类型", width = 15, dicCode = "produce_type")
    @Dict(dicCode = "produce_type")
    @ApiModelProperty(value = "类型")
    private java.lang.Integer type;
    /**
     * 创建人
     */
    @Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private java.lang.String creater;
    /**
     * 厂站名称
     */
    @Excel(name = "厂站名称", width = 15)
    @ApiModelProperty(value = "厂站名称")
    private java.lang.String departName;
    /**
     * 创建人
     */
    @Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private java.lang.String productName;
    /**
     * 创建人
     */
    @Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private java.lang.String collectPoint;


}
