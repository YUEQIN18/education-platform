package com.qy.service.edu.controller;


import com.qy.service.edu.entity.Chapter;
import com.qy.service.edu.entity.dto.ChapterDTO;
import com.qy.service.edu.service.ChapterService;
import com.qy.service.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author qinyue
 * @since 2022-10-05
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterDTO> list= chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().setData("list",list);
    }

    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){
        Chapter chapter= chapterService.getById(chapterId);
        return R.ok().setData("chapter", chapter);
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("delete/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        return flag ? R.ok(): R.error();
    }

}

