package cn.jlb.oknet;

/**
 * 当服务器返回了一个 非0的错误时
 * 自定义异常
 */
public class NoZeroException extends Exception {
    private static final long serialVersionUID = -8642978599404639923L;
    private int code;
    private String msg;
    private String body;

    public NoZeroException(int code, String msg, String body) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return String.format("code=%s, msg=%s, body=%s", code, msg, body);
    }
}
