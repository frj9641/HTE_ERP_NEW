package org.jeecg.modules.excel.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonMapper {
    //示例方法，每个表应有一个单独的接口
//    void insertExcel(List<Map<String, String>> list);

    /**
     * 采购期初值
     * @param list
     */
    void insertPurchaseQC(List<Map<String, String>> list);

    /**
     * 入库期初值
     * @param list
     */
    void insertRkQC(List<Map<String, String>> list);

    /**
     * @Description: 产出品导入
     * @Param: [list]
     * @return: void
     * @Author: lpf
     * @Date: 2021/12/1 15:20
    **/
    void insertCkProduct(List<Map<String, String>> list);

    /**
     * @Description: 产出品 ——污泥 数据采集主表
     * @Param: [list]
     * @return: void
     * @Author: lpf
     * @Date: 2022/1/10 14:28
    **/
    void insertExcelCkProductDataCollectMain(List<Map<String, String>> list);

    /**
     * @Description: 产出品 ——污泥 数据采集明细表
     * @Param: [list]
     * @return: void
     * @Author: lpf
     * @Date: 2022/1/10 14:28
     **/
    void insertExcelCkProductDataCollectDetail(List<Map<String, String>> list);

    /**
     * @Description: 入库质量数据采集 主表导入
     * @Param: [list]
     * @return: void
     * @Author: lpf
     * @Date: 2022/1/11 8:54
    **/
    void insertExcelRkDataCollectMain(List<Map<String, String>> list);

    /**
     * @Description: 入库质量数据采集 附表导入
     * @Param: [list]
     * @return: void
     * @Author: lpf
     * @Date: 2022/1/11 8:54
     **/
    void insertExcelRkDataCollectDetail(List<Map<String, String>> list);

    /**
     * @Description: 物料出库明细
     * @Param: [list]
     * @return: void
     * @Author: lpf
     * @Date: 2022/1/11 8:54
     **/
    void insertExcelCk(List<Map<String, String>> list);

    /**
     * @Description: 质量数据采集主表
     * @Param: [list]
     * @return: void
     * @Author: lpf
     * @Date: 2022/1/11 8:54
     **/
    void insertExcelWaterDataMain(List<Map<String, String>> list);

    /**
     * @Description: 质量数据采集附表
     * @Param: [list]
     * @return: void
     * @Author: lpf
     * @Date: 2022/1/11 8:54
     **/
    void insertExcelWaterDataDetail(List<Map<String, String>> list);
}
