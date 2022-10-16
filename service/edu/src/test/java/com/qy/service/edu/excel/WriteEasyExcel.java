package com.qy.service.edu.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinyue
 * @create 2022-10-04 23:34:00
 */
public class WriteEasyExcel {
    public static void main(String[] args) {
        //写excel
        String filename = "/Users/qinyue/Downloads/subject.xlsx";
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getData());

    }
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setName("lucy"+i);
            list.add(data);
        }
        return list;
    }
}
