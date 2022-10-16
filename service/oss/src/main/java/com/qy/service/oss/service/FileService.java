package com.qy.service.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author qinyue
 * @create 2022-10-04 01:54:00
 */
public interface FileService {
    public String uploadFileAvatar(MultipartFile file);
}
