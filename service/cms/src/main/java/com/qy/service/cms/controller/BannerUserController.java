package com.qy.service.cms.controller;

import com.qy.service.cms.entity.CrmBanner;
import com.qy.service.cms.service.CrmBannerService;
import com.qy.service.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qinyue
 * @create 2022-10-09 02:26:00
 */
@RestController
@RequestMapping("/cms/bannerUser")
@CrossOrigin
public class BannerUserController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().setData("list", list);
    }
}
