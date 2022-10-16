package com.qy.service.oss.controller;

import com.qy.service.oss.service.FileService;
import com.qy.service.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qinyue
 * @create 2022-10-04 01:54:00
 */
@RestController
@CrossOrigin
@RequestMapping("/oss/file")
public class FileController {

    @Autowired
    private FileService ossService;

    @PostMapping("/upload")
    public R uploadOss(MultipartFile file){
        //返回文件的oss路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().setData("url", url);
    }
}
