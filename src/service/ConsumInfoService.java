package service;

import dao.ConsumInfoDao;
import pojo.ConsumInfo;

import java.util.ArrayList;

/**
 * 消费详单业务类
 *
 * @author zhouxw
 * @date 2022/5/8
 */
public class ConsumInfoService {
    private final ConsumInfoDao consumInfoDao = new ConsumInfoDao();

    /**
     * 遍历对应手机号的消费列表, 获取其消费详单
     *
     * @param num 手机号
     * @return 消费详单
     */
    public String showConsumInfo(String num) {
        StringBuilder detail = new StringBuilder(num + "本月消费详单:\n" + "序号 \t 类型 \t 用量 \t 时间\n");
        ArrayList<ConsumInfo> consumInfos = consumInfoDao.queryConsumInfo(num);
        if (consumInfos == null) {
            return num + "本月不存在消费记录!";
        }
        int no = 1;
        for (ConsumInfo consumInfo : consumInfos) {
            detail.append(no).append(" \t ").
                    append(consumInfo.getType().getDescription()).append(" \t ").
                    append(consumInfo.getConsumData()).append(consumInfo.getType().getUnit()).append(" \t ").
                    append(consumInfo.getDateTime()).append("\n");
            no++;
        }
        return detail.toString();
    }

    public static void main(String[] args) {
        new ConsumInfoService().showConsumInfo("13185971323");
    }
}
