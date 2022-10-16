package com.qy.service.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qy.service.edu.entity.Course;
import com.qy.service.edu.entity.Teacher;
import com.qy.service.edu.service.CourseService;
import com.qy.service.edu.service.TeacherService;
import com.qy.service.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qinyue
 * @create 2022-10-09 02:44:00
 */
@RestController
@CrossOrigin
@RequestMapping("/edu/front")
public class FrontDataController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("index")
    public R index(){
        //查询前8条热门课程, 查询前4条名师
        List<Course> list = courseService.getIndexCourse();
        List<Teacher> list2 = teacherService.getIndexTeacher();
        return R.ok().setData("eduList", list).setData("teacherList", list2);
    }
}
