package com.qy.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qy.service.edu.client.VodClient;
import com.qy.service.edu.entity.Video;
import com.qy.service.edu.mapper.VideoMapper;
import com.qy.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author qinyue
 * @since 2022-10-05
 */
@Service
@Transactional
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        // 根据课程id查询课程所有视频id
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        // 只需要视频id
        wrapper.select("video_source_id");
        List<Video> videoList = baseMapper.selectList(wrapper);
        // 同时删除云视频
        for (Video video : videoList) {
            if(video.getVideoSourceId() != null){
                vodClient.deleteVideo(video.getVideoSourceId());
            }
        }
        // 再删小节
        QueryWrapper<Video> wrapper2 = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper2);
    }

}

