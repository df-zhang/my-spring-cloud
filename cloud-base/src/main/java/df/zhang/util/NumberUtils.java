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

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-04-23
 * @since 1.0.0
 */
public final class NumberUtils {
    public static final byte BYTE_DEFAULT_VALUE = (byte) 0;
    public static final short SHORT_DEFAULT_VALUE = (short) 0;
    public static final int INT_DEFAULT_VALUE = 0;
    public static final float FLOAT_DEFAULT_VALUE = 0F;
    public static final double DOUBLE_DEFAULT_VALUE = 0D;
    public static final long LONG_DEFAULT_VALUE = 0L;
    public static final long CHAR_DEFAULT_VALUE = '\u0000';
    public static final boolean BOOLEAN_DEFAULT_VALUE = false;

    public static final Object getPrimitiveDefaultValue(Class<?> targetClass) {
        if (!targetClass.isPrimitive()) {
            return null;
        }
        if (targetClass == byte.class) {
            return BYTE_DEFAULT_VALUE;
        } else if (targetClass == short.class) {
            return SHORT_DEFAULT_VALUE;
        } else if (targetClass == int.class) {
            return INT_DEFAULT_VALUE;
        } else if (targetClass == float.class) {
            return FLOAT_DEFAULT_VALUE;
        } else if (targetClass == double.class) {
            return DOUBLE_DEFAULT_VALUE;
        } else if (targetClass == long.class) {
            return LONG_DEFAULT_VALUE;
        } else if (targetClass == char.class) {
            return CHAR_DEFAULT_VALUE;
        } else if (targetClass == boolean.class) {
            return BOOLEAN_DEFAULT_VALUE;
        }
        return null;
    }
}
