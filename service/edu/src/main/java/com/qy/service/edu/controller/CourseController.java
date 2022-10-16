package com.qy.service.edu.controller;


import com.qy.service.edu.entity.Course;
import com.qy.service.edu.entity.dto.CourseInfo;
import com.qy.service.edu.entity.dto.CoursePublishDTO;
import com.qy.service.edu.service.CourseService;
import com.qy.service.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author qinyue
 * @since 2022-10-05
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    //课程列表 TODO 加上条件查询和分页功能
    @GetMapping("getAllCourse")
    public R getAllCourse(){
        List<Course> list = courseService.list(null);
        return R.ok().setData("list", list);
    }

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfo courseInfo){
        String id = courseService.save(courseInfo);
        return R.ok().setData("CourseId", id);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfo courseInfo = courseService.getCourseInfo(courseId);
        return R.ok().setData("courseInfo", courseInfo);
    }

    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishDTO publishCourse = courseService.getPublishCourseInfo(courseId);
        return R.ok().setData("publishCourse", publishCourse);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfo courseInfo){
        courseService.updateCourseInfo(courseInfo);
        return R.ok();
    }

    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    @DeleteMapping("delete/{id}")
    public R deleteCourse(@PathVariable String id){
        courseService.deleteCourse(id);
        return R.ok();
    }
}

