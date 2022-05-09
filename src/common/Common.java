package common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公共类
 *
 * @author Zhouxw
 * @date 2022/4/29 9:50
 */
public class Common {
    private final static DecimalFormat FORMAT = new DecimalFormat("#.0");
    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");

    /**
     * 格式化数字, 保留一位小数
     *
     * @param num 数字
     * @return 格式化后的数组
     */
    public static String dataFormat(double num) {
        return FORMAT.format(num);
    }

    public static String getNowDate() {
        Date date = new Date();
        return format.format(date);
    }

    public static void main(String[] args) {
        System.out.println(getNowDate());
    }
}
