package com.qy.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qy.service.edu.entity.Teacher;
import com.qy.service.edu.entity.TeacherQuery;
import com.qy.service.edu.mapper.TeacherMapper;
import com.qy.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qinyue
 * @since 2022-10-01
 */
@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher对象
        baseMapper.selectPage(pageTeacher, wrapper);
        List<Teacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();//下一页
        boolean hasPrevious = pageTeacher.hasPrevious();//上一页
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    @Cacheable(value = "banner", key = "'selectTeacher'")
    public List<Teacher> getIndexTeacher() {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<Teacher> list = baseMapper.selectList(wrapper);
        return list;
    }

    public void pageQuery(Page<Teacher> page, TeacherQuery query){
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(query == null){
            baseMapper.selectPage(page, queryWrapper);
            return;
        }
        //多条件组合查询 动态sql
        String name = query.getName();
        Integer level = query.getLevel();
        String begin = query.getBegin();
        String end = query.getEnd();
        //判断条件是否为空
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("create_time", begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("update_time", level);
        }
        queryWrapper.orderByDesc("create_time");
        baseMapper.selectPage(page, queryWrapper);
    }
}
