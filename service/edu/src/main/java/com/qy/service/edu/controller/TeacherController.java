package com.qy.service.edu.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qy.service.edu.entity.Teacher;
import com.qy.service.edu.entity.TeacherQuery;
import com.qy.service.edu.service.TeacherService;
import com.qy.service.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.management.Descriptor;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qinyue
 * @since 2022-10-01
 */
@Api(value = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().setData("item", teacher);
    }

    @ApiOperation(value = "查询所有讲师")
    @GetMapping("/findAll")
    public R findAllTeacher(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().setData("items", list);
    }

    @ApiOperation(value = "分页查询讲师")
    @GetMapping("/findList/{current}/{limit}")
    public R findTeacherList(@PathVariable Long current, @PathVariable Long limit){
        Page<Teacher> page = new Page<>(current, limit);
        teacherService.page(page, null);

        long total = page.getTotal();
        List<Teacher> records = page.getRecords();
        return R.ok().setData("total", total).setData("rows", records);
    }

    @ApiOperation(value = "分页条件查询讲师")
    @PostMapping("/findListCondition/{current}/{limit}")
    public R findTeacherList(@PathVariable Long current,
                             @PathVariable Long limit,
                             @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<Teacher> page = new Page<>(current, limit);
        teacherService.pageQuery(page, teacherQuery);

        long total = page.getTotal();
        List<Teacher> records = page.getRecords();
        return R.ok().setData("total", total).setData("rows", records);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){
        boolean saved = teacherService.save(teacher);
        return saved ? R.ok() : R.error();

    }

    @ApiOperation(value = "修改讲师")
    @PostMapping("/updateTeacher")
    public R updateById(@RequestBody Teacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        return flag ? R.ok() : R.error();
    }

    @ApiOperation(value = "删除单个讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

}

