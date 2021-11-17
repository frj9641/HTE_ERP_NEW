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
    public void test() throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream("E:\\IdeaProject\\hte_erp_new\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\java\\org\\jeecg\\modules\\excel\\p.xlsx");
        System.out.println(excelWorker);
        excelWorker.importExcel(fileInputStream, 0, 1);
    }


}
