package com.books.notebasecore.util;

import org.apache.commons.lang3.StringUtils;

/**
 * emoji表情转换工具
 * 
 * @Filename: EmojiUtil.java
 * @Version: 1.0
 * @Author: linyue
 * @Email: linyue@xianghuoquan.com
 * 
 */
public class EmojiUtil {

    public static String filterEmoji(String source) {
        if (StringUtils.isNotBlank(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
        } else {
            return source;
        }
    }
}
