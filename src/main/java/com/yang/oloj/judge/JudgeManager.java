package com.yang.oloj.judge;

import com.yang.oloj.judge.strategy.DefaultJudgeStrategy;
import com.yang.oloj.judge.strategy.JavaLanguageJudgeStrategy;
import com.yang.oloj.judge.strategy.JudgeContext;
import com.yang.oloj.judge.strategy.JudgeStrategy;
import com.yang.oloj.judge.codesandbox.model.JudgeInfo;
import com.yang.oloj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {
    
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)){
            judgeStrategy = new  JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
