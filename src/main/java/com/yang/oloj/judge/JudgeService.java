package com.yang.oloj.judge;

import com.yang.oloj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yang.oloj.model.entity.QuestionSubmit;
import com.yang.oloj.model.vo.QuestionSubmitVO;

public interface JudgeService {
    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
