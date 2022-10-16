package com.qy.service.controller;

import com.qy.service.service.MsmService;
import com.qy.service.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qinyue
 * @create 2022-10-09 22:09:00
 */
@RestController
@RequestMapping("/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @GetMapping("/signup/{email}")
    public R getVerifyCode(@PathVariable String email){
        msmService.verifyCode(email);
        return R.ok();
    }
}
