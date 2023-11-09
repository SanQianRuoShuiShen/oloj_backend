package com.yang.oloj.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.oloj.judge.codesandbox.model.JudgeInfo;
import com.yang.oloj.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目风封装类
 * @TableName question
 *
 * 用于专门返给前端的信息，给信息脱敏，保证信息安全
 */
@TableName(value ="question")
@Data
public class QuestionSubmitVO implements Serializable {
    /**
     * id
     */
//    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息（json对象）
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态（0-待判题、1-判题中、2-成功、3-失败)
     */
    private Integer status;

    /**
     * 题目Id
     */
    private Long questionId;

    /**
     * 创建用户ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 提交题目用户信息
     */
    private UserVO userVO;

    /**
     * 对应题目信息
     */
    private QuestionVO questionVO;

    /**
     * 包装类转对象
     *
     * @param questionSubmitVO
     * @return
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        JudgeInfo judgeInfoObj = questionSubmitVO.getJudgeInfo();
        if (judgeInfoObj != null) {
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return questionSubmit;
    }

    /**
     * 对象转包装类
     *
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        JudgeInfo judgeInfoStr = JSONUtil.toBean(questionSubmit.getJudgeInfo(),JudgeInfo.class);
        questionSubmitVO.setJudgeInfo(judgeInfoStr);
        return questionSubmitVO;
    }
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}