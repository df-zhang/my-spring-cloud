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

import java.util.Objects;

/**
 * 字符串操作类，一些常用的字符串工具集
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-04-21
 * @since 1.0.0
 */
public final class StringUtils {
    /**
     * 判断字符串是否非空。
     *
     * @param str 字符串
     * @return boolean 非空返回true，空返回false
     * @date 2019-04-21 23:20:36
     * @author df.zhang
     * @since 1.0.0
     */
    public static boolean isNotEmpty(String str) {
        return !Objects.isNull(str) && !str.isEmpty();
    }

    /**
     * 判断字符串是否为空。
     *
     * @param str 字符串
     * @return boolean 空返回true，非空返回false
     * @date 2019-04-21 23:21:05
     * @author df.zhang
     * @since 1.0.0
     */
    public static boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }

    /**
     * 判断字符串是否非空且并非只包含空白符。此方法验证该字符串至少有一个非空白符的字符时才为真。
     *
     * @param str 字符串
     * @return boolean 字符串长度大于0且至少包含一个非空白符的字符时返回true，否则返回false
     * @date 2019-04-21 23:21:27
     * @author df.zhang
     * @since 1.0.0
     */
    public static boolean isNotBlank(CharSequence str) {
        return (!Objects.isNull(str) && str.length() > 0 && containsText(str));
    }

    /**
     * 判断字符串是否非空且并非只包含空白符。此方法验证该字符串至少有一个非空白符的字符时才为真。
     *
     * @param str 字符串
     * @return boolean 字符串长度大于0且至少包含一个非空白符的字符时返回true，否则返回false
     * @date 2019-04-21 23:21:27
     * @author df.zhang
     * @since 1.0.0
     */
    public static boolean isNotBlank(String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    /**
     * 判断字符串是否为空或者只包含空白符，即字符串长度为0或者字符串内只有空白符（一个或多个）时，都验证为真。
     *
     * @param str param1
     * @return boolean 字符串长度为0或者字符串内只有空白符（一个或多个）时，都返回true；否则返回false
     * @date 2019-04-21 23:25:03
     * @author df.zhang
     * @since 1.0.0
     */
    public static boolean isBlank(String str) {
        return !isNotBlank(str);
    }

    /**
     * 检查文本是否包含非空白符的字符
     *
     * @param str 字符串
     * @return boolean 包含任意非空白符字符时返回true
     * @date 2019-04-21 23:28:36
     * @author df.zhang
     * @since 1.0.0
     */
    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
