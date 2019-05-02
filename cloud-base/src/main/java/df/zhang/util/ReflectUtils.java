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
package df.zhang.util;

import lombok.NonNull;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-04-23
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public class ReflectUtils {

    public static <R> R newInstance(Class<R> clazz) {
        if (Objects.isNull(clazz)) {
            return null;
        }
        // 创建集合
        if (clazz.isArray()) {
            return (R) Array.newInstance(clazz.getComponentType(), 0);
        }
        // 如果是接口或者抽象类
        if (isInterfaceOrAbstractClass(clazz)) {
            // Set
            if (Set.class.isAssignableFrom(clazz)) {
                return (R) new LinkedHashSet();
            }

            // 集合顶层接口或List
            if (Collection.class.isAssignableFrom(clazz) || List.class.isAssignableFrom(clazz)) {
                return (R) new ArrayList();
            }

            // Map
            if (Map.class.isAssignableFrom(clazz)) {
                return (R) new LinkedHashMap();
            }
            return null;
        }
        // 没有构造函数的
        Constructor<R> constructor;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException ignored) {
            return null;
        }
        if (constructor == null) {
            return null;
        }
        // 尝试调用newInstance
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != Object.class && !isInterfaceOrAbstractClass(superclass)) {
                return getField(clazz.getSuperclass(), fieldName);
            }
        }
        return null;
    }

    public static boolean isInterfaceOrAbstractClass(Class<?> clazz) {
        int mod = clazz.getModifiers();
        return Modifier.isInterface(mod) || Modifier.isAbstract(mod);
    }
}
