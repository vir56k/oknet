package zhangyf.vir56k.okhttpdemo;

/**
 * @author wqr
 *         api url and response code
 * @date 2014-11-6 下午7:00:56
 */
public class Apis {

    public static final String PREFIX_URL = "http://";
    /**
     * 柜体测试地址
     */
    private static final String GAEA_URL = "http://10.0.1.232:8889/gaea";
    private static final String CABZOOO_URL = "http://10.0.1.232:8889/cabzoo";
    private static final String PHAROS_URL =  "http://10.0.1.232:8889/pharos";
    private static final String CHAOS_URL = "http://10.0.1.232:8889/chaos";
    private static final String ACC_URL = "http://10.0.1.232:8889/acc";

    /**
     * @author wqr
     *         状态码
     * @date 2014-11-7 上午11:08:21
     */
    public class Code {
        // 1、系统状态码
        /**
         * 成功
         */
        public static final int CODE_SYS_SUCESS = 0;

        public static final int CODE_NET_SUCESS = 200;

        // 网络状态码

        /**
         * 网络错误
         */
        public static final int CODE_NO_NETWORK = 9999;

        /**
         * 没有初始化
         */
        public static final int CODE_SYS_NO_INIT = -1;

        // 服务器状态码
        /**
         * 服务器错误
         */
        public static final int CODE_SYS_ERROR = 500;

        /**
         * 路径不存在
         */
        public static final int CODE_SYS_404 = 404;

        /**
         * 路径不对或数据异常
         */
        public static final int CODE_SYS_PARSE = 555;

        /**
         * 失败
         */
        public static final int CODE_FAIL = 10001;
    }

    /**
     * @author wqr
     *         柜体接口
     * @date 2014-11-15 下午3:20:56
     */
    public class Cab_Urls {

        /**
         * 1、手机号注册及验证码发送(服务端逻辑：如果手机号已注册，则直接发送短信验证码，如果手机号不存在，则注册且发送短信验证码)
         */
        public static final String LOGIN_PHONE = CABZOOO_URL + "/cab/login/phone";

        /**
         * 获得短信验证码
         */
        public static final String LOGIN_GET_VERIFY_CODE = CABZOOO_URL + "/cab/pcode/send";

        /**
         * 获得动态密码
         */
        public static final String LOGIN_GET_DYNAMIC_PASSWORD = CABZOOO_URL + "/cab/login/dynamicpwd/get";
        /**
         * 使用动态密码登录
         */
        public static final String LOGIN_BY_VERIFY_CODE = CABZOOO_URL + "/cab/login/dynamicpwd";

        /**
         * 2、IC卡登陆
         */
        public static final String LOGIN_ICARD = CABZOOO_URL + "/cab/login/icard";
        /**
         * 3、投递、寄存完成
         */
        public static final String DELIVERY_NOTIFY = CABZOOO_URL + "/exp/deliver/notify";

        /**
         * 4、判断取件费用
         */
        public static final String PICKUP_CHECK_CHARGING = CABZOOO_URL + "/exp/pickup/check_charge";

        /**
         * 5、取件完成
         */
        public static final String PICKUP_NOTIFY = CABZOOO_URL + "/exp/pickup/notify";
        /**
         * 6、发送手机验证码
         */
        public static final String PCODE_SEND = CABZOOO_URL + "/cab/pcode/send";
        /**
         * 7、判断IC卡是否绑定用户(未使用)
         */
        public static final String ICARD_IS_BIND = CABZOOO_URL + "/cab/icard/isbind";
        /**
         * 8、绑定IC卡
         */
        public static final String ICARD_BIND = CABZOOO_URL + "/cab/icard/bind";
        /**
         * 9、解绑IC卡
         */
        public static final String ICARD_UNBIND = CABZOOO_URL + "/cab/icard/unbind";
        /**
         * 10、投递成功后, 修改收件人手机号
         */
        public static final String ORDER_UPDATE_PHONE = CABZOOO_URL + "/exp/order/update_phone";
        public static final String ORDER_UPDATE_PHONE_PUSH = CABZOOO_URL + "/cab/order/phone/update_ack";
//        "/cabzoo/cab/order/phone/update_ack" ;

