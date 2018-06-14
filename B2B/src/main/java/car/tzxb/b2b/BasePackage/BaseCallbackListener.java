package car.tzxb.b2b.BasePackage;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public interface BaseCallbackListener<T> {

    /**
     * 当任务成功的时候回调
     *
     * @param result 任务请求结果
     */
    void onSucceed(T result);

    /**
     * 当任务执行过程中出错的时候回调
     *
     * @param errorMsg 错误消息
     */
    void onError(Throwable errorMsg);
}
