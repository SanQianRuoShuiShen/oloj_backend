package com.yang.oloj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.oloj.annotation.AuthCheck;
import com.yang.oloj.common.BaseResponse;
import com.yang.oloj.common.ErrorCode;
import com.yang.oloj.common.ResultUtils;
import com.yang.oloj.constant.UserConstant;
import com.yang.oloj.exception.BusinessException;

import com.yang.oloj.model.dto.question.QuestionQueryRequest;
import com.yang.oloj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yang.oloj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yang.oloj.model.entity.Question;
import com.yang.oloj.model.entity.QuestionSubmit;
import com.yang.oloj.model.entity.User;
import com.yang.oloj.model.vo.QuestionSubmitVO;
import com.yang.oloj.service.QuestionSubmitService;
import com.yang.oloj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 提交题目
 *
 * @author <a href="https://github.com/SanQianRuoShuiShen">三千弱水</a>
 * @from  
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
@Deprecated
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目 / 取消提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return 提交题目的ID
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                         HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交题目
        final User loginUser = userService.getLoginUser(request);
        Long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }
    /**
     * 分页获取题目提交列表（除了管理员，普通用户只能看到非答案、提交代码等公开信息）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        //从数据库中获取原始地提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userService.getLoginUser(request);
        //返回脱敏信息
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage,loginUser));
    }
}
