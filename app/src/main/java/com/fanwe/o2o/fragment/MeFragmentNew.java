package com.fanwe.o2o.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AccountManageAcitivty;
import com.fanwe.o2o.activity.ActivityCouponActivity;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.activity.BindMobileActivity;
import com.fanwe.o2o.activity.BuyOrderActivity;
import com.fanwe.o2o.activity.ConsumeCouponActivity;
import com.fanwe.o2o.activity.CouponListActivity;
import com.fanwe.o2o.activity.KeepActivity;
import com.fanwe.o2o.activity.LoginActivity;
import com.fanwe.o2o.activity.MessageCenterActivity;
import com.fanwe.o2o.activity.OrderRefundListActivity;
import com.fanwe.o2o.activity.OrderListActivity;
import com.fanwe.o2o.activity.SettingActivity;
import com.fanwe.o2o.activity.ShippingAddressActivity;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.dao.InitActModelDao;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppUserCenterWapIndexActModel;
import com.fanwe.o2o.model.Init_indexActModel;
import com.fanwe.o2o.utils.GlideUtil;

import org.xutils.view.annotation.ViewInject;

import static com.fanwe.o2o.activity.ConsumeCouponActivity.EXTRA_COUPON_NAME;

/**
 * 我的
 * Created by Administrator on 2016/12/8.
 */

public class MeFragmentNew extends BaseFragment
{
    @ViewInject(R.id.iv_setting)
    private ImageView iv_setting;
    @ViewInject(R.id.iv_news)
    private ImageView iv_news;
    @ViewInject(R.id.iv_point)
    private ImageView iv_point;
    @ViewInject(R.id.ll_login)
    private LinearLayout ll_login;
    @ViewInject(R.id.iv_header)
    private ImageView iv_header;//头像
    @ViewInject(R.id.tv_name)
    private TextView tv_name;//用户名
    @ViewInject(R.id.tv_account_manage)
    private TextView tv_account_manage;//账户管理
    @ViewInject(R.id.ll_group_friend)
    private LinearLayout ll_group_friend;//会员，朋友圈
    @ViewInject(R.id.ll_user_group)
    private LinearLayout ll_user_group;//会员
    @ViewInject(R.id.tv_user_group)
    private TextView tv_user_group;
    @ViewInject(R.id.ll_circle_friend)
    private LinearLayout ll_circle_friend;
    @ViewInject(R.id.ll_balance)
    private LinearLayout ll_balance;
    @ViewInject(R.id.tv_balance)
    private TextView tv_balance;//余额
    @ViewInject(R.id.ll_integral)
    private LinearLayout ll_integral;
    @ViewInject(R.id.tv_integral)
    private TextView tv_integral;//积分
    @ViewInject(R.id.ll_bind)
    private LinearLayout ll_bind;
    @ViewInject(R.id.tv_bind_mobile)
    private TextView tv_bind_mobile;//绑定手机号
    @ViewInject(R.id.iv_dismiss)
    private ImageView iv_dismiss;
    @ViewInject(R.id.rl_check_order)
    private RelativeLayout rl_check_order;
    @ViewInject(R.id.tv_check_order)
    private TextView tv_check_order;//查看全部订单
    @ViewInject(R.id.ll_pen_pay)
    private LinearLayout ll_pen_pay;//待付款
    @ViewInject(R.id.tv_pen_pay)
    private TextView tv_pen_pay;
    @ViewInject(R.id.rl_dis)
    private RelativeLayout rl_dis;
    @ViewInject(R.id.ll_pen_sure)
    private LinearLayout ll_pen_sure;//待确认
    @ViewInject(R.id.tv_pen_sure)
    private TextView tv_pen_sure;
    @ViewInject(R.id.ll_evaluate)
    private LinearLayout ll_evaluate;//待评价
    @ViewInject(R.id.tv_evaluate)
    private TextView tv_evaluate;
    @ViewInject(R.id.ll_refund)
    private LinearLayout ll_refund;//退款
    @ViewInject(R.id.ll_bill)
    private LinearLayout ll_bill;//买单
    @ViewInject(R.id.ll_red_bag)
    private LinearLayout ll_red_bag;//红包
    @ViewInject(R.id.tv_red_bag)
    private TextView tv_red_bag;
    @ViewInject(R.id.ll_consume)
    private LinearLayout ll_consume;//消费券
    @ViewInject(R.id.tv_consume)
    private TextView tv_consume;
    @ViewInject(R.id.ll_coupon)
    private LinearLayout ll_coupon;//优惠券
    @ViewInject(R.id.tv_coupon)
    private TextView tv_coupon;
    @ViewInject(R.id.ll_active)
    private LinearLayout ll_active;//活动券
    @ViewInject(R.id.tv_active)
    private TextView tv_active;
    @ViewInject(R.id.ll_luck_draw)
    private LinearLayout ll_luck_draw;//我的抽奖
    @ViewInject(R.id.ll_integer_mall)
    private LinearLayout ll_integer_mall;//积分商城
    @ViewInject(R.id.ll_share_polite)
    private LinearLayout ll_share_polite;//分享有礼
    @ViewInject(R.id.ll_collection)
    private LinearLayout ll_collection;//我的收藏
    @ViewInject(R.id.ll_address)
    private LinearLayout ll_address;//收货地址
    @ViewInject(R.id.ll_my_evaluate)
    private LinearLayout ll_my_evaluate;//我的评价
    @ViewInject(R.id.tv_dis)
    private TextView tv_dis;//开通分销资格
    @ViewInject(R.id.view_dis)
    private View view_dis;
    @ViewInject(R.id.ll_dis)
    private LinearLayout ll_dis;
    @ViewInject(R.id.ll_dis_manage)
    private LinearLayout ll_dis_manage;//分销管理
    @ViewInject(R.id.ll_market)
    private LinearLayout ll_market;//市场
    @ViewInject(R.id.ll_withdraw_cash)
    private LinearLayout ll_withdraw_cash;//提现
    @ViewInject(R.id.ll_friends)
    private LinearLayout ll_friends;//好友
    @ViewInject(R.id.tv_coupon_name)
    private TextView tv_coupon_name;

