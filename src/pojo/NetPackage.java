package pojo;

/**
 * 网虫套餐, 单例
 *
 * @author Zhouxw
 * @date 2022/5/6 10:39
 */
public class NetPackage extends ServicePackage {
    private static NetPackage netPackage = new NetPackage();

    /**
     * 套餐费用:68元/月
     * 通话时长:0分钟
     * 短信条数:0条
     * 上网流量:3GB
     */
    public NetPackage() {
        super(68, 0, 0, 3 * 1024);
    }

    public static NetPackage getInstance() {
        return netPackage;
    }
}
