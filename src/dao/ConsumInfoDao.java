package dao;

import pojo.ConsumInfo;

import java.util.ArrayList;

/**
 * 消费详单数据类
 *
 * @author zhouxw
 * @date 2022/5/8
 */
public class ConsumInfoDao {
    /**
     * 存储消费详情的文件位置
     */
    private static final String CONSUM_INFO_DB_PATH = "src/db/consuminfo/";
    /**
     * 消费记录列表
     */
    private ArrayList<ConsumInfo> consumInfos;

    /**
     * 读取对应手机号的消费详情是否存在, 存在直接返回, 不存在则直接new一个
     *
     * @param number 手机号
     */
    private void load(String number) {
        String path = CONSUM_INFO_DB_PATH + number;
        consumInfos = FileTools.load(path);
        if (consumInfos == null) {
            consumInfos = new ArrayList<>();
        }
    }

    /**
     * 将序列化后的consumInfos存到指定的CONSUM_INFO_DB_PATH + number位置
     *
     * @param number 手机号
     */
    private void save(String number) {
        String path = CONSUM_INFO_DB_PATH + number;
        FileTools.save(path, consumInfos);
    }

    /**
     * 增加消费记录, 保存至文件
     */
    public void addConsumInfo(ConsumInfo consumInfo) {
        String num = consumInfo.getCardNumber();
        load(num);
        consumInfos.add(consumInfo);
        save(num);
    }

    /**
     * 获取对应卡号的消费记录
     *
     * @param num 手机号
     * @return 消费记录列表
     */
    public ArrayList<ConsumInfo> queryConsumInfo(String num) {
        String path = CONSUM_INFO_DB_PATH + num;
        return FileTools.load(path);
    }
}
