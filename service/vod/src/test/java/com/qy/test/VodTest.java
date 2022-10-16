package com.qy.test;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.apache.logging.log4j.util.PropertiesUtil;

import java.util.List;

/**
 * @author qinyue
 * @create 2022-10-07 18:33:00
 */
public class VodTest {
    public static void main(String[] args) throws ClientException {
        String accessKeyId = "LTAI5tSZf3V1kVbAgSqUftus";
        String accessKeySecret = "JqQl9RYRrrB0qO048HJwX2g3al1wOz";
        String title = ""; //上传后文件名
        String fileName = ""; //本地路径和文件名
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
    public static void getPlayAuth() throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = InitVod.initVodClient("LTAI5tSZf3V1kVbAgSqUftus", "JqQl9RYRrrB0qO048HJwX2g3al1wOz");
        //创建获取视频凭证request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //向request对象里设置视频id
        request.setVideoId("e54d6075c114427da0dba56123bc3928");
        //调用初始化对象里的方法, 获取数据
        response = client.getAcsResponse(request);
        //获取视频凭证
        System.out.println("视频凭证: "+response.getPlayAuth());
    }
    public static void getPlayUrl() throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitVod.initVodClient("LTAI5tSZf3V1kVbAgSqUftus", "JqQl9RYRrrB0qO048HJwX2g3al1wOz");
        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象里设置视频id
        request.setVideoId("e54d6075c114427da0dba56123bc3928");
        //调用初始化对象里的方法, 获取数据
        response = client.getAcsResponse(request);
        //播放地址(非加密视频)
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
