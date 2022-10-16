package com.qy.service.edu.service;

import com.qy.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.service.edu.entity.SubjectTree;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author qinyue
 * @since 2022-10-04
 */
@Service
public interface SubjectService extends IService<Subject> {
    public void saveSubject(MultipartFile file, SubjectService subjectService);

    public List<SubjectTree> getSubjectTree();
}