        /**
         * 12、充值卡充值 [不用]
         */
        public static final String RECHARGE_CARD = CABZOOO_URL + "/cab/recharge/card";
        /**
         * 13、获取价格配置信息 (这里做成PUSH方式! 有变更推送到各个柜体, 客户端缓存!)
         */
        public static final String TARIFF_CONFIG = CABZOOO_URL + "/cab/charge/tariffconf";
        /**
         * 14、取得数据存放的ftp信息(暂时未用，数据来源于init接口)
         */
        public static final String DATABUS_FTPINFO = CABZOOO_URL + "/cab/databus/ftpinfo";
        /**
         * 15、得到柜体所在城市的天气预报
         */
        public static final String DATABUS_WEATHER = CABZOOO_URL + "/cab/databus/weather";
        /**
         * 16、上传柜体上订单数据
         */
        public static final String UPLOAD_CABORDER = CABZOOO_URL + "/exp/order/upload_caborder";
        /**
         * 17、下载之前上传的柜体订单数据
         */
        public static final String DOWNLOAD_CABORDER = CABZOOO_URL + "/exp/order/download_caborder";

        /**
         * 18 快递员取回
         */
        public static final String RETRIEVE_RETRIEVE_ORDER = CABZOOO_URL
                + "/exp/retrieve/retrieve_order";

        /**
         * 19、收件人通过手机号查询包裹(未使用)
         */
        public static final String ORDER_LIST = CABZOOO_URL + "/exp/order/order_list";

        /**
         * 20 获取公告信息
         */
        public static final String NOTICE_NOTICE_LIST = CABZOOO_URL + "/cab/notice/list";

        /**
         * 21 柜体信息初始化
         */
        public static final String APP_UPDATE_INIT = CABZOOO_URL + "/cab/cabinet/init";
        /**
         * 22 柜体时间校验
         */
        public static final String CAB_VERIFY_TIME = CABZOOO_URL + "/cab/cabinet/verify_time";
        /**
         * 23 微信支付
         */
        public static final String PAY_WEIXIN = CABZOOO_URL + "/cab/charge/weixin/qrcode_request";
        /**
         * 24 支付宝支付
         */
        public static final String PAY_ALIPAY = CABZOOO_URL + "/cab/charge/alipay/qrcode_request";
        /**
         * 25 历史投递查询
         */
        public static final String HISTORY_QUERY = CABZOOO_URL + "/cab/order/history_list";

        public static final String TRACK_UPLOAD = CABZOOO_URL + "/cab/track/upload";
        /**
         * 盘点
         */
        public static final String INVENTORY = CABZOOO_URL + "/cab/inventory/record";
        /**
         * 重发短信
         */
        public static final String RESEND_SEND_SMS = CABZOOO_URL + "/cab/order/send_sms";
        /**
         * 取件再次开箱时查询服务端格口状态
         */
        public static final String PICKUP_CHECK_REOPEN = CABZOOO_URL + "/cab/pickup/check_reopen";
        /**
         * 获取所有格口信息列表
         */
        public static final String GET_BOX_FREE_NEWS = CABZOOO_URL + "/cab/cell/getall_list";

        /**
         * 获取柜体空闲格口信息
         */
        public static final String IDLE_LIST = CABZOOO_URL + "/cab/cell/idle_list";
        /**
         * 柜体打开指定类型单个格口
         */
        public static final String OPEN_SIMPLE = CABZOOO_URL + "/cab/cell/open_simple";
        /**
         * 获取投递未取订单列表(滞留件)
         */
        public static final String DETENTION_LIST = CABZOOO_URL + "/cab/order/detention_list";
        /**
         * 获取滞留件费用
         */
        public static final String CHECK_CHARGE = CABZOOO_URL + "/cab/retrieve/check_charge";

        /**
         * 取件再次开箱时查询服务端格口状态
         */
        public static final String cell_getbycode = CABZOOO_URL + "/cab/cell/getbycode";

        /**
         * 3 消息处理确认(柜体app)
         */
        public static final String PUSH_PUSH_ACKING = CABZOOO_URL + "/cab/push/acking";

        /**
         * 4 手机投递,柜体订单确认
         */
        public static final String DELIVER_CABCONFIRM = CABZOOO_URL + "/exp/deliver/cabconfirm";

        /**
         * 5 取消投递
         */
        public static final String DELIVER_CABCANCEL_ACK = CABZOOO_URL
                + "/exp/deliver/cabcancel_ack";
        /**
         * 6 通知格口打开失败
         */
        public static final String DELIVER_OPEN_FAIL = CABZOOO_URL + "/exp/deliver/open/fail";

