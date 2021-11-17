package org.jeecg.modules.excel;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.modules.excel.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导入器
 */
@Component
public class ExcelWorker {

    @Autowired
    private CommonMapper commonMapper;

    /**
     * 导入excel方法
     *
     * @param is    excel文件输入流
     * @param index 第几个工作簿，默认为0
     */
    @Transactional
    public void importExcel(InputStream is, int index) {
        long t1 = System.currentTimeMillis();
        Workbook xssfWorkbook = StreamingReader.builder().rowCacheSize(10).bufferSize(8192).open(is);
        if (xssfWorkbook == null) throw new RuntimeException("输入流错误");
        // 默认获取第一个工作部，可作为参数
        Sheet xssfSheet = xssfWorkbook.getSheetAt(index);
        // 雪花算法id生成器
        IdWorker worker = new IdWorker(1, 1, 1);
        List<Map<String, String>> list = new ArrayList<>();
        // 除表头外的第一行，可作为参数
        for (Row row : xssfSheet) {
            if (row.getRowNum() == 0) continue;
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(worker.nextId()));
            for (Cell cell : row) {
                map.put(String.valueOf((char) (cell.getColumnIndex() + (int) 'A')), getValue(cell));
            }
            list.add(map);
            // 数量达到指定或者遍历到最后一行则进行batch插入
            if (list.size() == 100000 || row.getRowNum() == xssfSheet.getLastRowNum() - 1) {
                commonMapper.insertExcel(list);
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
