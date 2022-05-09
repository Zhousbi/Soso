package service;

import common.Common;
import common.ConsumType;
import dao.CardsDao;
import dao.ConsumInfoDao;
import pojo.ConsumInfo;
import pojo.MobileCard;
import pojo.ServicePackage;

import java.util.HashMap;
import java.util.Random;

/**
 * 手机卡业务类
 *
 * @author Zhouxw
 * @date 2022/5/6 15:04
 */
public class MobileCardService {
    /**
     * 手机号长度
     */
    private final static int NUMBER_LENGTH = 11;
    /**
     * 手机号第二位(号段)
     */
    private final static int[] SECOND_NUMBER = {3, 5, 7, 8, 9};
    /**
     * 套餐类名和中文名映射表
     */
    private final static HashMap<String, String> PACKAGE_NAME = new HashMap<>();

    /*
     * 初始化套餐类名和中文名映射表
     */
    static {
        PACKAGE_NAME.put("TalkPackage", "话痨套餐");
        PACKAGE_NAME.put("NetPackage", "网虫套餐");
        PACKAGE_NAME.put("SuperPackage", "超人套餐");
    }

    private final CardsDao cardsDao = new CardsDao();
    private final ConsumInfoDao consumInfoDao = new ConsumInfoDao();
    private ConsumInfo consumInfo;
    private MobileCard card;

    /**
     * 注册新用户, 将传如变量封装成对象, 传入dao层保存
     *
     * @param number         手机号
     * @param username       用户名
     * @param password       密码
     * @param servicePackage 套餐类型
     * @param money          账户余额
     */
    public void register(String number, String username, String password, ServicePackage servicePackage, double money) {
        card = new MobileCard(number, username, password, servicePackage, money);
        cardsDao.addCard(card);
        System.out.println("注册成功! 卡号:" + card.getCardNum() +
                ", 用户名:" + card.getUserName() +
                ", 当前余额:" + card.getMoney() + "元");
        packageInfo(card);
    }

    /**
     * 打印手机卡对应套餐信息
     *
     * @param card 手机卡
     */
    public void packageInfo(MobileCard card) {
        StringBuffer info = new StringBuffer();
        ServicePackage servicePackage = card.getServicePackage();
        // 添加套餐名
        info.append(PACKAGE_NAME.get(servicePackage.getClass().getSimpleName())).append(": ");
        // 通话时长
        if (servicePackage.getTalkTime() != 0) {
            info.append("通话时长为").append(servicePackage.getTalkTime()).append("分钟/月,");
        }
        // 短信条数
        if (servicePackage.getSmsCount() != 0) {
            info.append("短信条数为").append(servicePackage.getSmsCount()).append("条/月,");
        }
        // 上网流量
        if (servicePackage.getTalkTime() != 0) {
            info.append("上网流量为").append(servicePackage.getFlow() / 1024).append("GB/月,");
        }
        System.out.println(info);
    }

    /**
     * 获取对应卡号的套餐余额
     *
     * @param num 手机号
     * @return 余额信息
     */
    public String showRemainDetail(String num) {
        card = cardsDao.findByNum(num);
        StringBuilder detail = new StringBuilder();
        int talkTime = card.getServicePackage().getTalkTime() - card.getRealTalkTime();
        int SMSCount = card.getServicePackage().getSmsCount() - card.getRealSMSCount();
        int flow = card.getServicePackage().getFlow() - card.getRealFlow();
        detail.append("您的卡号是: ").append(num).append(", 套餐内剩余:\n").
                append("通话时长: ").append(Math.max(talkTime, 0)).append("分钟\n").
                append("短信条数: ").append(Math.max(SMSCount, 0)).append("条\n").
                append("上网流量: ").append(Math.max(flow / 1024.0, 0)).append("GB");
        return detail.toString();
    }
    /**
     * 登录
     *
     * @param num      手机号
     * @param password 密码
     * @return 是否登录成功
     */
    public boolean login(String num, String password) {
        card = cardsDao.findByNum(num);
        if (card != null) {
            return password.equals(card.getPassword());
        } else {
            return false;
        }
    }

    /**
     * 充值
     *
     * @param num   手机号
     * @param money 金额
     */
    public String charge(String num, double money) {
        card = cardsDao.findByNum(num);
        if (card == null) {
            return "手机号不存在, 请重试!";
        }
        card.setMoney(card.getMoney() + money);
        cardsDao.update(card);
        return "充值成功, 当前账户余额" + Common.dataFormat(card.getMoney()) + "元";
    }

    /**
     * 使用soso通话 短信或上网, 超过套餐的时会给出提示, 账户余额不足时会中止操作
     *
     * @param num  手机号
     * @param type 消费类型
     * @param data 用量
     */
    public void useSoso(String num, ConsumType type, int data) {
        switch (type) {
            case TALK_TIME:
                call(num, data);
                break;
            case SMS_COUNT:
                send(num, data);
                break;
            case FLOW:
                surfing(num, data);
                break;
            default:
        }
        System.out.println("使用结束!");
    }

