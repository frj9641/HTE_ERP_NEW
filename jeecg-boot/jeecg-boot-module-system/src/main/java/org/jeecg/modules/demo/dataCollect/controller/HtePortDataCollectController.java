package org.jeecg.modules.demo.dataCollect.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.dataCollect.entity.HtePortDataCollectDetail;
import org.jeecg.modules.demo.dataCollect.entity.HtePortDataCollect;
import org.jeecg.modules.demo.dataCollect.vo.HtePortDataCollectPage;
import org.jeecg.modules.demo.dataCollect.service.IHtePortDataCollectService;
import org.jeecg.modules.demo.dataCollect.service.IHtePortDataCollectDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 质量数据采集概览表
 * @Author: jeecg-boot
 * @Date: 2021-12-02
 * @Version: V1.0
 */
@Api(tags = "质量数据采集概览表")
@RestController
@RequestMapping("/dataCollect/htePortDataCollect")
@Slf4j
public class HtePortDataCollectController {
    @Autowired
    private IHtePortDataCollectService htePortDataCollectService;
    @Autowired
    private IHtePortDataCollectDetailService htePortDataCollectDetailService;

    /**
     * 分页列表查询
     *
     * @param htePortDataCollect
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "质量数据采集概览表-分页列表查询")
    @ApiOperation(value = "质量数据采集概览表-分页列表查询", notes = "质量数据采集概览表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(HtePortDataCollect htePortDataCollect,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<HtePortDataCollect> queryWrapper = QueryGenerator.initQueryWrapper(htePortDataCollect, req.getParameterMap());
        Page<HtePortDataCollect> page = new Page<HtePortDataCollect>(pageNo, pageSize);
        IPage<HtePortDataCollect> pageList = htePortDataCollectService.page(page, queryWrapper);
        // 替换输出为vo
//        List<HtePortDataCollect> records = pageList.getRecords();
//        List<HtePortDataCollectPage> list = new ArrayList<>();
//        for (HtePortDataCollect r : records) {
//            HtePortDataCollectPage htePortDataCollectPage = new HtePortDataCollectPage();
//            BeanUtils.copyProperties(r, htePortDataCollectPage);
//            htePortDataCollectPage.setHtePortDataCollectDetailList(htePortDataCollectDetailService.selectByMainId(r.getId()));
//            list.add(htePortDataCollectPage);
//        }
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param htePortDataCollectPage
     * @return
     */
    @AutoLog(value = "质量数据采集概览表-添加")
    @ApiOperation(value = "质量数据采集概览表-添加", notes = "质量数据采集概览表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody HtePortDataCollectPage htePortDataCollectPage) {
        HtePortDataCollect htePortDataCollect = new HtePortDataCollect();
        BeanUtils.copyProperties(htePortDataCollectPage, htePortDataCollect);
        htePortDataCollectService.saveMain(htePortDataCollect, htePortDataCollectPage.getHtePortDataCollectDetailList());
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param htePortDataCollectPage
     * @return
     */
    @AutoLog(value = "质量数据采集概览表-编辑")
    @ApiOperation(value = "质量数据采集概览表-编辑", notes = "质量数据采集概览表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody HtePortDataCollectPage htePortDataCollectPage) {
        HtePortDataCollect htePortDataCollect = new HtePortDataCollect();
        BeanUtils.copyProperties(htePortDataCollectPage, htePortDataCollect);
        HtePortDataCollect htePortDataCollectEntity = htePortDataCollectService.getById(htePortDataCollect.getId());
        if (htePortDataCollectEntity == null) {
            return Result.error("未找到对应数据");
        }
        htePortDataCollectService.updateMain(htePortDataCollect, htePortDataCollectPage.getHtePortDataCollectDetailList());
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "质量数据采集概览表-通过id删除")
    @ApiOperation(value = "质量数据采集概览表-通过id删除", notes = "质量数据采集概览表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        htePortDataCollectService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "质量数据采集概览表-批量删除")
    @ApiOperation(value = "质量数据采集概览表-批量删除", notes = "质量数据采集概览表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.htePortDataCollectService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "质量数据采集概览表-通过id查询")
    @ApiOperation(value = "质量数据采集概览表-通过id查询", notes = "质量数据采集概览表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        HtePortDataCollect htePortDataCollect = htePortDataCollectService.getById(id);
        if (htePortDataCollect == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(htePortDataCollect);

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "质量数据采集明细表通过主表ID查询")
    @ApiOperation(value = "质量数据采集明细表主表ID查询", notes = "质量数据采集明细表-通主表ID查询")
    @GetMapping(value = "/queryHtePortDataCollectDetailByMainId")
    public Result<?> queryHtePortDataCollectDetailListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<HtePortDataCollectDetail> htePortDataCollectDetailList = htePortDataCollectDetailService.selectByMainId(id);
        return Result.OK(htePortDataCollectDetailList);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param htePortDataCollect
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HtePortDataCollect htePortDataCollect) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<HtePortDataCollect> queryWrapper = QueryGenerator.initQueryWrapper(htePortDataCollect, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //Step.2 获取导出数据
        List<HtePortDataCollect> queryList = htePortDataCollectService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<HtePortDataCollect> htePortDataCollectList = new ArrayList<HtePortDataCollect>();
        if (oConvertUtils.isEmpty(selections)) {
            htePortDataCollectList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            htePortDataCollectList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<HtePortDataCollectPage> pageList = new ArrayList<HtePortDataCollectPage>();
        for (HtePortDataCollect main : htePortDataCollectList) {
            HtePortDataCollectPage vo = new HtePortDataCollectPage();
            BeanUtils.copyProperties(main, vo);
            List<HtePortDataCollectDetail> htePortDataCollectDetailList = htePortDataCollectDetailService.selectByMainId(main.getId());
            vo.setHtePortDataCollectDetailList(htePortDataCollectDetailList);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "质量数据采集概览表列表");
        mv.addObject(NormalExcelConstants.CLASS, HtePortDataCollectPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("质量数据采集概览表数据", "导出人:" + sysUser.getRealname(), "质量数据采集概览表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
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
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<HtePortDataCollectPage> list = ExcelImportUtil.importExcel(file.getInputStream(), HtePortDataCollectPage.class, params);
                for (HtePortDataCollectPage page : list) {
                    HtePortDataCollect po = new HtePortDataCollect();
                    BeanUtils.copyProperties(page, po);
                    htePortDataCollectService.saveMain(po, page.getHtePortDataCollectDetailList());
                }
                return Result.OK("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.OK("文件导入失败！");
    }

}
