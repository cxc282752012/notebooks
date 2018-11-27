package com.books.notebasecore.util;

/**
 * 参数错误，方法调用的入参不符合要求
 * 
 * @Filename: ArgumentException.java
 * @Version: 1.0
 * @Author: fenghu
 * @Email: liulei@mightcloud.com
 * 
 */
public class ArgumentException extends BusinessException {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6832695224568830049L;

    public ArgumentException(String message) {
        super(message);
    }
}