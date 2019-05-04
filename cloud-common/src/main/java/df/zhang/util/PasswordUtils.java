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

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;

/**
 * 密码工具类
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-03
 * @since 1.0.0
 */
public final class PasswordUtils {
    /** 一袋盐*/
    private static final byte[] SALT = DigestUtils.md5("@~df.zhang~@");
    /** 交叉合并后byte数组的长度*/
    private static final int ENCODE_LEN = 32;

    /**
     * 密码加密工具，使用{@link DigestUtils}将密码转换为16位长度的MD5字节数组，
     * 然后与预先设置好的SALT数组交叉合并，得到最终32位长度的字节数组，转换为SHA-1字符串。
     *
     * @param rawPassword param1
     * @return java.lang.String
     * @date 2019-05-03 17:48
     * @author df.zhang
     * @since 1.0.0
     */
    public static String encode(CharSequence rawPassword) {
        assert Objects.nonNull(rawPassword);
        byte[] rawBytes = DigestUtils.md5(rawPassword.toString());
        byte[] encodeBytes = new byte[ENCODE_LEN];
        // 将两个字节数组交叉组合成一个字节数组，循环中可实现某个数组反转
        int jump = 2;
        for (int i = 0, j = 0; i < ENCODE_LEN; i += jump, j++) {
            encodeBytes[i] = rawBytes[j];
            encodeBytes[i + 1] = SALT[j];
        }
        return DigestUtils.sha1Hex(encodeBytes);
    }
}