    private int login_status = 0;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_o2o_tab_me_new;
    }

    @Override
    protected void init()
    {
        super.init();
        initListener();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        requestUserCenter();
    }

    private void initListener()
    {
        iv_setting.setOnClickListener(this);
        iv_news.setOnClickListener(this);
        ll_login.setOnClickListener(this);
        tv_account_manage.setOnClickListener(this);
        ll_user_group.setOnClickListener(this);
        ll_circle_friend.setOnClickListener(this);
        ll_balance.setOnClickListener(this);
        ll_integral.setOnClickListener(this);
        tv_bind_mobile.setOnClickListener(this);
        iv_dismiss.setOnClickListener(this);
        rl_check_order.setOnClickListener(this);
        ll_pen_pay.setOnClickListener(this);
        ll_pen_sure.setOnClickListener(this);
        ll_evaluate.setOnClickListener(this);
        ll_refund.setOnClickListener(this);
        ll_bill.setOnClickListener(this);
        ll_red_bag.setOnClickListener(this);
        ll_consume.setOnClickListener(this);
        ll_coupon.setOnClickListener(this);
        ll_active.setOnClickListener(this);
        ll_luck_draw.setOnClickListener(this);
        ll_integer_mall.setOnClickListener(this);
        ll_share_polite.setOnClickListener(this);
        ll_collection.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_my_evaluate.setOnClickListener(this);
        tv_dis.setOnClickListener(this);
        ll_dis_manage.setOnClickListener(this);
        ll_market.setOnClickListener(this);
        ll_withdraw_cash.setOnClickListener(this);
        ll_friends.setOnClickListener(this);
    }

    private void requestUserCenter()
    {
        showProgressDialog("");
        CommonInterface.requestUserCenterWapIndex(new AppRequestCallback<AppUserCenterWapIndexActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    login_status = actModel.getUser_login_status();
                    String not_read_msg = actModel.getNot_read_msg();
                    String user_name = actModel.getUser_name();
                    String balance = actModel.getUser_money();
                    int user_score = actModel.getUser_score();
                    String user_score_str = String.valueOf(user_score);
                    String pay_order_count = actModel.getNot_pay_order_count();//未付款的订单数
                    String wait_confirm = actModel.getWait_confirm();//待确认订单数
                    String wait_dp_count = actModel.getWait_dp_count();//待点评的条数
                    String new_ecv = actModel.getNew_ecv();//新增红包数
                    String new_coupon = actModel.getNew_coupon();//新发放给用户消费券数
                    String new_youhui = actModel.getNew_youhui();//新发放给用户优惠券数
                    String new_event = actModel.getNew_event();//新增活动券数
                    String is_fx = actModel.getIs_user_fx();//0未开通分销 1开通分销
                    String coupon_name=actModel.getCoupon_name();
                    if (!TextUtils.isEmpty(coupon_name)){
                        tv_coupon_name.setText(coupon_name);
                        }
                    if (!TextUtils.isEmpty(not_read_msg))
                        SDViewUtil.show(iv_point);
                    else
                        SDViewUtil.hide(iv_point);
                    GlideUtil.load(actModel.getUser_avatar()).into(iv_header);
                    if (!TextUtils.isEmpty(user_name))
                        SDViewBinder.setTextView(tv_name,user_name);
                    else
                        SDViewBinder.setTextView(tv_name,"点击登录或注册");
                    if (!TextUtils.isEmpty(balance))
                        SDViewBinder.setTextView(tv_balance,balance);
                    else
                        SDViewBinder.setTextView(tv_balance,"0");
                    if (!TextUtils.isEmpty(user_score_str))
                        SDViewBinder.setTextView(tv_integral,user_score_str);
                    else
                        SDViewBinder.setTextView(tv_integral,"0");
                    if (actModel.getUser_mobile_empty() == 1)//0:已绑定手机 1未绑定手机
                    {
                        SDViewUtil.show(ll_bind);
                    }else
                        SDViewUtil.hide(ll_bind);
                    if (!TextUtils.isEmpty(pay_order_count))
                    {
                        SDViewUtil.show(tv_pen_pay);
                        SDViewBinder.setTextView(tv_pen_pay,pay_order_count);
                    }else
                        SDViewUtil.hide(tv_pen_pay);
                    if (!TextUtils.isEmpty(wait_confirm))
                    {
                        SDViewUtil.show(tv_pen_sure);
                        SDViewBinder.setTextView(tv_pen_sure,wait_confirm);
                    }else
                        SDViewUtil.hide(tv_pen_sure);
                    if (!TextUtils.isEmpty(wait_dp_count))
                    {
                        SDViewUtil.show(tv_evaluate);
                        SDViewBinder.setTextView(tv_evaluate,wait_dp_count);
                    }else
                        SDViewUtil.hide(tv_evaluate);
                    if (!TextUtils.isEmpty(new_ecv))
                    {
                        SDViewUtil.show(tv_red_bag);
                        SDViewBinder.setTextView(tv_red_bag,new_ecv);
                    }else
                        SDViewUtil.hide(tv_red_bag);
                    if (!TextUtils.isEmpty(new_coupon))
                    {
                        SDViewUtil.show(tv_consume);
                        SDViewBinder.setTextView(tv_consume,new_coupon);
                    }else
                        SDViewUtil.hide(tv_consume);
                    if (!TextUtils.isEmpty(new_youhui))
                    {
                        SDViewUtil.show(tv_coupon);
                        SDViewBinder.setTextView(tv_coupon,new_youhui);
                    }else
                        SDViewUtil.hide(tv_coupon);
                    if (!TextUtils.isEmpty(new_event))
                    {
                        SDViewUtil.show(tv_active);
                        SDViewBinder.setTextView(tv_active,new_event);
                    }else
                    {
                        SDViewUtil.hide(tv_active);
                    }
                    Init_indexActModel init_indexActModel= InitActModelDao.query();
                     String isFx=String.valueOf(init_indexActModel.getIs_fx());
                    if (!TextUtils.isEmpty(isFx) && isFx.equals("0"))  //0无分销功能 1有
                    {
                        SDViewUtil.hide(rl_dis);
                        SDViewUtil.hide(view_dis);
                        SDViewUtil.hide(ll_dis);
                    }else if(!TextUtils.isEmpty(isFx) && isFx.equals("1")){
                        SDViewUtil.show(rl_dis);
                        SDViewUtil.show(view_dis);
                        SDViewUtil.show(ll_dis);
                        if(!TextUtils.isEmpty(is_fx) && is_fx.equals("1")){//0未开通分销 1开通分销
                            SDViewUtil.hide(tv_dis);
                            SDViewUtil.show(rl_dis);
                            SDViewUtil.show(view_dis);
                            SDViewUtil.show(ll_dis);
                        } else if (login_status!=1|| is_fx.equals("0"))
                        {
                            SDViewUtil.show(rl_dis);
                            SDViewUtil.show(tv_dis);
                            SDViewUtil.show(view_dis);
                            SDViewUtil.hide(ll_dis);
                        }

                    }

                    if (login_status == 1)
                    {
                        SDViewUtil.show(ll_group_friend);
                        SDViewBinder.setTextView(tv_user_group,actModel.getUser_group());
                    }else
                    {
                        SDViewUtil.hide(ll_group_friend);
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == iv_setting)
        {
            //设置
            clickSetting();
        }else if (v == iv_news)
        {
            //消息
            if (login_status == 0)
                clickLogin();
            else if (login_status == 1)
                clickNews();
        }else if (v == ll_login)
        {
            //登录
            if (login_status == 0)
                clickLogin();
        }else if (v == tv_account_manage)
        {
            //账户管理
            clickAccountManage();
        }else if (v == ll_user_group)
        {
            //会员
            clickAccountManage();
        }else if (v == ll_circle_friend)
        {
            //朋友圈
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_home";
            isClickWebView(url);
        }else if (v == ll_balance)
        {
            //余额
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_money";
            isClickWebView(url);
        }else if (v == ll_integral)
        {
            //积分
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_score";
            isClickWebView(url);
        }else if (v == tv_bind_mobile)
        {
            //绑定手机号
            clickBindMobile();
        }else if (v == iv_dismiss)
        {
            SDViewUtil.hide(ll_bind);
        }else if (v == rl_check_order)
        {
            //查看全部订单
//            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_order";
//            isClickWebView(url);
            if (login_status == 0)
                clickLogin();
            else if (login_status == 1){
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                intent.putExtra(OrderListActivity.EXTRA_ORDER_TYPE, Constant.OrderType.ALL);
                startActivity(intent);
            }
        }else if (v == ll_pen_pay)
        {
            //待付款
//            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_order&pay_status=1";
//            isClickWebView(url);
            if (login_status == 0)
            {
                clickLogin();
                return;
            }
            Intent intent = new Intent(getActivity(), OrderListActivity.class);
            intent.putExtra(OrderListActivity.EXTRA_ORDER_TYPE, Constant.OrderType.WAIT_PAY);
            startActivity(intent);
        }else if (v == ll_pen_sure)
        {
            if (login_status == 0)
            {
                clickLogin();
                return;
            }
            //待确认
//            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_order&pay_status=3";
//            isClickWebView(url);
            Intent intent = new Intent(getActivity(), OrderListActivity.class);
            intent.putExtra(OrderListActivity.EXTRA_ORDER_TYPE, Constant.OrderType.WAIT_CONFIRM);
            startActivity(intent);
        }else if (v == ll_evaluate)
        {
            if (login_status == 0)
            {
                clickLogin();
                return;
            }
            //待评价
//            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_order&pay_status=4";
//            isClickWebView(url);
            Intent intent = new Intent(getActivity(), OrderListActivity.class);
            intent.putExtra(OrderListActivity.EXTRA_ORDER_TYPE, Constant.OrderType.WAIT_EVALUATE);
            startActivity(intent);
        }else if (v == ll_refund)
        {
            //退款
            //String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_order&act=refund_list";
            //isClickWebView(url);
            clickRefund();
        }else if (v == ll_bill)
        {
            //买单
            //String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_store_pay_order";
            //isClickWebView(url);
            clickBill();
        }else if (v == ll_red_bag)
        {
            //红包
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_ecv";
            isClickWebView(url);
        }else if (v == ll_consume)
        {
            //消费券
            clickConsumeGroup();
            //String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_coupon";
            //isClickWebView(url);
        }else if (v == ll_coupon)
        {
            //优惠券
            clickCoupon();
            //String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_youhui";
            //isClickWebView(url);
        }else if (v == ll_active)
        {
            //活动券
            clickActivityCoupon();
            //String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_event";
            //isClickWebView(url);
        }else if (v == ll_luck_draw)
        {
            //我的抽奖
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_lottery";
            isClickWebView(url);
        }else if (v == ll_integer_mall)
        {
            //积分商城
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=scores_index";
            clickWebView(url);
        }else if (v == ll_share_polite)
        {
            //分享有礼
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_share";
            isClickWebView(url);
        }else if (v == ll_collection)
        {
//            //我的收藏
//            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_collect";
//            isClickWebView(url);
            if (login_status == 0)
                clickLogin();
            else if (login_status == 1){
                Intent intent = new Intent(getActivity(), KeepActivity.class);
                startActivity(intent);
            }
        }else if (v == ll_address)
        {
            //收货地址
//            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_address";
//            isClickWebView(url);
            if (login_status == 0)
                clickLogin();
            else if (login_status == 1){
                Intent intent = new Intent(getActivity(), ShippingAddressActivity.class);
                startActivity(intent);
            }
        }else if (v == ll_my_evaluate)
        {
            //我的评价
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_review";
            isClickWebView(url);
        }else if (v == tv_dis)
        {
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_fx&act=vip_buy";
            isClickWebView(url);
        }else if (v == ll_dis_manage)
        {
            //分销管理
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_fx";
            isClickWebView(url);
        }else if (v == ll_market)
        {
            //市场
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_fx&act=deal_fx";
            isClickWebView(url);
        }else if (v == ll_withdraw_cash)
        {
            //提现
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_fxwithdraw";
            isClickWebView(url);
        }else if (v == ll_friends)
        {
            //好友
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_fxinvite";
            isClickWebView(url);
        }
    }

    /**
     * 设置
     */
    private void clickSetting()
    {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    /**
     * 消息
     */
    private void clickNews()
    {
        Intent intent = new Intent(getActivity(), MessageCenterActivity.class);
        startActivity(intent);
    }

    /**
     * 登录
     */
    private void clickLogin()
    {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void clickCoupon(){
        if (login_status == 0)
        {
            clickLogin();
            return;
        }
        Intent intent = new Intent(getActivity(), CouponListActivity.class);
        startActivity(intent);
    }

    private void clickActivityCoupon(){
        if (login_status == 0)
        {
            clickLogin();
            return;
        }
        Intent intent = new Intent(getActivity(), ActivityCouponActivity.class);
        startActivity(intent);
    }

    /**
     * 账户管理
     */
    private void clickAccountManage()
    {
        if (login_status == 0)
        {
            clickLogin();
        }else if (login_status == 1)
        {
            Intent intent = new Intent(getActivity(), AccountManageAcitivty.class);
            getActivity().startActivity(intent);
        }
    }

    /**
     * 绑定手机号
     */
    private void clickBindMobile()
    {
        Intent intent = new Intent(getActivity(), BindMobileActivity.class);
        startActivity(intent);
    }

    /**
     * 退款
     */
    private void clickRefund()
    {
        if (login_status == 0)
        {
            clickLogin();
            return;
        }
        Intent intent = new Intent(getActivity(), OrderRefundListActivity.class);
        startActivity(intent);
    }

    /**
     * 买单
     */
    private void clickBill()
    {
        if (login_status == 0)
        {
            clickLogin();
            return;
        }
        Intent intent = new Intent(getActivity(), BuyOrderActivity.class);
        startActivity(intent);
    }

    /**
     * 消费券
     */
    private void clickConsumeGroup()
    {
        if (login_status == 0)
        {
            clickLogin();
            return;
        }
        Intent intent = new Intent(getActivity(), ConsumeCouponActivity.class);
        intent.putExtra(EXTRA_COUPON_NAME,tv_coupon_name.getText());
        startActivity(intent);
    }

    /**
     * 是否跳转webView（登录跳转对应页面，没有登录跳转登录页）
     * @param url
     */
    private void isClickWebView(String url)
    {
        if (login_status == 0)
            clickLogin();
        else if (login_status == 1)
            clickWebView(url);
    }

    private void clickWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_IS_SHOW_TITLE,false);
            intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
            getActivity().startActivity(intent);
        }else
            SDToast.showToast("url为空");
    }
}
