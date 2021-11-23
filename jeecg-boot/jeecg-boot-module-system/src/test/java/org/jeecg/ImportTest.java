package org.jeecg;

import org.jeecg.modules.excel.ExcelWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class ImportTest {
    @Autowired
    ExcelWorker excelWorker;

    @Test
    public void indexTest() {
        System.out.println(String.valueOf((char) (1 + (int) 'A')));
    }

    @Test
    public void test() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\p.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0);
    }

    @Test
    public void testRk() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\rk.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0);
    }

    @Test
    public void testCk() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\ck.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0);
    }

    @Test
    public void testTzd() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\tzd.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0);
    }

    @Test
    public void testRkDataCollectMain() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\rk_data_collect_main.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0);
    }

    @Test
    public void testRkDataCollectDetail() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\rk_data_collect_detail.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0);
    }

    @Test
    public void testCkProductDataCollectMain() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\ck_product_data_collect_main.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0);
    }

    @Test
    public void testCkProductDataCollectDetail() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\ck_product_data_collect_detail" +
                ".xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0);
    }

    @Test
    public void testWaterDataCollectMain() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\water_data_collect_main.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0);
    }

    @Test
    public void testWaterDataCollectDetail() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\water_data_collect_detail" +
                ".xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0);
    }

    public void testPurchaseQc() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("D:\\jeecg-boot\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\qc.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcelQC(fileInputStream, 0, "CGQC");
    }

    @Test
    public void testRkQc() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("D:\\jeecg-boot\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\qc.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcelQC(fileInputStream, 0, "RKQC");
    }

    @Test
    public void testFillZero() {
        String str = String.format("%014d", 10);
        System.out.println(str);
        System.out.println(str.length());
    }

}
