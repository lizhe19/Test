package com.test.rpc;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

/**
 * Retryable测试类
 *
 * @author lz-119612
 * @version 1.0
 * @date 2020/6/19 10:41
 *  
 **/
public class RetryableTest {
    /**
     * springboot启动类 标注声明  @EnableRetry注解
     * @param args
     */
    public static void main(String[] args) {
        switchMaster();
    }
    /**
     * 调用方法
     */
    @Retryable(value = TestException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000L, multiplier = 2))
    public static void switchMaster() throws TestException {
        try {
            //System.out.println("--------------------");
            String str = null;
            str.toString();
        } catch (Exception e) {
            throw new TestException(new Exception("业务异常"));
        }
    }

    /**
     * 异常后方法
     * @param sme
     */
    @Recover
    public void switchMasterRecover(TestException sme) {
        System.out.println("方法调用异常，原因:" + sme);
    }

    static class TestException extends RuntimeException {
        public TestException(Exception e) {
            super(e.getMessage(), e);
        }
    }
}
