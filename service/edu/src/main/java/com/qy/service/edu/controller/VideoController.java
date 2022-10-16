package com.qy.service.edu.controller;


import com.qy.service.base.exception.EduException;
import com.qy.service.edu.client.VodClient;
import com.qy.service.edu.entity.Video;
import com.qy.service.edu.service.VideoService;
import com.qy.service.utils.ExceptionUtils;
import com.qy.service.utils.R;
import com.qy.service.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author qinyue
 * @since 2022-10-05
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable String id){
        Video video = videoService.getById(id);
        return R.ok().setData("video", video);
    }

    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video){
        System.out.println(video.toString());
        videoService.save(video);
        return R.ok();
    }
    // 删除小节的时候,同时把云视频删掉
    @DeleteMapping("delete/{id}")
    @Transactional
    public R deleteVideo(@PathVariable String id){
        //根据小节id查到视频id
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            //远程调用删除云视频
            R res = vodClient.deleteVideo(videoSourceId);
            if(Objects.equals(res.getCode(), ResultCode.ERROR)){
                throw new EduException(20001, "远程调用删除失败");
            }
        }
        //删小节
        videoService.removeById(id);
        return R.ok();
    }

}

