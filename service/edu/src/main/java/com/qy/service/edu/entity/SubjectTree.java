package com.qy.service.edu.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinyue
 * @create 2022-10-05 01:48:00
 */
@Data
public class SubjectTree {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String parentId;

    private List<SubjectTree> children;

    public SubjectTree(){
        this.children = new ArrayList<>();
    }
    public void addChild(SubjectTree child){
        children.add(child);
    }

}
