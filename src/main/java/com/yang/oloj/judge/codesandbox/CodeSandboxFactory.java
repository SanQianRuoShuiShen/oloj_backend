package com.yang.oloj.judge.codesandbox;

import com.yang.oloj.judge.codesandbox.impl.ExampleCodeSandboxImpl;
import com.yang.oloj.judge.codesandbox.impl.RemoteCodeSandboxImpl;
import com.yang.oloj.judge.codesandbox.impl.ThirdPartyCodeSandboxImpl;

/**
 * 代码沙箱静态工厂（根据字符串创建指定的代码沙箱实例）
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱实例
     *
     * @param type
     * @return
     */
    public static CodeSandbox newInstance(String type) {
//可扩展为单例工厂模式
            switch (type) {
                case "example":
                    return new ExampleCodeSandboxImpl();
                case "remote":
                    return new RemoteCodeSandboxImpl();
                case "thirdParty":
                    return new ThirdPartyCodeSandboxImpl();
                default:
                    return new ExampleCodeSandboxImpl();
            }
    }

}
