package com.ljh.controller;

import com.ljh.DTO.CountRegisterDTO;
import com.ljh.DTO.CountTotalDTO;
import com.ljh.DTO.FindUncancelDTO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("/export")
public class ExportController {
    @Autowired
    OrderController orderController;
    @Autowired
    UserController userController;

    @RequestMapping("/excel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response) throws Exception {
        LocalDate orderTime1 = LocalDate.now();
        LocalDate orderTime2 = LocalDate.now().plusMonths(-1);

        CountTotalDTO countTotalDTO = orderController.CountTotal();
        FindUncancelDTO findUncancelDTO = orderController.findUncancel();
        CountRegisterDTO countRegisterDTO = userController.CountRegister();

        int userNum = 0;
        int money = 0 ;
        for (int i = 0; i < 32; i++) {
            Integer num = countRegisterDTO.getNum().get(i);
            userNum += num;
            Integer num2 = countTotalDTO.getMoney().get(i);
            money += num2;
        }

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("static/module.xlsx");

        try (XSSFWorkbook excel = new XSSFWorkbook(in)) {

            XSSFSheet sheet1 = excel.getSheet("Sheet1");

            sheet1.getRow(1).getCell(0).setCellValue("时间：" + orderTime2 + "到" + orderTime1);
            sheet1.getRow(3).getCell(1).setCellValue(money);
            sheet1.getRow(3).getCell(3).setCellValue((double) (findUncancelDTO.getOrderAmount().get(31)) / findUncancelDTO.getAllOrders().get(31));
            sheet1.getRow(3).getCell(5).setCellValue(userNum);
            sheet1.getRow(4).getCell(1).setCellValue(findUncancelDTO.getAllOrders().get(31));
            sheet1.getRow(4).getCell(3).setCellValue((double) money / findUncancelDTO.getOrderAmount().get(31));
            for (int i = 0; i < 32; i++) {
                sheet1.getRow(i + 7).getCell(0).setCellValue(countTotalDTO.getDate().get(i));
                sheet1.getRow(i + 7).getCell(1).setCellValue(countTotalDTO.getMoney().get(i));
                if (i > 0 && !Objects.equals(findUncancelDTO.getAllOrders().get(i), findUncancelDTO.getAllOrders().get(i - 1))) {
                    sheet1.getRow(i + 7).getCell(2).setCellValue((double) ((findUncancelDTO.getOrderAmount().get(i) - findUncancelDTO.getOrderAmount().get(i - 1))) / (findUncancelDTO.getAllOrders().get(i) - findUncancelDTO.getAllOrders().get(i - 1)));
                } else if(i > 0 && Objects.equals(findUncancelDTO.getAllOrders().get(i), findUncancelDTO.getAllOrders().get(i - 1))){
                    sheet1.getRow(i + 7).getCell(2).setCellValue(0);
                } else if(i==0 && findUncancelDTO.getOrderAmount().get(i)==0){
                    sheet1.getRow(i + 7).getCell(2).setCellValue(0);
                }
                sheet1.getRow(i + 7).getCell(3).setCellValue(countRegisterDTO.getNum().get(i));
                if (i > 0) {
                    sheet1.getRow(i + 7).getCell(4).setCellValue(findUncancelDTO.getAllOrders().get(i) - findUncancelDTO.getAllOrders().get(i - 1));
                } else {
                    sheet1.getRow(i + 7).getCell(4).setCellValue(findUncancelDTO.getAllOrders().get(i));
                }
                if (i > 0 && !Objects.equals(findUncancelDTO.getOrderAmount().get(i), findUncancelDTO.getOrderAmount().get(i - 1))) {
                    sheet1.getRow(i + 7).getCell(5).setCellValue((double) countTotalDTO.getMoney().get(i) / (findUncancelDTO.getOrderAmount().get(i) - findUncancelDTO.getOrderAmount().get(i - 1)));
                } else if(i>0 && Objects.equals(findUncancelDTO.getOrderAmount().get(i), findUncancelDTO.getOrderAmount().get(i - 1))) {
                    sheet1.getRow(i + 7).getCell(5).setCellValue(0);
                } else if(i==0 && findUncancelDTO.getOrderAmount().get(i)==0){
                    sheet1.getRow(i + 7).getCell(5).setCellValue(0);
                }else {
                    sheet1.getRow(i + 7).getCell(5).setCellValue((double) countTotalDTO.getMoney().get(i) / findUncancelDTO.getOrderAmount().get(i));
                }
            }

            ServletOutputStream outputStream = response.getOutputStream();
            excel.write(outputStream);

            excel.close();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
