package com.eas.common.utils;

import com.eas.modules.lesson.entity.Lesson;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;

/**
 * @Author huahonglei
 * @Version 2019/3/19
 */

public class ReadLesson {




    public static void main(String[] args) throws Exception {
        //声明一个lesson的集合来存储此表中的数据
        List<Lesson> lessons=new ArrayList<>();
        //创建一个新的工作簿
        XSSFWorkbook workbook=new XSSFWorkbook("C:\\Users\\lenovo\\Desktop\\test.xlsx");
        //获得第一个表
        XSSFSheet sheet=workbook.getSheetAt(0);

        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        Row row ;
        //int colNum = row.getPhysicalNumberOfCells();
        for (int i = 2; i <= rowNum; i++) {
            //获取表中的一行
            row = sheet.getRow(i);
//            Lesson lesson=new Lesson() ;
//            lesson.setLnum(row.getCell(1).getStringCellValue());
//            lesson.setSubject(row.getCell(2).getStringCellValue());
//            lesson.setLtotalnum((int)row.getCell(3).getNumericCellValue());
//            String gidsStr=row.getCell(7).getStringCellValue();
//            String tname=row.getCell(8).getStringCellValue();
            System.out.print(row.getCell(1).getStringCellValue());
            System.out.print("--");
            System.out.print(row.getCell(2).getStringCellValue());
            System.out.print("--");
            //getFormatCellString(row.getCell(3))
            System.out.print(row.getCell(3).toString());
            System.out.print("--");
            System.out.print(row.getCell(7).getStringCellValue());
            System.out.print("--");
            //课程号
            System.out.print(row.getCell(11).toString());
            System.out.print("--");
            //课程名
            System.out.print(row.getCell(12).toString());
            System.out.print("--");

            System.out.println();
         }
     }





    private static String getFormatCellString(Cell cell) {


        Double cellvalue;
        String value="";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellTypeEnum()) {
                case NUMERIC:
                    cellvalue= cell.getNumericCellValue();
                    value=cellvalue.toString();
                    break;
                case STRING:
                    value=cell.getStringCellValue();
                    break;
                default:
                    value = "";
            }
        } else {
            value = "";
        }
        return value;
    }

}
