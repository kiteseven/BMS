package org.kiteseven.bms_common.utils;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class AccountGeneratorUtil {
    private static final SecureRandom random = new SecureRandom();
    private static final Set<String> generatedAccounts = new HashSet<>();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ACCOUNT_LENGTH = 12;

    /**
     * 生成唯一的账号，长度为 12 位，只包含数字和字母。
     *
     * @return 唯一的账号字符串
     */
    public static synchronized String generateUniqueAccount() {
        String account;
        do {
            account = generateRandomAccount();
        } while (generatedAccounts.contains(account));

        generatedAccounts.add(account);
        return account;
    }

    /**
     * 生成随机账号字符串。
     *
     * @return 随机生成的账号字符串，长度为 12 位
     */
    private static String generateRandomAccount() {
        StringBuilder accountBuilder = new StringBuilder(ACCOUNT_LENGTH);
        for (int i = 0; i < ACCOUNT_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            accountBuilder.append(CHARACTERS.charAt(index));
        }
        return accountBuilder.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(generateUniqueAccount());
        }
    }
}
