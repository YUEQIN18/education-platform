package com.qy.service.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qy.service.edu.entity.Course;
import com.qy.service.edu.entity.dto.ChapterDTO;
import com.qy.service.edu.entity.frontVO.CourseFrontVo;
import com.qy.service.edu.entity.frontVO.CourseWebVo;
import com.qy.service.edu.service.ChapterService;
import com.qy.service.edu.service.CourseService;
import com.qy.service.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author qinyue
 * @create 2022-10-11 20:50:00
 */
@RestController
@RequestMapping("/edu/coursefront")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    //1 条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<Course> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().setData(map);
    }

    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterDTO> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().setData("courseWebVo",courseWebVo)
                .setData("chapterVideoList",chapterVideoList);
    }
}
