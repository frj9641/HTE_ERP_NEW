package org.jeecg.modules.demo.materialrk.controller;

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
import org.jeecg.modules.demo.materialrk.entity.HteKcMaterialRk;
import org.jeecg.modules.demo.materialrk.service.IHteKcMaterialRkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 入库业务表
 * @Author: jeecg-boot
 * @Date: 2021-11-10
 * @Version: V1.0
 */
@Api(tags = "入库业务表")
@RestController
@RequestMapping("/materialrk/hteKcMaterialRk")
@Slf4j
public class HteKcMaterialRkController extends JeecgController<HteKcMaterialRk, IHteKcMaterialRkService> {
    @Autowired
    private IHteKcMaterialRkService hteKcMaterialRkService;

    /**
     * 分页列表查询
     *
     * @param hteKcMaterialRk
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "入库业务表-分页列表查询")
    @ApiOperation(value = "入库业务表-分页列表查询", notes = "入库业务表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(HteKcMaterialRk hteKcMaterialRk,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<HteKcMaterialRk> queryWrapper = QueryGenerator.initQueryWrapper(hteKcMaterialRk, req.getParameterMap());
        Page<HteKcMaterialRk> page = new Page<HteKcMaterialRk>(pageNo, pageSize);
        IPage<HteKcMaterialRk> pageList = hteKcMaterialRkService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param hteKcMaterialRk
     * @return
     */
    @AutoLog(value = "入库业务表-添加")
    @ApiOperation(value = "入库业务表-添加", notes = "入库业务表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody HteKcMaterialRk hteKcMaterialRk) {
        hteKcMaterialRkService.save(hteKcMaterialRk);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param hteKcMaterialRk
     * @return
     */
    @AutoLog(value = "入库业务表-编辑")
    @ApiOperation(value = "入库业务表-编辑", notes = "入库业务表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody HteKcMaterialRk hteKcMaterialRk) {
        hteKcMaterialRkService.updateById(hteKcMaterialRk);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "入库业务表-通过id删除")
    @ApiOperation(value = "入库业务表-通过id删除", notes = "入库业务表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        hteKcMaterialRkService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "入库业务表-批量删除")
    @ApiOperation(value = "入库业务表-批量删除", notes = "入库业务表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.hteKcMaterialRkService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "入库业务表-通过id查询")
    @ApiOperation(value = "入库业务表-通过id查询", notes = "入库业务表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        HteKcMaterialRk hteKcMaterialRk = hteKcMaterialRkService.getById(id);
        if (hteKcMaterialRk == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(hteKcMaterialRk);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param hteKcMaterialRk
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HteKcMaterialRk hteKcMaterialRk) {
        return super.exportXls(request, hteKcMaterialRk, HteKcMaterialRk.class, "入库业务表");
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
        return super.importExcel(request, response, HteKcMaterialRk.class);
    }

    @AutoLog(value = "入库单-实时库存-修改")
    @ApiOperation(value = "入库单-实时库存-修改", notes = "入库单-实时库存-修改")
    @GetMapping(value = "/updateKc")
    public Result<?> updateKc(@RequestParam(name = "id", required = true) String id, @RequestParam(name = "kcSlT", required = true) String kcSlT) {
        HteKcMaterialRk hteKcMaterialRk = hteKcMaterialRkService.getById(id);
        hteKcMaterialRk.setKcSlT(Double.parseDouble(kcSlT));
        hteKcMaterialRkService.updateById(hteKcMaterialRk);
        return Result.OK("审核成功！");
    }
}
