package org.jeecg;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.modules.excelImort.IdWorker;
import org.jeecg.modules.excelImort.entity.PurchaseEntity;
import org.jeecg.modules.excelImort.mapper.PurchaseMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;

public class PurchaseImport {
    @Autowired
    private PurchaseMapper purchaseMapper;

    @Test
    public void importExcel() throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("D:\\jeecg-boot\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excelImort\\p.xlsx"));
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        IdWorker worker = new IdWorker(1, 1, 1);
        for (int r = 2; r < xssfSheet.getLastRowNum(); r++) {
            XSSFRow row = xssfSheet.getRow(r);
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            if (row != null) {
                purchaseEntity.setId(String.valueOf(worker.nextId()));
                purchaseEntity.setDjh(getValue(row.getCell(1)));
                purchaseEntity.setSite(getValue(row.getCell(2)));
                purchaseEntity.setMaterial(getValue(row.getCell(3)));
                purchaseEntity.setSupply(getValue(row.getCell(4)));
                purchaseEntity.setSlT(getValue(row.getCell(5)));
                purchaseEntity.setCgdj(getValue(row.getCell(6)));
                purchaseEntity.setYfdj(getValue(row.getCell(7)));
                purchaseEntity.setZje(getValue(row.getCell(8)));
                purchaseEntity.setPurchaseDate(getValue(row.getCell(9)));
                purchaseEntity.setCreater(getValue(row.getCell(10)));
                purchaseEntity.setRemark(getValue(row.getCell(11)));
                purchaseEntity.setCheckFlag(getValue(row.getCell(12)));
                purchaseEntity.setSysDate(getValue(row.getCell(13)));
                purchaseEntity.setCheckDate(getValue(row.getCell(14)));
                purchaseEntity.setRkFlag(getValue(row.getCell(15)));
                purchaseEntity.setPurchaseWay(getValue(row.getCell(16)));
            }
            purchaseMapper.myInsert(purchaseEntity);
        }

    }

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
