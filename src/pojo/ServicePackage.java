package pojo;

import java.io.Serializable;

/**
 * 套餐的父类
 *
 * @author Zhouxw
 * @date 2022/5/6 10:19
 */
public abstract class ServicePackage implements Serializable {
    /**
     * 套餐月资费
     */
    private double price;
    /**
     * 套餐内通话分钟数
     */
    private int talkTime;
    /**
     * 套餐内短信条数
     */
    private int smsCount;
    /**
     * 套餐内上网流量
     */
    private int flow;

    /**
     * 套餐费用:
     * 通话时长:
     * 短信条数:
     * 上网流量:
     */
    public ServicePackage(double price, int talkTime, int smsCount, int flow) {
        this.price = price;
        this.talkTime = talkTime;
        this.smsCount = smsCount;
        this.flow = flow;
    }

    public double getPrice() {
        return price;
    }

    public int getTalkTime() {
        return talkTime;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public int getFlow() {
        return flow;
    }
}
