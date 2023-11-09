package com.yang.oloj.judge.strategy;

import com.yang.oloj.model.dto.question.JudgeCase;
import com.yang.oloj.judge.codesandbox.model.JudgeInfo;
import com.yang.oloj.model.entity.Question;
import com.yang.oloj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 用于定义在策略中传递的参数
 */

@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;


}
