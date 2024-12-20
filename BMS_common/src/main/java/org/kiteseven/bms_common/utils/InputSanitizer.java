package org.kiteseven.bms_common.utils;

//用于
public class InputSanitizer {

    // 用于处理字符串中的特殊字符
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // 清除特殊字符以防止SQL注入和XSS
        return input.replace("'", "")   // 单引号转义
                .replace("\"", "")   // 双引号转义
                .replace("<", "")       // 小于号转义
                .replace(">", "")       // 大于号转义
                .replace("&", "")      // 和号转义
                .replace("#", "")        // 井号转义
                .replace("%", "")        // 百分号转义
                .replace("{", "")        // 左大括号转义
                .replace("}", "")        // 右大括号转义
                .replace("|", "")        // 竖线转义
                .replace("\\", "")      // 反斜杠转义
                .replace("^", "")        // 脱字符转义
                .replace("~", "")        // 波浪线转义
                .replace("[", "")        // 左方括号转义
                .replace("]", "");       // 右方括号转义
    }
}
