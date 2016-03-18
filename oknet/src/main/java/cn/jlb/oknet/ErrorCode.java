package cn.jlb.oknet;

/**
 * 错误码 常量
 * Created by zhangyunfei on 16/1/4.
 */
public class ErrorCode {
    private ErrorCode() {
    }

    public static int SUCCESS = 0;//: "成功",
    public static int PARAMETER_ERROR = 1;//: "参数错误",
    public static int SERVER_ERROR = 2;//: "程序内部错误",
    public static int PART_3_TIMEOUT = 3;//: "第三方接口超时",
    public static int OUT_INTERFACE_NO_EXIST = 4;//: "接口不存在",
    public static int OUT_INTERFACE_ERROR = 5;//: "外部接口错误",
    public static int AUTH_ERROR = 6;//: "鉴权失败",
    public static int NO_PROMENSTION = 7;//: "访问被禁止"

    public static int YOUHUIQUAN_NO_EXIST = 57000;//"优惠券不存在"
    public static int DELETE_CART_GOODS_ERROR = 57300;//"删除购物车商品失败"
    public static int ADD_CART_GOODS_ERROR = 57301;//:"商品添加购物车失败"
    public static int MODIFY_CART_GOODS_ERROR = 57302;//"更改购物车商品数量失败"
    public static int DELETE_LIKE_GOODS_ERROR = 57303;//"删除感兴趣商品失败"
    public static int UPDATE_SELECTED_ERROR = 57304;//"更新选中状态失败"
    public static int MOVE_GOODS_ERROR = 57305;//"商品迁移失败"

    public static int DISTRICT_NO_VALID = 58283;//: "商圈无效"

}
