package com.qy.service.oss.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.qy.service.oss.service.FileService;
import com.qy.service.oss.utils.PropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author qinyue
 * @create 2022-10-04 01:54:00
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = PropertiesUtil.END_POINT;
        String accessKeyId = PropertiesUtil.KEY_ID;
        String accessKeySecret = PropertiesUtil.KEY_SECRET;
        String bucketName = PropertiesUtil.BUCKET_NAME;
        String uploadUrl = null;
        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            //文件上传至阿里云
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + file.getOriginalFilename();
            //把文件按日期进行分类 2022/2/01
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;
            //传入后阿里云会新建日期的路径
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //获取url地址
            uploadUrl = "https://" + bucketName + "." + endPoint + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uploadUrl;
    }
}
