package common;

import pojo.NetPackage;
import pojo.ServicePackage;
import pojo.SuperPackage;
import pojo.TalkPackage;

/**
 * 套餐工厂
 *
 * @author Zhouxw
 * @date 2022/5/6 10:48
 */
public enum PackagesFactory {
    /**
     * 话痨套餐
     */
    TALK_PACKAGE(TalkPackage.getInstance()),
    /**
     * 网虫套餐
     */
    NET_PACKAGE(NetPackage.getInstance()),
    /**
     * 超人套餐
     */
    SUPER_PACKAGE(SuperPackage.getInstance());

    private final ServicePackage servicePackage;

    PackagesFactory(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public static ServicePackage getServicePackage(PackagesFactory packagesFactory) {
        return packagesFactory.getServicePackage();
    }

    private ServicePackage getServicePackage() {
        return servicePackage;
    }
}
