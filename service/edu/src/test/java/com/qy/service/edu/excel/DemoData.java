package com.qy.service.edu.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author qinyue
 * @create 2022-10-04 23:33:00
 */
@Data
public class DemoData {
    @ExcelProperty(value = "学生姓名", index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生编号", index = 1)
    private String name;
}
