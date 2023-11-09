package com.yang.oloj.judge.codesandbox;

import com.yang.oloj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yang.oloj.judge.codesandbox.model.ExecuteCodeResponse;


import java.util.concurrent.ExecutionException;
public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
