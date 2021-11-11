package org.jeecg.modules.demo.ckproduct.controller;

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
import org.jeecg.modules.demo.ckproduct.entity.HteCkProduct;
import org.jeecg.modules.demo.ckproduct.service.IHteCkProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 产出品业务表
 * @Author: jeecg-boot
 * @Date: 2021-11-10
 * @Version: V1.0
 */
@Api(tags = "产出品业务表")
@RestController
@RequestMapping("/ckproduct/hteCkProduct")
@Slf4j
public class HteCkProductController extends JeecgController<HteCkProduct, IHteCkProductService> {
    @Autowired
    private IHteCkProductService hteCkProductService;

    /**
     * 分页列表查询
     *
     * @param hteCkProduct
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "产出品业务表-分页列表查询")
    @ApiOperation(value = "产出品业务表-分页列表查询", notes = "产出品业务表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(HteCkProduct hteCkProduct,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<HteCkProduct> queryWrapper = QueryGenerator.initQueryWrapper(hteCkProduct, req.getParameterMap());
        Page<HteCkProduct> page = new Page<HteCkProduct>(pageNo, pageSize);
        IPage<HteCkProduct> pageList = hteCkProductService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param hteCkProduct
     * @return
     */
    @AutoLog(value = "产出品业务表-添加")
    @ApiOperation(value = "产出品业务表-添加", notes = "产出品业务表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody HteCkProduct hteCkProduct) {
        hteCkProductService.save(hteCkProduct);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param hteCkProduct
     * @return
     */
    @AutoLog(value = "产出品业务表-编辑")
    @ApiOperation(value = "产出品业务表-编辑", notes = "产出品业务表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody HteCkProduct hteCkProduct) {
        hteCkProductService.updateById(hteCkProduct);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "产出品业务表-通过id删除")
    @ApiOperation(value = "产出品业务表-通过id删除", notes = "产出品业务表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        hteCkProductService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "产出品业务表-批量删除")
    @ApiOperation(value = "产出品业务表-批量删除", notes = "产出品业务表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.hteCkProductService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "产出品业务表-通过id查询")
    @ApiOperation(value = "产出品业务表-通过id查询", notes = "产出品业务表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        HteCkProduct hteCkProduct = hteCkProductService.getById(id);
        if (hteCkProduct == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(hteCkProduct);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param hteCkProduct
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HteCkProduct hteCkProduct) {
        return super.exportXls(request, hteCkProduct, HteCkProduct.class, "产出品业务表");
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
        return super.importExcel(request, response, HteCkProduct.class);
    }

}
