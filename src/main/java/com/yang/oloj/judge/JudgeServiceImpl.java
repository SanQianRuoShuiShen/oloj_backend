package com.yang.oloj.judge;

import cn.hutool.json.JSONUtil;
import com.yang.oloj.common.ErrorCode;
import com.yang.oloj.exception.BusinessException;
import com.yang.oloj.judge.codesandbox.CodeSandbox;
import com.yang.oloj.judge.codesandbox.CodeSandboxFactory;
import com.yang.oloj.judge.codesandbox.CodeSandboxProxy;
import com.yang.oloj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yang.oloj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yang.oloj.judge.strategy.JudgeContext;
import com.yang.oloj.model.dto.question.JudgeCase;
import com.yang.oloj.judge.codesandbox.model.JudgeInfo;
import com.yang.oloj.model.entity.Question;
import com.yang.oloj.model.entity.QuestionSubmit;
import com.yang.oloj.model.enums.QuestionSubmitLanguageEnum;
import com.yang.oloj.model.enums.QuestionSubmitStatusEnum;
import com.yang.oloj.service.QuestionService;
import com.yang.oloj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codesandbox.type:example}")
    private String type;
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private JudgeManager judgeManager;
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //判题服务业务流程
        //1)传入题目的提交id，获取对应的题目、提交信息（代码、编程语言等等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        //2)如果提交题目状态不为等待中，就不用重复执行了
        if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目正在判题中");
        };


        //3)更改判题的状态为"判题中"防止重复执行，也让用户看到
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"判题状态更新失败");
        }
        //4)调用沙箱，获取执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String code = questionSubmit.getCode();
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        //获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr,JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        List<String> outputList = executeCodeResponse.getOutputList();
        //5)根据执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        //修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目状态更新失败");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
        //判断逻辑 1.判断沙箱执行结果数量和预期结果数量是否相等
//        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.WAITING;
//        if (outputList.size() != inputList.size()){
//            judgeInfoMessageEnum = judgeInfoMessageEnum.WRONG_ANSWER;
//            return null;
//        }
//        //2.依次判断吗，每一项输出和预期输出是否相等
//        for (int i = 0; i<judgeCaseList.size();i++){
//            JudgeCase judgeCase =judgeCaseList.get(i);
//            if (!judgeCase.getOutput().equals(outputList.get(i))){
//                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
//                return null;
//            }
//        }
//        //3.判断题目的限制是否符合要求
//        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
//        Long memory = judgeInfo.getMemory();
//        Long time = judgeInfo.getTime();
//        String judgeConfigStr = question.getJudgeConfig();
//        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr,JudgeConfig.class);
//        Long needTimeLimit = judgeConfig.getTimeLimit();
//        Long needMemoryLimit = judgeConfig.getMemoryLimit();
//        if (memory > needMemoryLimit){
//            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
//            return null;
//        }
//        if (time > needTimeLimit){
//            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
//            return null;
//        }
//
//        //4.可能有其他的异常情况

    }
}
