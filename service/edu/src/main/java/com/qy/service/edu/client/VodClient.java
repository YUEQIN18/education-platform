package com.qy.service.edu.client;

import com.qy.service.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author qinyue
 * @create 2022-10-08 17:45:00
 */
@Component
@FeignClient(value = "service-vod", fallback = VodClientDegradeFeign.class)
public interface VodClient {
    //方法的路径
    //@PathVariable注解一定要指定参数名称，否则出错
    @DeleteMapping("/vod/video/delete/{id}")
    R deleteVideo(@PathVariable("id") String id);
}
