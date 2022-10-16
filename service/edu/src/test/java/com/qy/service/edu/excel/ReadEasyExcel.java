package com.qy.service.edu.excel;

import com.alibaba.excel.EasyExcel;

/**
 * @author qinyue
 * @create 2022-10-04 23:50:00
 */
public class ReadEasyExcel {
    public static void main(String[] args) {
        //读excel
        String filename = "/Users/qinyue/Downloads/subject.xlsx";
        EasyExcel.read(filename, DemoData.class, new EasyExcelListener()).sheet().doRead();
    }
}
