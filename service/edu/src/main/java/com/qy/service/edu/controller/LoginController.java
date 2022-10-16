package com.qy.service.edu.controller;

import com.qy.service.utils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author qinyue
 * @create 2022-10-03 22:18:00
 */
@RestController
@CrossOrigin
@RequestMapping("/edu/user")
public class LoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().setData("token", "admin");
    }

    @GetMapping("info")
    public R info(){

        return R.ok().setData("roles","[admin]").setData("name", "admin").setData("avatar", "https://tupian.qqw21.com/article/UploadPic/2021-1/202111921572761327.jpg");
    }

}
