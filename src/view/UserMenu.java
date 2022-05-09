package view;

import common.ConsumType;
import common.InputVerification;
import service.ConsumInfoService;
import service.MobileCardService;

import java.util.Scanner;

/**
 * 用户菜单, 登录后显示
 *
 * @author zhouxw
 * @date 2022/5/7
 */
public class UserMenu {
    private final MobileCardService cardService = new MobileCardService();
    private final ConsumInfoService consumInfoService = new ConsumInfoService();
    private final Scanner input = new Scanner(System.in);

    /**
     * 用户主菜单界面, 根据用户选择跳转
     */
    public void mainMenu(String number) {
        System.out.println("******soso移动用户菜单******");
        while (true) {
            System.out.println("1.使用soso\n2.本月账单查询\n3.套餐余量查询\n4.打印消费详单\n5.套餐变更\n6.办理退网");
            System.out.print("请选择(输入1~6选择功能, 其他键返回上级):");
            String opr = input.next();
            switch (opr) {
                case "1":
                    useSoso(number);
                    break;
                case "2":
                    printAmountDetail(number);
                    break;
                case "3":
                    printRemainDetail(number);
                    break;
                case "4":
                    printConsumInfo(number);
                    break;
                case "5":
                    break;
                case "6":
                    break;
                default:
                    new MainMenu().mainMenu();
            }
        }
    }

    /**
     * 用户使用手机通话 短信 上网功能
     */
    public void useSoso(String number) {
        // 消费类型
        ConsumType type = null;
        // 使用量
        int dataCount;
        String opr = InputVerification.getInput(InputVerification.FUN_SELECT);
        switch (opr) {
            case "1":
                type = ConsumType.TALK_TIME;
                break;
            case "2":
                type = ConsumType.SMS_COUNT;
                break;
            case "3":
                type = ConsumType.FLOW;
                break;
            default:
        }
        System.out.print("请输入使用量(电话/分钟, 短信/条, 上网/MB): ");
        dataCount = input.nextInt();
        cardService.useSoso(number, type, dataCount);
    }

    /**
     * 打印本月账单
     *
     * @param number 手机号
     */
    public void printAmountDetail(String number) {
        System.out.println(cardService.showAmountDetail(number));
    }

    /**
     * 打印套餐余量
     *
     * @param number 手机号
     */
    public void printRemainDetail(String number) {
        System.out.println(cardService.showRemainDetail(number));
    }

    /**
     * 打印消费详单
     *
     * @param number 手机号
     */
    public void printConsumInfo(String number) {
        System.out.println(consumInfoService.showConsumInfo(number));
    }
}
