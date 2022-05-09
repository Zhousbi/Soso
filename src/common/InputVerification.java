package common;

import java.util.Scanner;

/**
 * @author zhouxw
 * @date 2022/5/4
 */
public enum InputVerification {
    /**
     * 手机号验证
     */
    PHONE_NUMBER("^1\\d{10}$", "手机号长度应为11位, 请重试!", "请输入手机号: "),
    /**
     * 密码验证
     */
    PASSWORD("^\\d{6}$", "密码长度应为6位, 请重试!", "请输入密码: "),
    /**
     * 注册时选号
     */
    CARD_NUMBER_SELECT("^[1-9]$", "序号范围为1~9, 请重试!", "请选择卡号(输入1~9的序号):"),
    /**
     * 注册时选择套餐
     */
    PACKAGE_SELECT("^[1-3]$", "序号范围为1~3, 请重试!", "1.话痨套餐\t2.网虫套餐\t3.超人套餐, 请选择套餐(输入序号):"),
    /**
     * 使用soso时选择功能
     */
    FUN_SELECT("^[1-3]$", "序号范围为1~3, 请重试!", "请选择使用功能(1.打电话 2.发短信 3.上网): ");

    private final static Scanner INPUT = new Scanner(System.in);
    private final String regex;
    private final String errNote;
    private final String inputNote;

    InputVerification(String regex, String errNote, String inputNote) {
        this.regex = regex;
        this.errNote = errNote;
        this.inputNote = inputNote;
    }

    /**
     * 验证字符串是否能匹配对应正则表达式
     *
     * @param str 字符串
     * @param ver 正则表达式
     * @return 是否
     */
    public static boolean verify(String str, InputVerification ver) {
        if (str == null) {
            return false;
        }
        if (str.matches(ver.getRegex())) {
            return true;
        } else {
            System.out.println(ver.getErrNote());
            return false;
        }
    }

    /**
     * 获取用户输入是否匹配对应正则表达式, 满足: 返回用户输入, 不满足: 提示并要求重新输入
     *
     * @param ver       验证规则
     * @return 字符串
     */
    public static String getInput(InputVerification ver) {
        String result;
        while (true) {
            result = INPUT.next();
            System.out.println(result);
            if (verify(result, ver)) {
                return result;
            }
        }
    }

    public String getRegex() {
        return regex;
    }

    public String getErrNote() {
        return errNote;
    }

}
