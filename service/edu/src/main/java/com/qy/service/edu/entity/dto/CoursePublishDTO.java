package com.qy.service.edu.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author qinyue
 * @create 2022-10-07 00:21:00
 */
@Data
@ApiModel(value = "课程发布信息")
public class CoursePublishDTO {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
