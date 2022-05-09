package dao;

import pojo.MobileCard;

import java.util.HashMap;

/**
 * 操作已注册用户
 *
 * @author Zhouxw
 * @date 2022/5/6 13:54
 */
public class CardsDao {
    /**
     * 存储已注册用户的文件位置
     */
    private static final String CARD_DB_PATH = "src/db/cards";
    /**
     * 已注册用户:
     * key:已注册的手机号,value:手机卡对象
     */
    private HashMap<String, MobileCard> cards;

    public CardsDao() {
        load();
    }

    /**
     * 将序列化后的card存到指定的CARD_DB_PATH = "src/db/cards"位置
     */
    private void save() {
        FileTools.save(CARD_DB_PATH, cards);
    }

    /**
     * 从指定路径将对象反序列化加载到cards, 如果反序列化后对象为null则重新new一个对象
     */
    private void load() {
        cards = FileTools.load(CARD_DB_PATH);
        if (cards == null) {
            cards = new HashMap<>();
        }
    }

    /**
     * 反序列化cards, 增加手机号后再将其序列化
     *
     * @param card 需要存储的手机卡对象
     */
    public void addCard(MobileCard card) {
        load();
        cards.put(card.getCardNum(), card);
        save();
    }

    /**
     * 通过手机号将指定手机号从注册用户中删除
     *
     * @param num 手机号
     */
    public void delCardByNum(String num) {
        load();
        cards.remove(num);
        save();
    }

    /**
     * 更新指定手机卡的信息
     *
     * @param card 手机卡
     */
    public void update(MobileCard card) {
        addCard(card);
    }

    /**
     * 从cards中查找指定的手机号, 存在时返回对应的手机卡对象, 否则返回null
     *
     * @param num 手机号
     * @return ...
     */
    public MobileCard findByNum(String num) {
        load();
        return cards.get(num);
    }
}
