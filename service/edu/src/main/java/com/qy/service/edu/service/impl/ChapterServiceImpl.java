package com.qy.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qy.service.base.exception.EduException;
import com.qy.service.edu.entity.Chapter;
import com.qy.service.edu.entity.Video;
import com.qy.service.edu.entity.dto.ChapterDTO;
import com.qy.service.edu.entity.dto.VideoDTO;
import com.qy.service.edu.mapper.ChapterMapper;
import com.qy.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.service.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author qinyue
 * @since 2022-10-05
 */
@Service
@Transactional
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;
    @Override
    public List<ChapterDTO> getChapterVideoByCourseId(String courseId) {
        //根据课程id查询所有的章节, 再查询所有的小节
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapper);

        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<Video> videoList = videoService.list(videoWrapper);

        List<ChapterDTO> res = new LinkedList<>();

        for(Chapter c: chapterList){
            ChapterDTO chapterDTO = new ChapterDTO();
            BeanUtils.copyProperties(c, chapterDTO);
            res.add(chapterDTO);
            List<VideoDTO> videoDTOList = new LinkedList<>();
            for(Video v : videoList){
                String chapterID = c.getId();
                if(chapterID.equals(v.getChapterId())){
                    VideoDTO videoDTO = new VideoDTO();
                    BeanUtils.copyProperties(v, videoDTO);
                    videoDTOList.add(videoDTO);
                }
            }
            chapterDTO.setChildren(videoDTOList);
        }
        return res;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterId 查小节 video表, 能查出来 就报错 不让删
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        if(count > 0){
            throw new EduException(20001, "删除章节失败, 先删除小节");
        }else{
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
