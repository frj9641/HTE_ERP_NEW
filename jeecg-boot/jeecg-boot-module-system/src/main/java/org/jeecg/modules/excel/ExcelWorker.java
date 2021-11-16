package org.jeecg.modules.excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.modules.excel.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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
     * @param is       excel文件输入流
     * @param index    第几个工作簿，默认为0
     * @param firstCol 第几行开始解析，有表头的默认为1
     */
    public void importExcel(InputStream is, int index, int firstCol) {
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (xssfWorkbook == null) throw new RuntimeException("输入流错误");
        // 默认获取第一个工作部，可作为参数
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(index);
        // 雪花算法id生成器
        IdWorker worker = new IdWorker(1, 1, 1);
        // 除表头外的第一行，可作为参数
        for (int r = firstCol; r < xssfSheet.getLastRowNum(); r++) {
            XSSFRow row = xssfSheet.getRow(r);
            Map<String, String> map = new HashMap<>();
            if (row != null) {
                map.put("id", String.valueOf(worker.nextId()));
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    map.put(String.valueOf(i), getValue(row.getCell(i)));
                }
            }
            commonMapper.insertExcel(map);
        }

    }

    /**
     * 解析excel单元格内容
     *
     * @param xSSFCell
     * @return
     */
    private static String getValue(XSSFCell xSSFCell) {
        if (xSSFCell == null) {
            return "";
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
