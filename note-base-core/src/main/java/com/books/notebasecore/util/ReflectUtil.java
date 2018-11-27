package com.books.notebasecore.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;

/**
 * 反射相关操作
 * 
 * @Filename: ReflectUtil.java
 * @Version: 1.0
 * @Author: fenghu
 * @Email: liulei@mightcloud.com
 * 
 */
public final class ReflectUtil {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 将source对象上的同名属性值拷贝到target对象上。
     * <p>
     * 1. source或者target中任意一个为null时，拷贝失败；<br />
     * 2. 仅进行浅拷贝，即：对任何一个同名属性，都将source上的field值设置到target上； 3. 仅拷贝实例属性，静态属性不会拷贝； 4.
     * 只会对source和target类中声明的field进行拷贝，任何父类上的field不会考虑；
     * 
     * @param source
     *            源对象。
     * @param target
     *            目标对象。
     * @param mode
     *            0:拷贝所有同名属性(field)；1:仅拷贝fields中指定的属性；2:拷贝同名属性，但排除fields中指定的属性。
     * @param fields
     *            指定的属性。
     */
    public static void copy(Object source, Object target, int mode, String... fields) {
        if (source == null) {
            if (LOGGER.isInfoEnabled())
                LOGGER.info("Source object is null, can not copy fields to target object "
                        + (target == null ? "null" : target.getClass().getName()));
            return;
        }
        if (target == null) {
            if (LOGGER.isInfoEnabled())
                LOGGER.info("Target object is null, can not copy fields from source object "
                        + (source == null ? "null" : source.getClass().getName()));
            return;
        }

        Class<?> clsTarget = target.getClass();
        Field[] targetFields = clsTarget.getDeclaredFields();
        if (targetFields == null || targetFields.length <= 0) {
            if (LOGGER.isInfoEnabled())
                LOGGER.info(clsTarget.getName() + " has no fields to copy to");
            return;
        }

        Field[] sourceFields = source.getClass().getDeclaredFields();
        Map<String, Field> sourceFieldMap = new HashMap<String, Field>(sourceFields.length);
        for (Field f : sourceFields) {
            if ("this$0".equals(f.getName()))
                continue;
            sourceFieldMap.put(f.getName().toLowerCase(), f);
        }

        Set<String> fieldSet = new HashSet<String>(fields == null ? 0 : fields.length);
        for (int i = 0; fields != null && i < fields.length; i++) {
            if (StringUtil.isEmpty(fields[i]))
                continue;
            fieldSet.add(fields[i].toLowerCase().trim());
        }

        for (Field f : targetFields) {
            String name = f.getName().toLowerCase();
            if (!sourceFieldMap.containsKey(name))
                continue;
            if (mode == 1 && !fieldSet.contains(name))
                continue;
            if (mode == 2 && fieldSet.contains(name))
                continue;
            try {
                f.setAccessible(true);
                sourceFieldMap.get(name).setAccessible(true);
                f.set(target, sourceFieldMap.get(name).get(source));
            } catch (IllegalArgumentException e) {
                LOGGER.info("Copy fields failed", e);
            } catch (IllegalAccessException e) {
                LOGGER.info("Copy fields failed", e);
            }
        }
    }
}