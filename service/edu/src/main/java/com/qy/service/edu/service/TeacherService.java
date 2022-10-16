package com.qy.service.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qy.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.service.edu.entity.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qinyue
 * @since 2022-10-01
 */
public interface TeacherService extends IService<Teacher> {

    public void pageQuery(Page<Teacher> page, TeacherQuery query);

    List<Teacher> getIndexTeacher();

    Map<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher);
}
