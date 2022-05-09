package pojo;

import java.io.Serializable;

/**
 * 手机卡类
 *
 * @author Zhouxw
 * @date 2022/5/6 10:19
 */
public class MobileCard implements Serializable {
    /**
     * 卡号
     */
    private String cardNum;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 所属套餐
     */
    private ServicePackage servicePackage;
    /**
     * 当月消费金额
     */
    private double consumAmount;
    /**
     * 账户余额
     */
    private double money;
    /**
     * 当月实际通话时间
     */
    private int realTalkTime;
    /**
     * 当月实际短信条数
     */
    private int realSMSCount;
    /**
     * 当月上网流量
     */
    private int realFlow;

    public MobileCard() {
    }

    public MobileCard(String cardNum, String userName, String password, ServicePackage servicePackage, double money) {
        this.cardNum = cardNum;
        this.userName = userName;
        this.password = password;
        this.servicePackage = servicePackage;
        this.money = money;
        this.consumAmount = servicePackage.getPrice();
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getPassword() {
        return password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getUserName() {
        return userName;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public double getConsumAmount() {
        return consumAmount;
    }

    public void setConsumAmount(double consumAmount) {
        this.consumAmount = consumAmount;
    }

    public int getRealTalkTime() {
        return realTalkTime;
    }

    public void setRealTalkTime(int realTalkTime) {
        this.realTalkTime = realTalkTime;
    }

    public int getRealSMSCount() {
        return realSMSCount;
    }

    public void setRealSMSCount(int realSMSCount) {
        this.realSMSCount = realSMSCount;
    }

    public int getRealFlow() {
        return realFlow;
    }

    public void setRealFlow(int realFlow) {
        this.realFlow = realFlow;
    }
}
