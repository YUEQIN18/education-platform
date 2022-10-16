package com.qy.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qy.service.base.exception.EduException;
import com.qy.service.edu.entity.Subject;
import com.qy.service.edu.entity.excel.SubjectData;
import com.qy.service.edu.service.SubjectService;

/**
 * @author qinyue
 * @create 2022-10-05 00:02:00
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public SubjectService subjectService;

    public SubjectExcelListener(){}
    public SubjectExcelListener(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //一行一行读取
        if(subjectData == null){
            throw new EduException(20001, "数据文件为空");
        }
        Subject subjectOne = this.checkSubject(subjectService, subjectData.getOneSubjectName(), "0");
        //判断分类是否重复
        if(subjectOne == null){
            Subject subject = new Subject();
            subject.setParentId("0");
            subject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(subject);
        }
        String parentId = getParentID(subjectService, subjectData.getOneSubjectName());
        Subject subjectTwo = this.checkSubject(subjectService, subjectData.getOneSubjectName(), parentId);
        //判断分类是否重复
        if(subjectTwo == null){
            Subject subject = new Subject();
            subject.setParentId(parentId);
            subject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(subject);
        }
    }

    //判断一级分类不能重复添加
    private Subject checkSubject(SubjectService subjectService, String name, String pid){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        Subject subject = subjectService.getOne(wrapper);
        return subject;
    }

    private String getParentID(SubjectService subjectService, String parentTitle){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", parentTitle);
        return subjectService.getOne(wrapper).getId();
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
