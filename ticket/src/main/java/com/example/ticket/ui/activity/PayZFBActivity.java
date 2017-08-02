package com.example.ticket.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.ticket.db.DBConfig;
import com.example.ticket.db.ticket.TicketRecord;
import com.example.ticket.utils.PayResult;
import com.example.ticket.utils.SignUtils;




import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import xutils.common.util.KeyValue;
import xutils.db.sqlite.WhereBuilder;
import xutils.ex.DbException;
import xutils.x;

public class PayZFBActivity extends Activity {
    double mTotalPrice;
    // 商户PID
    public static final String PARTNER = "2088911056441175";
    // 商户收款账号
    public static final String SELLER = "1942623403@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMne6Yte+kgeBTH79d2J7w6QJc4rdUDzajbhJr5VRPvdhQ5S+wG2P7Gjb0WujtWVH6qw24w+UHYas1dAJAgxI5bRosH3fTPn0B56n8Bm1NGrAtvtmHuGFBGVpClNxcpycJST1P7XmNDix6q5w1yjiZSaca+5QfzJU9bT2i8R2XBpAgMBAAECgYEAu8TzWObUSIZb8L1bWWC2ksYz1AyKOQrxLfDJV9r6mofW/pbi0RnTLwlFM/yE8VePJ/bvkAhaUTDOxgZsPY5veWzyTyHUIsJKqoiH9srm1oAAGm87PNMjYH2sMmZIHzb2EiciAJB2rqRQrjg3ebBkVma4Z82e3GbzZKoIW2dpbYECQQD/M6mcrwJcT5/garbqEzVCo8q3Mo3Mw4kf+Jm9/Q+4QN0tnrqETSK3pLNDfQLea6D4u8mMFqrX4HiexBqFVaO5AkEAyoCMUO5igFzqr2msO5oXcUJes3l8U4M5HP5wQIYfX4rP+4fCpDlYPdxqkERebpoiENgI9yGChjdWTmWVjj7qMQJAWvvLO541ZD3LDRJetiM0+NY2Xov6fAc2axyRi2eeZB1T/YC6lJJ/jyN7+dKm5C9RC3y/xV8Ld72Co2/c2K6ImQJBAKPFNrW1NUBu5PYcVkiVpGPas1Ae7id0Ntp2BD6KuhDA6NeZwhaisfTFsAEg6eY1DVvl+8ox9aeqVUht63fw9eECQQCFlMya+qtktIB3/f0n2mYyR2W0q6d5GpYO8p6o+ll0pkplrW9p8Jk9dIHbP6V6eTQsCYpDTKpEqYdvepCxc6yQ";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    private int id;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((String) msg.obj);
            /**
             * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
             * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
             * docType=1) 建议商户依赖异步通知
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            System.out.println("resultStatus = " + resultStatus);
            // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
            if (TextUtils.equals(resultStatus, "9000")) {
                Toast.makeText(PayZFBActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                WhereBuilder whereBuilder=WhereBuilder.b("id","=",id);
                KeyValue keyValue=new KeyValue("state","已支付");
                try {
                    x.getDb(DBConfig.getDaoConfig()).update(TicketRecord.class,whereBuilder,keyValue);
                    Intent intent=new Intent(PayZFBActivity.this,MainActivity.class);
                    startActivity(intent);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            } else {
                // 判断resultStatus 为非"9000"则代表可能支付失败
                // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                if (TextUtils.equals(resultStatus, "8000")) {
                    Toast.makeText(PayZFBActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                } else {
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                    Toast.makeText(PayZFBActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                }
            }

            finish();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            id = extras.getInt("id");
        }
        startPay();
    }

    private void startPay() {
        mTotalPrice = 0.01;
        String orderInfo = getOrderInfo("昊客运", "您购买了车票，总价为：", mTotalPrice+"");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayZFBActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        //测试阶段动手脚
        price="0.01";

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

//		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		orderInfo += "&return_url=\"m.alipay.com\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
