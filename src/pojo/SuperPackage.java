package pojo;

/**
 * 超人套餐
 *
 * @author Zhouxw
 * @date 2022/5/6 10:45
 */
public class SuperPackage extends ServicePackage {
    private static SuperPackage superPackage = new SuperPackage();

    /**
     * 套餐费用:78元/月
     * 通话时长:200分钟
     * 短信条数:50条
     * 上网流量:1GB
     */
    public SuperPackage() {
        super(78,200,50, 1024);
    }
    public static SuperPackage getInstance(){
        return superPackage;
    }
}
