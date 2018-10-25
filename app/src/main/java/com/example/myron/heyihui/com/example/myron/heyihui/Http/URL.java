package com.example.myron.heyihui.com.example.myron.heyihui.Http;

import android.util.Log;

import com.example.myron.heyihui.com.example.myron.heyihui.fragment.HomeFragment;

import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLEngineResult;


/**
 * Created by Jason on 2017/11/2.
 */

public class URL {
//    public static void initUrl(Boolean debug) {
//        if (debug) {
//            LOGIN = "http://10.10.10.88:8080/scm/UserController/loginCode";
//            GETRE_ORDER = "http://10.10.10.88:8080/scm/hyh/order";
//        } else {
//            LOGIN = HOSTNAME + SERVERNAME + "/UserController/loginCode";//登录   //正式服没有服务器名字
//            GETRE_ORDER = HOSTNAME + SERVERNAME + "/order";
//        }
//    }
//   public static String HOSTNAME = "http://10.10.10.157:8080/"; //xuyang
//    public static String SERVERNAME = "scm.server"; // xuyang
    public static String HOSTNAME = "http://10.10.10.88:8080/scm/"; //测试；
//    public static String HOSTNAME ="http://gscm.hyhscm.com/"; //正式;
    public static String SERVERNAME = "hyh"; //正式
    public static String IMGPATH = "http://hyh.hyhscm.com/";
    public static String SENDVERIFYCODE = HOSTNAME  + "Verification/code";   //发送验证码
    //    public static  String LOGIN = HOSTNAME+SERVERNAME+"/UserController/loginCode";//登录   //正式服没有服务器名字
    public static String LOGIN = HOSTNAME + "UserController/loginCode";//登录   //正式服没有服务器名字
    public static String GETTAG = HOSTNAME + "GoodscategoryController/query";//获取产品分类标签
    public static String GETPRODUCTS = HOSTNAME + SERVERNAME + "/goods";//产品别表
    public static String GET_PRODUCT = HOSTNAME + SERVERNAME + "/getGoods"; //获取产品详情；
    public static String GETRE_ORDERS = HOSTNAME + SERVERNAME + "/order";  //获取订单列表
    public static String GETRE_ORDER = HOSTNAME + SERVERNAME + "/getOrder";  //获取订单内容列表
    //    http://10.10.10.88:8080/scm/hyh/orderDoc?id=27
    public static String GET_ORDER_DOC = HOSTNAME + SERVERNAME + "/orderDoc";  //获取订单跟踪
    //    http://10.10.10.88:8080/scm/hyh/account
    public static String GET_MONTH_IN = HOSTNAME + SERVERNAME + "/account";  //获取月收入
    public static String GET_JF = HOSTNAME + SERVERNAME + "/integral";  //获取积分
    public static String GET_TOTAL_JF = HOSTNAME + SERVERNAME + "/integrals";  //获取积分
    //http://10.10.10.88:8080/scm/MessageController/query
    public static String GETMESSAGES = HOSTNAME  + "/MessageController/query";  //获取消息
    public static String GETHOMEDATA = HOSTNAME  + SERVERNAME+"/orders";  //首页数据
    public static String GETACCOUNT = HOSTNAME  +SERVERNAME+ "/accounts";  //账户
    public static String GETBANNER = "http://hyh.hyhscm.com/hyhs/index";
//    http://10.10.10.88:8080/scm/hyh/purchase
   public static String  POSTREQUEST =  HOSTNAME  +SERVERNAME+ "/purchase"; //需求提交
   public static String POSTISSUE =  HOSTNAME  + "FeedbackController/save";//建议提交
   public static String GETMALLPRODUCT = HOSTNAME + SERVERNAME +"hyhMall/AdOrPros";// mall home product 1/小 2/大  3首页广告 4 搜索广告
//UserController/loginCode
//    帐户信息：HOSTNAME/accounts
//    积分信息：HOSTNAME/integrals  2222
//    订单数：HOSTNAME/orders
//    产品列表：HOSTNAME/goods  111
//    产品明细：HOSTNAME/getGoods  111
//    产品分类：HOSTNAME/GoodscategoryController/query  1111
//    验证码：HOSTNAME/Verification/code  1111
//    需求\支出列表:HOSTNAME/order   2222
//    订单产品:HOSTNAME/getOrder  2222
//    订单跟踪:HOSTNAME/orderDoc  2222
//    收支列表:HOSTNAME/account  2222
//    消息列表:HOSTNAME/MessageController/query
//    提交需求:HOSTNAME/purchase
//    意见:HOSTNAME/FeedbackController/save
//    积分:HOSTNAME/integral  2222
    //  accounts?t=0 本月数据  , t= 1 上月 数据； 收入和支出


//    ResponseResult order(String startDate, String endDate, String t, String s, String condition);
//
//    ResponseResult getOrder(String id);
//
//    ResponseResult orders(String startDate, String endDate, String t);
//
//    ResponseResult purchase(GorderBean bean, String type);
//
//    ResponseResult orderDoc(String id, String condition);
//
//    ResponseResult goods(String condition, String n, String c);
//
//    ResponseResult getGoods(String id);
//
//    ResponseResult account(String startDate, String endDate, String t, String condition);
//
//    ResponseResult getAccount(String id);
//
//    ResponseResult accounts(String startDate, String endDate, String t, String condition);
//
//    ResponseResult companys(String startDate, String endDate, String t, String condition);
//
//    ResponseResult integral(String startDate, String endDate, String t, String condition);
//
//    ResponseResult getIntegral(String id);
//
//    ResponseResult integrals(String startDate, String endDate, String t, String condition);
//
//    ResponseResult company(String condition);
}
