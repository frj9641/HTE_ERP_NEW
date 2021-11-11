package org.jeecg.modules.demo.purchase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.purchase.entity.HteKcMaterialPurchase;
import org.jeecg.modules.demo.purchase.service.IHteKcMaterialPurchaseService;
import org.jeecg.modules.util.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 采购表
 * @Author: jeecg-boot
 * @Date: 2021-11-05
 * @Version: V1.0
 */
@Api(tags = "采购表")
@RestController
@RequestMapping("/purchase/hteKcMaterialPurchase")
@Slf4j
public class HteKcMaterialPurchaseController extends JeecgController<HteKcMaterialPurchase, IHteKcMaterialPurchaseService> {
    @Autowired
    private IHteKcMaterialPurchaseService hteKcMaterialPurchaseService;
    @Autowired
    private OrderNoUtil orderNoUtil;

    /**
     * 分页列表查询
     *
     * @param hteKcMaterialPurchase
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "采购表-分页列表查询")
    @ApiOperation(value = "采购表-分页列表查询", notes = "采购表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(HteKcMaterialPurchase hteKcMaterialPurchase,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<HteKcMaterialPurchase> queryWrapper = QueryGenerator.initQueryWrapper(hteKcMaterialPurchase, req.getParameterMap());
        Page<HteKcMaterialPurchase> page = new Page<HteKcMaterialPurchase>(pageNo, pageSize);
        IPage<HteKcMaterialPurchase> pageList = hteKcMaterialPurchaseService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param hteKcMaterialPurchase
     * @return
     */
    @AutoLog(value = "采购表-添加")
    @ApiOperation(value = "采购表-添加", notes = "采购表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody HteKcMaterialPurchase hteKcMaterialPurchase) {
        hteKcMaterialPurchaseService.save(hteKcMaterialPurchase);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param hteKcMaterialPurchase
     * @return
     */
    @AutoLog(value = "采购表-编辑")
    @ApiOperation(value = "采购表-编辑", notes = "采购表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody HteKcMaterialPurchase hteKcMaterialPurchase) {
        hteKcMaterialPurchaseService.updateById(hteKcMaterialPurchase);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "采购表-通过id删除")
    @ApiOperation(value = "采购表-通过id删除", notes = "采购表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        hteKcMaterialPurchaseService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "采购表-批量删除")
    @ApiOperation(value = "采购表-批量删除", notes = "采购表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.hteKcMaterialPurchaseService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "采购表-通过id查询")
    @ApiOperation(value = "采购表-通过id查询", notes = "采购表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        HteKcMaterialPurchase hteKcMaterialPurchase = hteKcMaterialPurchaseService.getById(id);
        if (hteKcMaterialPurchase == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(hteKcMaterialPurchase);
    }

    /**
     * 获取出库单据号
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getCgNo")
    public Result<?> getCgNo(@RequestParam(name = "id", required = true) String id) {
        String orderNo = orderNoUtil.getOrderNo("CG", id);
        return Result.OK(orderNo);
    }

    /**
     * 获取入库单据号
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getRkNo")
    public Result<?> getRkNo(@RequestParam(name = "id", required = true) String id) {
        String orderNo = orderNoUtil.getOrderNo("RK", id);
        return Result.OK(orderNo);
    }

    /**
     * 获取出库单据号
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getCkNo")
    public Result<?> getCkNo(@RequestParam(name = "id", required = true) String id) {
        String orderNo = orderNoUtil.getOrderNo("CK", id);
        return Result.OK(orderNo);
    }

    /**
     * 获取调增单据号
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getTzNo")
    public Result<?> getTzNo(@RequestParam(name = "id", required = true) String id) {
        String orderNo = orderNoUtil.getOrderNo("TZ", id);
        return Result.OK(orderNo);
    }

    /**
     * 获取产品单据号
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getCpNo")
    public Result<?> getCpNo(@RequestParam(name = "id", required = true) String id) {
        String orderNo = orderNoUtil.getOrderNo("CP", id);
        return Result.OK(orderNo);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param hteKcMaterialPurchase
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HteKcMaterialPurchase hteKcMaterialPurchase) {
        return super.exportXls(request, hteKcMaterialPurchase, HteKcMaterialPurchase.class, "采购表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, HteKcMaterialPurchase.class);
    }

}
