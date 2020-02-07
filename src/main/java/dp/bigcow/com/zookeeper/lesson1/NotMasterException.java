package dp.bigcow.com.zookeeper.lesson1;

/**
 * create by suzhiwu on 2019/02/09
 */
public class NotMasterException extends Exception {

    public NotMasterException() {
    }

    public NotMasterException(String message) {
        super(message);
    }
}
