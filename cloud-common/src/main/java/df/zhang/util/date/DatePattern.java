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
package df.zhang.util.date;

import java.time.format.DateTimeFormatter;

/**
 * 日期时间格式化常量类，基于Java8的Date&Time API
 *
 * @author df.zhang Email: 84154025@qq.com
 * @version 1.0.0
 * @date 2019-04-21
 */
public final class DatePattern {

    public static final DateTimeFormatter TIME_HMMSS = DateTimeFormatter.ofPattern("hmmss");
    public static final DateTimeFormatter TIME_H_MM_SS = DateTimeFormatter.ofPattern("h:mm:ss");
    public static final DateTimeFormatter TIME_AHMMSS = DateTimeFormatter.ofPattern("ahmmss");
    public static final DateTimeFormatter TIME_AH_MM_SS = DateTimeFormatter.ofPattern("ah:mm:ss");
    public static final DateTimeFormatter TIME_HHMMSS = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter TIME_HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static final DateTimeFormatter DATE_YYMM = DateTimeFormatter.ofPattern("yyMM");
    public static final DateTimeFormatter DATE_YYYYMM = DateTimeFormatter.ofPattern("yyyyMM");
    public static final DateTimeFormatter DATE_YYYY_MM = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final DateTimeFormatter DATE_YYMMDD = DateTimeFormatter.ofPattern("yyMMdd");
    public static final DateTimeFormatter DATE_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DATE_YY_MM_DD = DateTimeFormatter.ofPattern("yy-MM-dd");
    public static final DateTimeFormatter DATE_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DATETIME_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter DATETIME_YYYYMMDDHHMMSSSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_H_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_H_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ssZ");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_H_MM_SS_UTC = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss'Z'");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_H_MM_SS_SSS = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss.SSS");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_H_MM_SS_SSS_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss.SSSZ");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_H_MM_SS_SSS_UTC = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss.SSS'Z'");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_AH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd ah:mm:ss");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_AH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd ah:mm:ssZ");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_AH_MM_SS_SSS = DateTimeFormatter.ofPattern("yyyy-MM-dd ah:mm:ss.SSS");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_AH_MM_SS_SSS_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd ah:mm:ss.SSSZ");
    public static final DateTimeFormatter DATETIME_YY_MM_DD_AH_MM_SS_SSS_UTC = DateTimeFormatter.ofPattern("yyyy-MM-dd ah:mm:ss.SSS'Z'");
    public static final DateTimeFormatter DATETIME_YY_MM_DDTHH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateTimeFormatter DATETIME_YY_MM_DDTHH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    public static final DateTimeFormatter DATETIME_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATETIME_YYYY_MM_DD_HH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZ");
    public static final DateTimeFormatter DATETIME_YYYY_MM_DDTHH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateTimeFormatter DATETIME_YYYY_MM_DDTHH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    public static final DateTimeFormatter DATETIME_YYYY_MM_DDTHH_MM_SS_SSS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    public static final DateTimeFormatter DATETIME_YYYY_MM_DDTHH_MM_SS_SSS_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static final DateTimeFormatter DATETIME_YYYY_MM_DDTHH_MM_SS_SSS_UTC = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static final DateTimeFormatter SLASH_DATE_YY_MM_DD = DateTimeFormatter.ofPattern("yy/MM/dd");
    public static final DateTimeFormatter SLASH_DATE_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DD_H_MM_SS = DateTimeFormatter.ofPattern("yyyy/MM/dd h:mm:ss");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DD_H_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy/MM/dd h:mm:ssZ");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DD_AH_MM_SS = DateTimeFormatter.ofPattern("yyyy/MM/dd ah:mm:ss");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DD_AH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy/MM/dd ah:mm:ssZ");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DD_AH_MM_SS_UTC = DateTimeFormatter.ofPattern("yyyy/MM/dd ah:mm:ss'Z'");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DD_HH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ssZ");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DD_HH_MM_SS_UTC = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss'Z'");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DDTHH_MM_SS = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DDTHH_MM_SS_SSS = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss.SSS");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DDTHH_MM_SS_SSS_Z = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss.SSSZ");
    public static final DateTimeFormatter SLASH_DATETIME_YYYY_MM_DDTHH_MM_SS_SSS_UTC = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss.SSS'Z'");

    public static final DateTimeFormatter CHINESE_TIME_H_MM_SS = DateTimeFormatter.ofPattern("h时mm分ss秒");
    public static final DateTimeFormatter CHINESE_TIME_AH_MM_SS = DateTimeFormatter.ofPattern("ah时mm分ss秒");
    public static final DateTimeFormatter CHINESE_TIME_HH_MM_SS = DateTimeFormatter.ofPattern("HH时mm分ss秒");
    public static final DateTimeFormatter CHINESE_DATE_YY_MM_DD = DateTimeFormatter.ofPattern("yy年MM月dd日");
    public static final DateTimeFormatter CHINESE_DATE_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    public static final DateTimeFormatter CHINESE_DATETIME_YYYY_MM_DDH_MM_SS = DateTimeFormatter.ofPattern("yyyy年MM月dd日h时mm分ss秒");
    public static final DateTimeFormatter CHINESE_DATETIME_YYYY_MM_DDH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy年MM月dd日h时mm分ss秒Z");
    public static final DateTimeFormatter CHINESE_DATETIME_YYYY_MM_DDAH_MM_SS = DateTimeFormatter.ofPattern("yyyy年MM月dd日ah时mm分ss秒");
    public static final DateTimeFormatter CHINESE_DATETIME_YYYY_MM_DDAH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy年MM月dd日ah时mm分ss秒Z");
    public static final DateTimeFormatter CHINESE_DATETIME_YYYY_MM_DDHH_MM_SS = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒");
    public static final DateTimeFormatter CHINESE_DATETIME_YYYY_MM_DDHH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒Z");
    public static final DateTimeFormatter CHINESE_DATETIME_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
    public static final DateTimeFormatter CHINESE_DATETIME_YYYY_MM_DD_HH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");

    private DatePattern() {
        throw new UnsupportedOperationException();
    }
}
