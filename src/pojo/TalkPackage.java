package pojo;

/**
 * 话痨套餐,单例
 *
 * @author Zhouxw
 * @date 2022/5/6 10:24
 */
public class TalkPackage extends ServicePackage {
    private static TalkPackage talkPackage = new TalkPackage();

    /**
     * 套餐费用:58元/月
     * 通话时长:500分钟
     * 短信条数:30条
     * 上网流量:0MB
     */
    private TalkPackage() {
        super(58, 500, 30, 0);
    }

    public static TalkPackage getInstance() {
        return talkPackage;
    }
}
