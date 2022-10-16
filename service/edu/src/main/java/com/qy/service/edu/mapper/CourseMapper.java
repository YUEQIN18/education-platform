package com.qy.service.edu.mapper;

import com.qy.service.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.service.edu.entity.dto.CoursePublishDTO;
import com.qy.service.edu.entity.frontVO.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author qinyue
 * @since 2022-10-05
 */
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishDTO getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
