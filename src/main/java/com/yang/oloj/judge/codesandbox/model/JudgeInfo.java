package com.yang.oloj.judge.codesandbox.model;

import lombok.Data;

/**
 * 判题信息
 *
 */
@Data
public class JudgeInfo {

//    {
//        "message":程序执行信息,
//            "time":1000,
//            "memory":1000
//    }
    /**
     * 程序执行信息
     */
    private String message;
    /**
     * 消耗内存（kb）
     */
    private Long memory;
    /**
     * 消耗时间（ms）
     */
    private Long time;
}