        /**
         * 7 、退回确认
         */
        public static final String DELIVER_WITHDRAW_CONFIRM = CABZOOO_URL + "/exp/withdraw/confirm";
        /**
         * 8 、柜体接受订单确认
         */
        public static final String DELIVER_CAB_RECV_ACK = CABZOOO_URL
                + "/szoo2/delivery/cab/recv_ack";
        /**
         * 9. 获取取件金额
         */
        public static final String GET_MONEY = CABZOOO_URL + "/cab/order/charge_info";
        /**
         * 获取柜体可用及空闲的格口列表
         */
        public static final String CELL_AVAIL_LIST = CABZOOO_URL + "/cab/cell/avail_list";
        /**
         * 获取柜体可用及空闲的格口列表
         */
        public static final String CELL_UNLOCK = CABZOOO_URL + "/cab/cell/unlock";

        /**
         * 空箱取走
         */
        public static final String EMPTY_BOX_GET = CABZOOO_URL + "/cab/pickup/abnormal";

        /**
         * 获取通过手机号查询的html的url
         */
        public static final String GET_SELECT_HTML = CABZOOO_URL + "/cab/order/phone_search";

        /**
         * 获取充值金额列表
         */
        public static final String RECHARGE_MONEY_LIST = CABZOOO_URL + "/cab/activity/forecharge";
        /**
         * 通过手机号或者快递单号进行查询
         */
        public static final String EXPRESS_SEARCH = CABZOOO_URL + "/cab/order/search";

        /**
         * 重发短信 带有次数限制
         */
        public static final String RESEND_SMS = CABZOOO_URL + "/cab/order/repeat_sms";

        /**
         * 投币支付
         */
        public static final String PAY_COIN = CABZOOO_URL + "/cab/pickup/chargebycoin";

        /**
         * 批量发送短信提醒
         */
        public static final String BATCH_SEND_SMS = CABZOOO_URL + "/cab/order/batch/sms/notify";

        /**
         * 通过手机号或者快递单号进行查询
         */
        public static final String INQUERY_SEARCH_BY_WAYBILL_OR_PHONE = CABZOOO_URL + "/cab/order/consignee/search";
        /**
         * 上报分配格口结果
         */
        public static final String CELL_ALLOCATE_BYCAB = CABZOOO_URL + "/cab/cell/allocate/bycab";
        /**
         * 取消柜体分配格口确认
         */
        public static final String CELL_ALLOCATE_CANCEL_ACK = CABZOOO_URL + "/cab/cell/allocate/cancel_ack";

        /**
         * 更新动态码
         */
        public static final String LOGIN_MODIFY_PASSWORD_CODE = CABZOOO_URL + "/cab/login/dynamicpwd/update";

        /**
         * 所有版本升级
         */
        public static final String APP_UPDATE = CABZOOO_URL + "/cab/versions/upgrade";

        /**
         * 单一版本升级
         */
        public static final String APP_Single_UPDATE = CABZOOO_URL + "/cab/versions/single_upgrade";
    }

    public class GAEA_URLS {

        /**
         * 26 充值活动
         */
        public static final String CAB_ACTIVITY_FORECHARGE = GAEA_URL + "/cab/activity/forecharge";
        /**
         * 通知信息
         */
        public static final String CAB_NOTICE_LIST = GAEA_URL + "/cab/notice/list";
        /**
         * 广告信息
         */
        public static final String CAB_ADVERT_LIST = GAEA_URL + "/cab/advert/list";
    }

    public class SRV_URL {
        /**
         * 域名解析配置
         */
        public static final String DOMAIN_LIST = CHAOS_URL + "/domain/list";
    }

    public class ACC_URL {
        /**
         * 1、投币充值（快递员）
         */
        public static final String COURIER_RECHARGE_COIN = ACC_URL + "/cab/courier/recharge/bycoin";
        /**
         * 2、投递充值（用户）
         */
        public static final String USER_RECHARGE_COIN = ACC_URL + "/cab/user/recharge/bycoin";
        /**
         * 3、充值记录（快递员）
         */
        public static final String COURIER_RECHARGE_LIST = ACC_URL + "/cab/courier/recharge/list";
        /**
         * 4、支付宝充值页面请求（柜体）
         */
        public static final String COURIER_PAY_ALIPAY_URL = ACC_URL
                + "/cab/courier/recharge/alipay/qrcode_request";

