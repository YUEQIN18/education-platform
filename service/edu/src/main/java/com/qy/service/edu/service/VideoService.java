package com.qy.service.edu.service;

import com.qy.service.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author qinyue
 * @since 2022-10-05
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(String courseId);

}
