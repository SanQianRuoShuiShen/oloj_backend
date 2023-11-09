package com.yang.oloj.model.dto.questionsubmit;

import com.yang.oloj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/SanQianRuoShuiShen">三千弱水</a>
 * @from  
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {


    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交状态
     */
    private Integer status;


    /**
     * 题目Id
     */
    private Long questionId;

    /**
     * 用户Id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}