        /**
         * 支付宝充值页面请求（用户）
         */
        public static final String USER_PAY_ALIPAY_URL = ACC_URL
                + "/cab/user/recharge/alipay/qrcode_request";
        /**
         * 5、银联充值
         */
        public static final String ACC_UNIONPAY_URL = ACC_URL + "/wrecharge/unionpay/request";
        /**
         * 6、type：1表示硬币充值 2：表示支付宝充值 3：表示银联充值
         */
        public static final String ACC_FAIL = ACC_URL + "/wrecharge/recharge/question";

        /**
         * 7.快递员微信充值二维码
         */
        public static final String COURIER_RECHARGE_WEIXIN = ACC_URL + "/cab/courier/recharge/weixin/qrcode_request";

        /**
         * 8.用户微信充值二维码
         */
        public static final String USER_RECHARGE_WEIXIN = ACC_URL + "/cab/user/recharge/weixin/qrcode_request";
    }

    public class PHAROS_URLS {
        /**
         * 获取通信服务器地址
         */
        public static final String TICKET_APPLY = PHAROS_URL + "/ticket/apply";
    }

    /**
     * @author wqr
     *         推送消息类型
     * @date 2014-11-15 下午3:27:06
     */
    public class PushType {
        /**
         * 关机
         */
        public static final int TYPE_SHUTDOWN = 1;

        /**
         * 重启
         */
        public static final int TYPE_REBOOT = 2;

        /**
         * 软件升级
         */
        public static final int TYPE_UPGRADE_UI = 3;
        /**
         * 硬件升级
         */
        public static final int TYPE_UPGRADE_HARD = 4;

        /**
         * 上传日志
         */
        public static final int TYPE_UPLOAD_LOG = 5;

        /**
         * 上传表单数据
         */
        public static final int TYPE_UPLOAD_ORDER = 6;

        /**
         * 下载表单数据
         */
        public static final int TYPE_DOWNLOAD_ORDER = 7;

        /**
         * 清除缓存数据
         */
        public static final int TYPE_CLEAR_CACHE = 8;
        /**
         * 更新系统
         */
        public static final int TYPE_UPGRADE_SYS = 9;

        /**
         * 投递开箱
         */
        public static final int TYPE_DELIVERY_OPEN = 100;

        /**
         * 取件开箱
         */
        public static final int TYPE_CONSIGNEE_OPEN = 101;
        /**
         * 取消投递开箱
         */
        public static final int TYPE_CANCEL_CONSIGNEE_OPEN = 102;
        /**
         * 密码更新
         */
        public static final int TYPE_PASSWORD_UPDATE = 103;
        /**
         * 回退快件开箱
         */
        public static final int TYPE_FALLBACK_OPEN = 104;

        /**
         * 更新订单信息(消息确认, 更新订单信息, 请求释放格口）
         */
        public static final int TYPE_UPDATE_ORDER = 105;
        /**
         * 只开箱，状态不变
         */
        public static final int TYPE_OPEN = 106;

        /**
         * 管理员开箱
         */
        public static final int TYPE_ADMIN_OPEN = 107;

        /**
         * 删除订单
         */
        public static final int TYPE_DELETE_ORDER = 108;

        /**
         * 批量打开格口
         */
        public static final int TYPE_OPEN_BATCH = 109;
        /**
         * 添加扫码枪确认已完成投递的订单
         */
        public static final int TYPE_ACCEPT_ORDER = 110;
        /**
         * 111: 快递员取回消息(消息确认、开箱、删除订单)
         */
        public static final int TYPE_RETRIEVE_ORDER = 111;
        /**
         * 112: 发送服务端柜体可用格口(消息确认，开箱)
         */
        public static final int TYPE_ALLOCATEBYCAB = 112;
        /**
         * 113: 发送取消投递(消息确认，开箱，释放柜体格口)
         */
        public static final int TYPE_ALLOCATE_CANCELACK = 113;
        /**
         * 计费配置更新
         */
        public static final int TYPE_CHARGING_UPDATE = 200;
        /**
         * 充值成功通知
         */
        public static final int TYPE_RECHARGE_SUCCESS = 201;
    }
}
