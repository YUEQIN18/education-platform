package com.qy.service.base.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qinyue
 * @create 2022-10-02 02:12:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduException extends RuntimeException{

    private Integer code;
    private String msg;

}
