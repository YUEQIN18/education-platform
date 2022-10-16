package com.qy.service.edu.service;

import com.qy.service.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.service.edu.entity.dto.ChapterDTO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author qinyue
 * @since 2022-10-05
 */
public interface ChapterService extends IService<Chapter> {
    public List<ChapterDTO> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
