package view;

import common.InputVerification;
import common.PackagesFactory;
import pojo.ServicePackage;
import service.MobileCardService;

import java.util.Scanner;

/**
 * 主菜单
 *
 * @author Zhouxw
 * @date 2022/5/7 11:32
 */
public class MainMenu {
    private final Scanner input = new Scanner(System.in);
    private final MobileCardService cardService = new MobileCardService();

    /**
     * 一级主菜单
     */
    public void mainMenu() {
        System.out.println("*************欢迎使用soso移动业务大厅*************");
        while (true) {
            System.out.println("1.用户注册\t2.用户登录\t3.话费充值\t4.资费说明\t5.退出系统");
            System.out.print("请选择: ");
            String opr = input.next();
            switch (opr) {
                case "1":
                    regist();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    charge();
                    break;
                case "4":
                    break;
                case "5":
                    System.exit(0);
                    break;
                default:
                    System.out.println("序号不存在, 请重新选择");
            }
        }
    }

    /**
     * 用户注册, 将选择的手机号 套餐, 输入的用户名 密码 充值的金额传入service层
     */
    public void regist() {
        System.out.println("*****新用户注册*****");
        String num;
        String username;
        String password;
        double money;
        ServicePackage servicePackage = null;
        String[] numbers = cardService.getNumbers(9);
        StringBuffer numSelection = new StringBuffer("*****可选择卡号*****\n");
        for (int i = 0; i < numbers.length; i++) {
            int no = (i + 1);
            numSelection.append(no).append(".").append(numbers[i]).append("\t");
            if (no % 3 == 0 && no != 9) {
                numSelection.append("\n");
            }
        }
        System.out.println(numSelection);
        int index = Integer.parseInt(InputVerification.getInput(InputVerification.CARD_NUMBER_SELECT));
        num = numbers[index - 1];
        String opr = InputVerification.getInput(InputVerification.PACKAGE_SELECT);
        switch (opr) {
            case "1":
                servicePackage = PackagesFactory.getServicePackage(PackagesFactory.TALK_PACKAGE);
                break;
            case "2":
                servicePackage = PackagesFactory.getServicePackage(PackagesFactory.NET_PACKAGE);
                break;
            case "3":
                servicePackage = PackagesFactory.getServicePackage(PackagesFactory.SUPER_PACKAGE);
                break;
            default:
        }
        System.out.print("请输入用户名: ");
        username = input.next();
        password = InputVerification.getInput(InputVerification.PASSWORD);
        while (true) {
            System.out.print("请输入预存话费(套餐资费为" + servicePackage.getPrice() + "元): ");
            money = input.nextDouble();
            money -= servicePackage.getPrice();
            if (money > 0) {
                break;
            } else {
                System.out.println("预存话费不足以抵扣本月资费!");
            }
        }
        cardService.register(num, username, password, servicePackage, money);
    }

    /**
     * 用户登录
     */
    public void login() {
        System.out.println("*****用户登录*****");
        String num = InputVerification.getInput(InputVerification.PHONE_NUMBER);
        String password = InputVerification.getInput(InputVerification.PASSWORD);
        if (cardService.login(num, password)) {
            System.out.print("登陆成功!");
            new UserMenu().mainMenu(num);
        } else {
            System.out.print("手机号或密码错误!");
        }
    }

    /**
     * 话费充值, 根据用户输入的手机号和金额给出对应提示
     */
    public void charge() {
        System.out.println("*****话费充值*****");
        System.out.print("请输入需要充值的手机号: ");
        String num = input.next();
        System.out.print("请输入充值金额: ");
        double money = input.nextDouble();
        System.out.println(cardService.charge(num, money));
    }
}
