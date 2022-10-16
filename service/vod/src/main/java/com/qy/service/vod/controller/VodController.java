package com.qy.service.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.qy.service.base.exception.EduException;
import com.qy.service.utils.R;
import com.qy.service.vod.service.VoDService;
import com.qy.service.vod.utils.InitVodClient;
import com.qy.service.vod.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qinyue
 * @create 2022-10-07 23:06:00
 */
@RestController
@CrossOrigin
@RequestMapping("/vod/video")
public class VodController {

    @Autowired
    private VoDService voDService;

    @PostMapping("upload")
    public R uploadVideo(MultipartFile file){
        String videoId = voDService.uploadVideo(file);
        return R.ok().setData("videoId", videoId);
    }

    @DeleteMapping("delete/{id}")
    public R deleteVideo(@PathVariable String id){
        try {
            voDService.deleteVideo(id);
            return R.ok();
        }catch(Exception e){
            e.printStackTrace();
            throw new EduException(20001, "删除视频失败");
        }
    }

    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(PropertiesUtil.KEY_ID, PropertiesUtil.KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().setData("playAuth", playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EduException(20001, "获取视频凭证失败");
        }
    }
}
