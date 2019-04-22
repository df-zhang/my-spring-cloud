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

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @version 1.0.0
 * @date 2019-04-22
 */
public final class BeanUtils {

    public static <R> R copy(Object source, Class<R> rClass) {
        assert !Objects.isNull(source);
        assert !Objects.isNull(rClass);

        if (Iterable.class.isAssignableFrom(rClass)) {

        }
        return null;
    }

    public static void copy(Object source, Object target) {
        assert !Objects.isNull(source);
        assert !Objects.isNull(target);
        getCopier(source.getClass(), target.getClass()).copy(source, target, BEAN_COPY_CONVERTER);
    }

    private static BeanCopier getCopier(Class<?> sourceClass, Class<?> targetClass) {
        String key = sourceClass.getName() + "@" + targetClass.getName();
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

    private static final Converter BEAN_COPY_CONVERTER = (value, target, context) -> {
        if (Objects.isNull(value)) {
            return null;
        }
        return null;
    };


}
