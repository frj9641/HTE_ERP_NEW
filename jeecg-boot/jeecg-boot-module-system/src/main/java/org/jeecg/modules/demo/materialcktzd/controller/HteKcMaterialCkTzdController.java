package org.jeecg.modules.demo.materialcktzd.controller;

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
import org.jeecg.modules.demo.materialcktzd.entity.HteKcMaterialCkTzd;
import org.jeecg.modules.demo.materialcktzd.service.IHteKcMaterialCkTzdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 出库_调整单表
 * @Author: jeecg-boot
 * @Date: 2021-11-11
 * @Version: V1.0
 */
@Api(tags = "出库_调整单表")
@RestController
@RequestMapping("/materialcktzd/hteKcMaterialCkTzd")
@Slf4j
public class HteKcMaterialCkTzdController extends JeecgController<HteKcMaterialCkTzd, IHteKcMaterialCkTzdService> {
    @Autowired
    private IHteKcMaterialCkTzdService hteKcMaterialCkTzdService;

    /**
     * 分页列表查询
     *
     * @param hteKcMaterialCkTzd
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "出库_调整单表-分页列表查询")
    @ApiOperation(value = "出库_调整单表-分页列表查询", notes = "出库_调整单表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(HteKcMaterialCkTzd hteKcMaterialCkTzd,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<HteKcMaterialCkTzd> queryWrapper = QueryGenerator.initQueryWrapper(hteKcMaterialCkTzd, req.getParameterMap());
        Page<HteKcMaterialCkTzd> page = new Page<HteKcMaterialCkTzd>(pageNo, pageSize);
        IPage<HteKcMaterialCkTzd> pageList = hteKcMaterialCkTzdService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param hteKcMaterialCkTzd
     * @return
     */
    @AutoLog(value = "出库_调整单表-添加")
    @ApiOperation(value = "出库_调整单表-添加", notes = "出库_调整单表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody HteKcMaterialCkTzd hteKcMaterialCkTzd) {
        hteKcMaterialCkTzdService.save(hteKcMaterialCkTzd);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param hteKcMaterialCkTzd
     * @return
     */
    @AutoLog(value = "出库_调整单表-编辑")
    @ApiOperation(value = "出库_调整单表-编辑", notes = "出库_调整单表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody HteKcMaterialCkTzd hteKcMaterialCkTzd) {
        hteKcMaterialCkTzdService.updateById(hteKcMaterialCkTzd);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "出库_调整单表-通过id删除")
    @ApiOperation(value = "出库_调整单表-通过id删除", notes = "出库_调整单表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        hteKcMaterialCkTzdService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "出库_调整单表-批量删除")
    @ApiOperation(value = "出库_调整单表-批量删除", notes = "出库_调整单表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.hteKcMaterialCkTzdService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "出库_调整单表-通过id查询")
    @ApiOperation(value = "出库_调整单表-通过id查询", notes = "出库_调整单表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        HteKcMaterialCkTzd hteKcMaterialCkTzd = hteKcMaterialCkTzdService.getById(id);
        if (hteKcMaterialCkTzd == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(hteKcMaterialCkTzd);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param hteKcMaterialCkTzd
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HteKcMaterialCkTzd hteKcMaterialCkTzd) {
        return super.exportXls(request, hteKcMaterialCkTzd, HteKcMaterialCkTzd.class, "出库_调整单表");
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
        return super.importExcel(request, response, HteKcMaterialCkTzd.class);
    }

    @AutoLog(value = "出库_调整单表-审核")
    @ApiOperation(value = "出库_调整单表-审核", notes = "出库_调整单表-审核")
    @GetMapping(value = "/check")
    public Result<?> check(@RequestParam(name = "id", required = true) String id) {
        HteKcMaterialCkTzd hteKcMaterialCkTzd = hteKcMaterialCkTzdService.getById(id);
        hteKcMaterialCkTzd.setCheckFlag(2);
        hteKcMaterialCkTzdService.updateById(hteKcMaterialCkTzd);
        return Result.OK("审核成功！");
    }

}
