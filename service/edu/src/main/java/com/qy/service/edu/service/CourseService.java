package com.qy.service.edu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qy.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.service.edu.entity.dto.CourseInfo;
import com.qy.service.edu.entity.dto.CoursePublishDTO;
import com.qy.service.edu.entity.frontVO.CourseFrontVo;
import com.qy.service.edu.entity.frontVO.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author qinyue
 * @since 2022-10-05
 */
public interface CourseService extends IService<Course> {
    String save(CourseInfo courseINfo);
    CourseInfo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfo courseInfo);

    CoursePublishDTO getPublishCourseInfo(String id);

    void deleteCourse(String id);

    List<Course> getIndexCourse();

    Map<String, Object> getCourseFrontList(Page<Course> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
