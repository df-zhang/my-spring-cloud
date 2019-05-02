/*
 * Copyright [2019] [df.zhang]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package df.zhang.util.bean;

import df.zhang.util.CollectionUtils;
import df.zhang.util.ReflectUtils;
import net.sf.cglib.beans.BeanCopier;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * <p>对象属性复制转换工具类。
 * <p>使用须知：本工具类使用cglib包中的{@link BeanCopier}实现，性能优于apache与spring提供的基于反射机制实现的BeanUtils。<br>
 * 应根据实际场景调整{@link BeanConverter#convert(Object, Class, Object)}方法。
 *
 * <p>{@link BeanCopier}复制属性时需要有对应的get和set方法，且set方法的返回类型必须是void。
 * 该硬性条件参考{@link net.sf.cglib.core.ReflectUtils#getBeanSetters(Class)}方法的具体实现{@link PropertyDescriptor#getWriteMethod()}：
 *
 * <blockquote><pre>
 * writeMethod = Introspector.findMethod(cls, writeMethodName, 1, args);
 * if (writeMethod != null) {
 *      if (!writeMethod.getReturnType().equals(void.class)) {
 *          writeMethod = null;
 *      }
 * }
 * </blockquote></pre>
 *
 * <p>结合Lombok使用时须关闭其访问器链配置：lombok.accessors.chain=false，请勿编写有返回类型的set方法。
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-04-22
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public final class BeanUtils {
    private static final String LINK_WORD = "@~df.zhang~@";

    public static <R> R copy(Object source, Class<R> rClass) {
        assert !Objects.isNull(source);
        assert !Objects.isNull(rClass);
        Class<?> sourceClass = source.getClass();
        if (sourceClass.isEnum() || BeanConverter.isPrimitive(sourceClass)) {
            return (R) source;
        }
        R r = ReflectUtils.newInstance(rClass);
        copy(source, r);
        return r;
    }

    public static Object copyObject(Object source, Class<?> rClass) {
        assert !Objects.isNull(source);
        assert !Objects.isNull(rClass);
        Class<?> sourceClass = source.getClass();
        if (sourceClass.isEnum() || BeanConverter.isPrimitive(sourceClass)) {
            return source;
        }
        Object r = ReflectUtils.newInstance(rClass);
        copy(source, r);
        return r;
    }

    public static void copy(Object source, Object target) {
        assert !Objects.isNull(source);
        assert !Objects.isNull(target);
        getCopier(source.getClass(), target.getClass())
                .copy(source, target, new BeanConverter(source.getClass(), target.getClass()));
    }

    public static <R> List<R> copyList(List<?> source, Class<R> rClass) {
        return (List<R>) copyCollection(source, rClass);
    }

    public static <R> Collection<R> copyCollection(Collection<?> source) {
        return copyCollection(source, null);
    }

    public static <R> Collection<R> copyCollection(Collection<?> source, Class<R> rClass) {
        if (CollectionUtils.isEmpty(source)) {
            return new ArrayList<>(0);
        }
        if (rClass == null) {
            Collection collection = ReflectUtils.newInstance(source.getClass());
            if (CollectionUtils.isEmpty(collection)) {
                return new ArrayList<>(0);
            }
            for (Object sourceValue : source) {
                collection.add(copy(sourceValue, sourceValue.getClass()));
            }
            return collection;
        }
        Collection<R> collection = new ArrayList<>();
        for (Object sourceValue : source) {
            collection.add(copy(sourceValue, rClass));
        }
        return collection;
    }


    public static <T> T[] copyArray(Object[] source, Class<T> componentType) {
        if (CollectionUtils.isEmpty(source)) {
            if (componentType == null) {
                return (T[]) Array.newInstance(Object.class, 0);
            } else {
                return (T[]) Array.newInstance(componentType, 0);
            }
        }
        // 创建数组
        Class<?> targetComponentType;
        if (componentType == null || componentType == Object.class) {
            targetComponentType = source.getClass().getComponentType();
        } else {
            targetComponentType = componentType;
        }
        int size = Array.getLength(source);
        Object result;
        if (componentType == null) {
            result = Array.newInstance(Object.class, size);
        } else {
            result = Array.newInstance(componentType, size);
        }
        for (int i = 0; i < size; i++) {
            Array.set(result, i, BeanUtils.copy(source[i], targetComponentType));
        }
        return (T[]) result;
    }

    public static <T> T[] copyCollectionToArray(Collection<?> source, Class<T> componentType) {
        if (CollectionUtils.isEmpty(source)) {
            return (T[]) Array.newInstance(componentType, 0);
        }
        // 创建数组
        int size = source.size();
        T[] result = (T[]) Array.newInstance(componentType, size);
        int i = 0;
        if (componentType == Object.class) {
            // 如果目标数组对象类型是Object。 不在循环中判断，提升一点微乎其微的性能。
            for (Object value : source) {
                Array.set(result, i++, BeanUtils.copy(value, value.getClass()));
            }
        } else {
            // 复制成目标数组对象类型。
            for (Object value : source) {
                Array.set(result, i++, BeanUtils.copy(value, componentType));
            }
        }
        return result;
    }

    public static void copyArrayToCollection(Object[] source, Collection collection, Class<?> targetClass) {
        if (source == null) {
            return;
        }
        if (collection == null) {
            return;
        }
        // 创建数组
        Class<?> targetComponentType;
        if (targetClass == null || targetClass == Object.class) {
            targetComponentType = source.getClass().getComponentType();
        } else {
            targetComponentType = targetClass;
        }
        for (Object value : source) {
            collection.add(BeanUtils.copyObject(value, targetComponentType));
        }
    }

    private static BeanCopier getCopier(Class<?> sourceClass, Class<?> targetClass) {
        String key = sourceClass.getName() + LINK_WORD + targetClass.getName();
        if (BEAN_COPIER_MAP.containsKey(key)) {
            return BEAN_COPIER_MAP.get(key);
        } else {
            BeanCopier copier = BeanCopier.create(sourceClass, targetClass, true);
            BeanCopier old = BEAN_COPIER_MAP.putIfAbsent(key, copier);
            if (old != null) {
                return old;
            }
            return copier;
        }
    }

    private static final ConcurrentMap<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>(0);

    static List<Function<?, ?>> list = new ArrayList<>();

}
