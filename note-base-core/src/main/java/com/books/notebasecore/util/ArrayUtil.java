package com.books.notebasecore.util;

import java.util.List;

public class ArrayUtil {
    /**
     * 是否存在下标
     * 
     * @param arr
     * @param index
     * @return
     */
    public static boolean isArrayIndex(String[] arr, int index) {
        try {
            String str = arr[index];
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 是否存在下标
     * 
     * @param list
     * @param index
     * @return
     */
    public static boolean isArrayIndex(List<?> list, int index) {
        try {
            list.get(index);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
