package com.qy.service.edu.controller;


import com.qy.service.edu.entity.SubjectTree;
import com.qy.service.edu.service.SubjectService;
import com.qy.service.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author qinyue
 * @since 2022-10-04
 */
@RestController
@CrossOrigin
@RequestMapping("/edu/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    //添加课程分类 获取上传的文件,把文件内容读取出来
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    //课程分类列表显示, 树形结构
    @GetMapping("/getAll")
    public R getAllSubject(){
        List<SubjectTree> list = subjectService.getSubjectTree();
        return R.ok().setData("list", list);
    }
}

