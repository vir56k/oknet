package cn.jlb.oknet;

import java.io.Serializable;


/**
 * 约定好的，固定的返回信息 格式。
 */
public class CommonMessage implements Serializable {
    public int code;
    public String msg;
    public String body;
}