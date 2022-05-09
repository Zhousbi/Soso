package common;

/**
 * 消费类型及超出套餐资费
 *
 * @author Zhouxw
 * @date 2022/5/5 16:55
 */
public enum ConsumType {
    /**
     * 超出的通话: 0.2元/分钟
     */
    TALK_TIME(0.2, "分钟", "通话"),
    /**
     * 超出的短信: 0.1元/条
     */
    SMS_COUNT(0.1, "条", "短信"),
    /**
     * 超出的上网流量: 0.1元/MB
     */
    FLOW(0.1, "MB", "上网");
    /**
     * 价格
     */
    private final double price;
    /**
     * 单位
     */
    private final String unit;
    /**
     * 说明
     */
    private final String description;

    ConsumType(double price, String unit, String description) {
        this.price = price;
        this.unit = unit;
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }
}
