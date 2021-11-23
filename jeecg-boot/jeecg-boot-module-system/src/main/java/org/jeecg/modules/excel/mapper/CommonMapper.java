package org.jeecg.modules.excel.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonMapper {
    //示例方法，每个表应有一个单独的接口
    void insertExcel(List<Map<String, String>> list);

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
}
