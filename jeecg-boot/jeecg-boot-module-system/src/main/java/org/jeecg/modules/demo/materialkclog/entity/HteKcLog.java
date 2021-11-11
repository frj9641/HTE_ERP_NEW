package org.jeecg.modules.demo.materialkclog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 入库单库存历史日志
 * @Author: jeecg-boot
 * @Date: 2021-11-11
 * @Version: V1.0
 */
@Data
@TableName("hte_kc_log")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "hte_kc_log对象", description = "入库单库存历史日志")
public class HteKcLog implements Serializable {
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
     * 入库单号
     */
    @Excel(name = "入库单号", width = 15)
    @ApiModelProperty(value = "入库单号")
    private java.lang.String rkDjh;
    /**
     * 操作数量(吨)
     */
    @Excel(name = "操作数量(吨)", width = 15)
    @ApiModelProperty(value = "操作数量(吨)")
    private java.lang.Double slT;
    /**
     * 库存数量(吨)
     */
    @Excel(name = "库存数量(吨)", width = 15)
    @ApiModelProperty(value = "库存数量(吨)")
    private java.lang.Double kcSlT;
    /**
     * 相关单据号
     */
    @Excel(name = "相关单据号", width = 15)
    @ApiModelProperty(value = "相关单据号")
    private java.lang.String relatedDjh;
}
