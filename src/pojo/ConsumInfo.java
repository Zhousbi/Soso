package pojo;

import common.ConsumType;

import java.io.Serializable;

/**
 * 消费信息
 *
 * @author Zhouxw
 * @date 2022/4/29 9:50
 */
public class ConsumInfo implements Serializable {
    /**
     * 卡号
     */
    private String cardNumber;
    /**
     * 消费类型
     */
    private ConsumType type;
    /**
     * 用量
     */
    private int consumData;
    /**
     * 消费时间
     */
    private String dateTime;

    public ConsumInfo(String cardNumber, ConsumType type, int consumData, String dateTime) {
        this.cardNumber = cardNumber;
        this.type = type;
        this.consumData = consumData;
        this.dateTime = dateTime;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public ConsumType getType() {
        return type;
    }

    public int getConsumData() {
        return consumData;
    }

    public String getDateTime() {
        return dateTime;
    }
}
