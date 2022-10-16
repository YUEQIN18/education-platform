package com.qy.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qy.service.edu.entity.Subject;
import com.qy.service.edu.entity.SubjectTree;
import com.qy.service.edu.entity.excel.SubjectData;
import com.qy.service.edu.listener.SubjectExcelListener;
import com.qy.service.edu.mapper.SubjectMapper;
import com.qy.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author qinyue
 * @since 2022-10-04
 */
@Service
@Transactional
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<SubjectTree> getSubjectTree() {
        List<Subject> subjectList = baseMapper.selectList(new QueryWrapper<>());
        //转换成subject data
        List<SubjectTree> subjectTrees = new ArrayList<>();
        for(Subject subject: subjectList){
            SubjectTree subjectTree = new SubjectTree();
            BeanUtils.copyProperties(subject, subjectTree);
            subjectTrees.add(subjectTree);
        }
        //构建缓存
        Map<String, SubjectTree> cache = subjectTrees.stream().collect(Collectors.toMap(SubjectTree::getId, Function.identity()));
        //result
        List<SubjectTree> res = new LinkedList<>();
        for(SubjectTree s: subjectTrees){
            SubjectTree parent = cache.get(s.getParentId());
            if(Objects.isNull(parent)){
                //s 是 parent
                res.add(s);
            }else{
                // s 是 child
                parent.addChild(s);
            }
        }

        return res;
    }
}
