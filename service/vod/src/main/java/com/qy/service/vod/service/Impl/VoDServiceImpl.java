package com.qy.service.vod.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.qy.service.vod.service.VoDService;
import com.qy.service.vod.utils.InitVodClient;
import com.qy.service.vod.utils.PropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author qinyue
 * @create 2022-10-07 23:07:00
 */
@Service
public class VoDServiceImpl implements VoDService {
    @Override
    public void deleteVideo(String id) throws ClientException {
        DefaultAcsClient client = InitVodClient.initVodClient(PropertiesUtil.KEY_ID, PropertiesUtil.KEY_SECRET);
        //创建删除视频request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        //向request中设置id
        request.setVideoIds(id);
        //
        client.getAcsResponse(request);
    }

    @Override
    public String uploadVideo(MultipartFile file) {
        try
        {
            String fileName = file.getOriginalFilename(); //原始文件名
            String title = fileName.substring(0, fileName.lastIndexOf(".")); //上传后文件名
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(PropertiesUtil.KEY_ID, PropertiesUtil.KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
