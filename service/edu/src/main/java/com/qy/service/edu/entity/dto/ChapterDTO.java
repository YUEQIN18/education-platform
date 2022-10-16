package com.qy.service.edu.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinyue
 * @create 2022-10-06 01:57:00
 */
@Data
public class ChapterDTO {
    private String id;
    private String title;
    private List<VideoDTO> children = new ArrayList<>();
}