    /**
     * 打电话功能
     *
     * @param num  手机号
     * @param data 通话时间
     */
    public void call(String num, int data) {
        card = cardsDao.findByNum(num);
        // 套餐内通话时间
        int packageTalkTime = card.getServicePackage().getTalkTime();
        // 实际通话时间
        int realTalkTime = card.getRealTalkTime();
        // 账户余额
        double money = card.getMoney();
        // 当月总消费
        double consumAmount = card.getConsumAmount();
        // 当套餐已用完时提示用户
        if (realTalkTime > packageTalkTime) {
            System.out.println("套餐内分钟数不足, 将使用话费, 请注意通话时间!");
        }
        // 模拟通话, 每分钟计费一次
        int talkTime = 0;
        for (; talkTime < data; talkTime++) {
            if (money < 0) {
                System.out.println("已通话" + talkTime + "分钟, 余额不足, 请充值后重试!");
                break;
            }
            int beyond = realTalkTime - packageTalkTime;
            if (beyond > 0) {
                money -= ConsumType.TALK_TIME.getPrice();
                consumAmount += ConsumType.TALK_TIME.getPrice();
            }
            realTalkTime++;
        }
        // 更新消费记录列表
        consumInfo = new ConsumInfo(num, ConsumType.TALK_TIME, talkTime, Common.getNowDate());
        consumInfoDao.addConsumInfo(consumInfo);
        // 更新话费余额 总计消费
        card.setMoney(money);
        card.setRealTalkTime(realTalkTime);
        card.setConsumAmount(consumAmount);
        cardsDao.update(card);
    }

    /**
     * 发短信功能
     *
     * @param num  手机号
     * @param data 短信条数
     */
    public void send(String num, int data) {
        card = cardsDao.findByNum(num);
        // 套餐内短信条数
        int packageSMSCount = card.getServicePackage().getSmsCount();
        // 实际使用短信数
        int realSMSCount = card.getRealSMSCount();
        // 账户余额
        double money = card.getMoney();
        // 当月总消费
        double consumAmount = card.getConsumAmount();
        // 当套餐已用完时提示用户
        if (realSMSCount > packageSMSCount) {
            System.out.println("套餐内分短信条数不足, 将使用话费, 请注意通话时间!");
        }
        // 模拟发短信, 每条短信计费一次
        int SMSCount = 0;
        for (; SMSCount < data; SMSCount++) {
            if (money < 0) {
                System.out.println("余额不足, 不能发送短信, 请充值!");
                break;
            }
            int beyond = realSMSCount - packageSMSCount;
            if (beyond > 0) {
                money -= ConsumType.SMS_COUNT.getPrice();
                consumAmount += ConsumType.SMS_COUNT.getPrice();
            }
            realSMSCount++;
        }
        // 更新消费记录列表
        consumInfo = new ConsumInfo(num, ConsumType.SMS_COUNT, SMSCount, Common.getNowDate());
        consumInfoDao.addConsumInfo(consumInfo);
        // 更新数据并存储
        card.setMoney(money);
        card.setRealSMSCount(realSMSCount);
        card.setConsumAmount(consumAmount);
        cardsDao.update(card);
    }

    /**
     * 上网功能
     *
     * @param num  手机号
     * @param data 使用流量
     */
    public void surfing(String num, int data) {
        card = cardsDao.findByNum(num);
        // 套餐内短信条数
        int packageFlow = card.getServicePackage().getFlow();
        // 实际使用短信数
        int realFlow = card.getRealFlow();
        // 账户余额
        double money = card.getMoney();
        // 当月总消费
        double consumAmount = card.getConsumAmount();
        // 当套餐已用完时提示用户
        if (realFlow > packageFlow) {
            System.out.println("套餐内流量不足, 将使用话费, 请注意通话时间!");
        }
        // 模拟上网, 每使用1MB流量计费一次
        int flow = 0;
        for (; flow < data; flow++) {
            if (money < 0) {
                System.out.println("本次已上网已使用流量" + flow + "MB, 话费余额不足, 请充值后重试");
                break;
            }
            int beyond = realFlow - packageFlow;
            if (beyond > 0) {
                money -= ConsumType.FLOW.getPrice();
                consumAmount += ConsumType.FLOW.getPrice();
            }
            realFlow++;
        }
        // 更新消费记录列表
        consumInfo = new ConsumInfo(num, ConsumType.FLOW, flow, Common.getNowDate());
        consumInfoDao.addConsumInfo(consumInfo);
        // 更新数据并存储
        card.setMoney(money);
        card.setRealFlow(realFlow);
        card.setConsumAmount(consumAmount);
        cardsDao.update(card);
    }

    /**
     * 显示本月消费详情
     *
     * @param number 手机号
     * @return 消费详情
     */
    public String showAmountDetail(String number) {
        card = cardsDao.findByNum(number);
        return "*****本月账单详情*****\n您的卡号: " + number + ",当月账单:\n" +
                "套餐资费: " + card.getServicePackage().getPrice() + "元\n" +
                "合计: " + Common.dataFormat(card.getConsumAmount()) + "元\n" +
                "账户余额: " + Common.dataFormat(card.getMoney()) + "元";
    }

    /**
     * 生成随机手机号
     *
     * @return 手机号
     */
    private String creatNumber() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        // 随机生成手机号前两位
        sb.append(1).append(SECOND_NUMBER[random.nextInt(SECOND_NUMBER.length)]);
        while (sb.length() < NUMBER_LENGTH) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 生成一组随机的手机号
     *
     * @param count 手机号个数
     * @return 一组随机手机号
     */
    public String[] getNumbers(int count) {
        String[] numbers = new String[count];
        int index = 0;
        while (index < count) {
            String number = creatNumber();
            if (cardsDao.findByNum(number) != null) {
                continue;
            }
            numbers[index] = number;
            index++;
        }
        return numbers;
    }
}
