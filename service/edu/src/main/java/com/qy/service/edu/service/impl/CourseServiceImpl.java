package com.qy.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qy.service.base.exception.EduException;
import com.qy.service.edu.entity.Course;
import com.qy.service.edu.entity.CourseDescription;
import com.qy.service.edu.entity.dto.CourseInfo;
import com.qy.service.edu.entity.dto.CoursePublishDTO;
import com.qy.service.edu.entity.frontVO.CourseFrontVo;
import com.qy.service.edu.entity.frontVO.CourseWebVo;
import com.qy.service.edu.mapper.CourseMapper;
import com.qy.service.edu.service.ChapterService;
import com.qy.service.edu.service.CourseDescriptionService;
import com.qy.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.service.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author qinyue
 * @since 2022-10-05
 */
@Service
@Transactional
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Override
    public String save(CourseInfo courseInfo) {
        //向课程表加课程
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo, course);
        int insert = baseMapper.insert(course);
        if(insert <= 0){
            //false
            throw new EduException(20001, "添加课程信息失败");
        }
        String courseID = course.getId();
        //向描述表加描述
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseID);
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionService.save(courseDescription);

        return courseID;
    }

    @Override
    public CourseInfo getCourseInfo(String courseId) {
        Course course = baseMapper.selectById(courseId);
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(course, courseInfo);

        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfo.setDescription(courseDescription.getDescription());

        return courseInfo;
    }

    @Override
    public void updateCourseInfo(CourseInfo courseInfo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo, course);
        int update = baseMapper.updateById(course);
        if(update <= 0){
            throw new EduException(20001, "修改课程信息失败");
        }

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfo.getId());
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionService.updateById(courseDescription);

    }

    @Override
    public CoursePublishDTO getPublishCourseInfo(String id) {
        CoursePublishDTO publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void deleteCourse(String id) {
        //删小节, 删章节, 删描述,删课程本身
        videoService.removeVideoByCourseId(id);
        chapterService.removeChapterByCourseId(id);
        courseDescriptionService.removeById(id);
        int result = baseMapper.deleteById(id);
        if(result == 0 ){
            throw new EduException(20001, "删除课程失败");
        }
    }

    @Override
    @Cacheable(value = "banner", key = "'selectCourse'")
    public List<Course> getIndexCourse() {
        //查询前8条热门课程, 查询前4条名师
        QueryWrapper<Course> wrapper= new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Course> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> pageCourse, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("create_time");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse,wrapper);

        List<Course> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
