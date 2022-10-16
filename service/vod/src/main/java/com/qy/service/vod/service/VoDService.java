package com.qy.service.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qinyue
 * @create 2022-10-07 23:07:00
 */
public interface VoDService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String id) throws ClientException;
}
