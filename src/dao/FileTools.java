package dao;

import java.io.*;

/**
 * 文件工具类
 *
 * @author Zhouxw
 * @date 2022/5/6 14:01
 */
public class FileTools {
    /**
     * 将序列化对象保存到指定位置
     *
     * @param path 文件位置
     * @param e    对象
     * @param <E>  对象类型
     */
    public static <E> void save(String path, E e) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(e);
            oos.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 从指定路径反序列化(读取)
     *
     * @param path 文件路径
     * @param <E>  对象类型
     * @return 对象
     */
    public static <E> E load(String path) {
        E e = null;
        // 如果文件存在
        if (exists(path)) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
                e = (E) ois.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return e;
    }
    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return 是否
     */
    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }
}
