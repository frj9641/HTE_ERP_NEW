package org.jeecg.modules.excel;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.*;
import org.jeecg.modules.excel.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导入器增强版
 * 不需要写代码，只需要修改excel。
 * excel需要表头为列导入对应数据表字段名，如果导入发生错误需要根据错误调整表内容，例如表内容为空可能无法识别为空字符串。
 */
@Component
public class ExcelWorkerPlus {

    @Autowired
    private CommonMapper commonMapper;

    /**
     * 导入excel方法
     *
     * @param is        文件输入流
     * @param index     第几个工作簿
     * @param tableName 导入表名
     */
    @Transactional
    public void importExcel(InputStream is, int index, String tableName) {
        long t1 = System.currentTimeMillis();
        Workbook xssfWorkbook = StreamingReader.builder().rowCacheSize(10).bufferSize(8192).open(is);
        if (xssfWorkbook == null) throw new RuntimeException("输入流错误");
        // 默认获取第一个工作簿，可作为参数
        Sheet xssfSheet = xssfWorkbook.getSheetAt(index);
        // 雪花算法id生成器
//        IdWorker worker = new IdWorker(1, 1, 1);
        // 保存数据的列表实体
        List<List<String>> list = new ArrayList<>();
        // 保存表头，表头需要与数据库字段对应
        List<String> title = new ArrayList<>();
        for (Row row : xssfSheet) {
            if (row.getRowNum() == 0) {
                for (Cell cell : row) {
                    title.add(getValue(cell));
                }
                continue;
            }
            List<String> l = new ArrayList<>();
            for (Cell cell : row) {
                l.add(getValue(cell));
            }
            list.add(l);
            // 数量达到指定或者遍历到最后一行则进行batch插入
            if (list.size() == 10000 || row.getRowNum() == xssfSheet.getLastRowNum()) {
                commonMapper.insertExcelPlus(list, title, tableName);
                list.clear();
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println("===>插入" + xssfSheet.getLastRowNum() + " 条， 总计耗时：" + (t2 - t1) / 1000 + "s");
    }

    /**
     * 解析excel单元格内容
     *
     * @param xSSFCell
     * @return
     */
    private static String getValue(Cell xSSFCell) {
        if (xSSFCell == null) {
            return null;
        }
        if (xSSFCell.getCellType() == CellType.BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(xSSFCell.getBooleanCellValue());
        } else if (xSSFCell.getCellType() == CellType.NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(xSSFCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(xSSFCell.getStringCellValue());
        }
    }
}
