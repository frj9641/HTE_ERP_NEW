package org.jeecg.modules.demo.material.controller;

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
import org.jeecg.modules.demo.material.entity.HteKcMaterial;
import org.jeecg.modules.demo.material.service.IHteKcMaterialService;

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
 * @Description: 库存原料表
 * @Author: jeecg-boot
 * @Date:   2021-11-29
 * @Version: V1.0
 */
@Api(tags="库存原料表")
@RestController
@RequestMapping("/material/hteKcMaterial")
@Slf4j
public class HteKcMaterialController extends JeecgController<HteKcMaterial, IHteKcMaterialService>{
	@Autowired
	private IHteKcMaterialService hteKcMaterialService;
	
	/**
	 * 分页列表查询
	 *
	 * @param hteKcMaterial
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "库存原料表-分页列表查询")
	@ApiOperation(value="库存原料表-分页列表查询", notes="库存原料表-分页列表查询")
	@GetMapping(value = "/rootList")
	public Result<?> queryPageList(HteKcMaterial hteKcMaterial,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String hasQuery = req.getParameter("hasQuery");
        if(hasQuery != null && "true".equals(hasQuery)){
            QueryWrapper<HteKcMaterial> queryWrapper =  QueryGenerator.initQueryWrapper(hteKcMaterial, req.getParameterMap());
            List<HteKcMaterial> list = hteKcMaterialService.queryTreeListNoPage(queryWrapper);
            IPage<HteKcMaterial> pageList = new Page<>(1, 10, list.size());
            pageList.setRecords(list);
            return Result.OK(pageList);
        }else{
            String parentId = hteKcMaterial.getPid();
            if (oConvertUtils.isEmpty(parentId)) {
                parentId = "0";
            }
            hteKcMaterial.setPid(null);
            QueryWrapper<HteKcMaterial> queryWrapper = QueryGenerator.initQueryWrapper(hteKcMaterial, req.getParameterMap());
            // 使用 eq 防止模糊查询
            queryWrapper.eq("pid", parentId);
            Page<HteKcMaterial> page = new Page<HteKcMaterial>(pageNo, pageSize);
            IPage<HteKcMaterial> pageList = hteKcMaterialService.page(page, queryWrapper);
            return Result.OK(pageList);
        }
	}

	 /**
      * 获取子数据
      * @param hteKcMaterial
      * @param req
      * @return
      */
	@AutoLog(value = "库存原料表-获取子数据")
	@ApiOperation(value="库存原料表-获取子数据", notes="库存原料表-获取子数据")
	@GetMapping(value = "/childList")
	public Result<?> queryPageList(HteKcMaterial hteKcMaterial,HttpServletRequest req) {
		QueryWrapper<HteKcMaterial> queryWrapper = QueryGenerator.initQueryWrapper(hteKcMaterial, req.getParameterMap());
		List<HteKcMaterial> list = hteKcMaterialService.list(queryWrapper);
		IPage<HteKcMaterial> pageList = new Page<>(1, 10, list.size());
        pageList.setRecords(list);
		return Result.OK(pageList);
	}

    /**
      * 批量查询子节点
      * @param parentIds 父ID（多个采用半角逗号分割）
      * @return 返回 IPage
      * @param parentIds
      * @return
      */
	@AutoLog(value = "库存原料表-批量获取子数据")
    @ApiOperation(value="库存原料表-批量获取子数据", notes="库存原料表-批量获取子数据")
    @GetMapping("/getChildListBatch")
    public Result getChildListBatch(@RequestParam("parentIds") String parentIds) {
        try {
            QueryWrapper<HteKcMaterial> queryWrapper = new QueryWrapper<>();
            List<String> parentIdList = Arrays.asList(parentIds.split(","));
            queryWrapper.in("pid", parentIdList);
            List<HteKcMaterial> list = hteKcMaterialService.list(queryWrapper);
            IPage<HteKcMaterial> pageList = new Page<>(1, 10, list.size());
            pageList.setRecords(list);
            return Result.OK(pageList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("批量查询子节点失败：" + e.getMessage());
        }
    }
	
	/**
	 *   添加
	 *
	 * @param hteKcMaterial
	 * @return
	 */
	@AutoLog(value = "库存原料表-添加")
	@ApiOperation(value="库存原料表-添加", notes="库存原料表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody HteKcMaterial hteKcMaterial) {
		hteKcMaterialService.addHteKcMaterial(hteKcMaterial);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param hteKcMaterial
	 * @return
	 */
	@AutoLog(value = "库存原料表-编辑")
	@ApiOperation(value="库存原料表-编辑", notes="库存原料表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody HteKcMaterial hteKcMaterial) {
		hteKcMaterialService.updateHteKcMaterial(hteKcMaterial);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "库存原料表-通过id删除")
	@ApiOperation(value="库存原料表-通过id删除", notes="库存原料表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		hteKcMaterialService.deleteHteKcMaterial(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "库存原料表-批量删除")
	@ApiOperation(value="库存原料表-批量删除", notes="库存原料表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.hteKcMaterialService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "库存原料表-通过id查询")
	@ApiOperation(value="库存原料表-通过id查询", notes="库存原料表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		HteKcMaterial hteKcMaterial = hteKcMaterialService.getById(id);
		if(hteKcMaterial==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(hteKcMaterial);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param hteKcMaterial
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HteKcMaterial hteKcMaterial) {
		return super.exportXls(request, hteKcMaterial, HteKcMaterial.class, "库存原料表");
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
		return super.importExcel(request, response, HteKcMaterial.class);
    }

}
