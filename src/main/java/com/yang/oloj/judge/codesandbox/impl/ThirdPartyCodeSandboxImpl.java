package com.yang.oloj.judge.codesandbox.impl;

import com.yang.oloj.judge.codesandbox.CodeSandbox;
import com.yang.oloj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yang.oloj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（网上现成的）
 */
public class ThirdPartyCodeSandboxImpl implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        System.out.println("第三方代码沙箱");
        return null;
    }
}
