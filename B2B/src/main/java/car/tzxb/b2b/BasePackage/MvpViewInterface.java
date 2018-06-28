package car.tzxb.b2b.BasePackage;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public interface MvpViewInterface<T> {

    /**
     * 显示数据
     */
    void showData(T t);

    /**
     * 开启等待框
     */
    void  showLoading();

    /**
     * 关闭等待框
     */
    void closeLoading();

    /**
     * 显示错误的结果
     */
    void showErro();
}
