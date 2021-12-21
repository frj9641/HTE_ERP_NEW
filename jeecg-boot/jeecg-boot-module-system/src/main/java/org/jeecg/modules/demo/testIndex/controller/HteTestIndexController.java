package org.jeecg.modules.demo.testIndex.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.testIndex.entity.HteTestIndex;
import org.jeecg.modules.demo.testIndex.service.IHteTestIndexService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 质量数据索引表
 * @Author: jeecg-boot
 * @Date:   2021-12-03
 * @Version: V1.0
 */
@Api(tags="质量数据索引表")
@RestController
@RequestMapping("/testIndex/hteTestIndex")
@Slf4j
public class HteTestIndexController extends JeecgController<HteTestIndex, IHteTestIndexService> {
	@Autowired
	private IHteTestIndexService hteTestIndexService;
	
	/**
	 * 分页列表查询
	 *
	 * @param hteTestIndex
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "质量数据索引表-分页列表查询")
	@ApiOperation(value="质量数据索引表-分页列表查询", notes="质量数据索引表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(HteTestIndex hteTestIndex,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<HteTestIndex> queryWrapper = QueryGenerator.initQueryWrapper(hteTestIndex, req.getParameterMap());
		Page<HteTestIndex> page = new Page<HteTestIndex>(pageNo, pageSize);
		IPage<HteTestIndex> pageList = hteTestIndexService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param hteTestIndex
	 * @return
	 */
	@AutoLog(value = "质量数据索引表-添加")
	@ApiOperation(value="质量数据索引表-添加", notes="质量数据索引表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody HteTestIndex hteTestIndex) {
		hteTestIndexService.save(hteTestIndex);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param hteTestIndex
	 * @return
	 */
	@AutoLog(value = "质量数据索引表-编辑")
	@ApiOperation(value="质量数据索引表-编辑", notes="质量数据索引表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody HteTestIndex hteTestIndex) {
		hteTestIndexService.updateById(hteTestIndex);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "质量数据索引表-通过id删除")
	@ApiOperation(value="质量数据索引表-通过id删除", notes="质量数据索引表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		hteTestIndexService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "质量数据索引表-批量删除")
	@ApiOperation(value="质量数据索引表-批量删除", notes="质量数据索引表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.hteTestIndexService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "质量数据索引表-通过id查询")
	@ApiOperation(value="质量数据索引表-通过id查询", notes="质量数据索引表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		HteTestIndex hteTestIndex = hteTestIndexService.getById(id);
		if(hteTestIndex==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(hteTestIndex);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param hteTestIndex
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HteTestIndex hteTestIndex) {
        return super.exportXls(request, hteTestIndex, HteTestIndex.class, "质量数据索引表");
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
        return super.importExcel(request, response, HteTestIndex.class);
    }

}
