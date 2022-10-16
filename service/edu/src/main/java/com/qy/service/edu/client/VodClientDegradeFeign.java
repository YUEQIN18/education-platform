package com.qy.service.edu.client;

import com.qy.service.utils.R;
import org.springframework.stereotype.Component;

/**
 * @author qinyue
 * @create 2022-10-08 23:40:00
 */
@Component
public class VodClientDegradeFeign implements VodClient{
    @Override
    public R deleteVideo(String id) {
        return R.error().setMessage("远程调用删除视频出错");
    }
}
