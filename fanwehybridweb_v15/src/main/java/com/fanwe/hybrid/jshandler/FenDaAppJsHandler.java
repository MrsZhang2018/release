package com.fanwe.hybrid.jshandler;

import android.app.Activity;
import android.media.MediaRecorder;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.common.AppInstanceConfig;
import com.fanwe.hybrid.dialog.SDProgressDialog;
import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.hybrid.model.JsVoiceRecordSenderStartModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.media.player.SDMediaPlayer;
import com.fanwe.library.media.recorder.SDMediaRecorder;
import com.fanwe.library.media.recorder.SDMediaRecorderListener;
import com.fanwe.library.utils.SDCountDownTimer;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.webview.CustomWebView;

import java.io.File;

import cn.fanwe.yi.BuildConfig;


/**
 * Created by Administrator on 2016/11/8.
 */

public class FenDaAppJsHandler extends AddAppJsHandler
{
    private static String mPath = AppInstanceConfig.getInstance().getmPath();

    private long longtime;

    public FenDaAppJsHandler(Activity activity, CustomWebView customWebView)
    {
        super(activity);
        this.customWebView = customWebView;
    }

    // FD 1.0 permissions OC 权限的判断 有权限就执行 FD 2.0
    @JavascriptInterface
    public void js_voiceRecord_permissions()
    {
        voiceRecord_start_play();
    }

    //FD 6.0重新录
    @JavascriptInterface
    public void js_voiceRecord_again()
    {
        voiceRecord_start_play();
    }

    //  FD 3.0停止录音
    @JavascriptInterface
    public void js_voiceRecord_stopVoice()
    {
        SDMediaRecorder.getInstance().stop();
    }

    private void voiceRecord_start_play()
    {
        SDMediaRecorder.getInstance().init(App.getApplication());
        SDMediaRecorder.getInstance().setMaxRecordTime(60);
        SDMediaRecorder.getInstance().start(mPath);
        SDMediaRecorder.getInstance().registerTimerListener(new SDCountDownTimer.SDCountDownTimerListener()
        {
            @Override
            public void onTick(long leftTime)
            {

            }

            @Override
            public void onFinish()
            {
                SDMediaRecorder.getInstance().stop();
            }
        });
        SDMediaRecorder.getInstance().registerListener(new SDMediaRecorderListener()
        {
            @Override
            public void onRecording()
            {

            }

            @Override
            public void onStopped(File file, long duration)
            {
                longtime = duration;
            }

            @Override
            public void onReleased()
            {

            }

            @Override
            public void onInfo(MediaRecorder mr, int what, int extra)
            {

            }

            @Override
            public void onError(MediaRecorder mr, int what, int extra)
            {

            }

            @Override
            public void onException(Exception e)
            {

            }
        });

        customWebView.post(new Runnable()
        {
            @Override
            public void run()
            {
                customWebView.loadJsFunction("js_voiceRecord_startVoice", "1");
            }
        });
    }

    // FD 4.0播放录音文件
    @JavascriptInterface
    public void js_voiceRecord_start_play()
    {
        SDMediaPlayer.getInstance().playAudioFile(mPath);
    }

    // FD 5.0播放录音文件
    @JavascriptInterface
    public void js_voiceRecord_stop_play()
    {
        SDMediaPlayer.getInstance().stop();
    }

    // FD 7.0发送 OC开始执行发送
    @JavascriptInterface
    public void js_voiceRecord_sender_start(String json)
    {
        try
        {
            JsVoiceRecordSenderStartModel model = JSON.parseObject(json, JsVoiceRecordSenderStartModel.class);

            AppRequestParams params = new AppRequestParams();
            params.setUrl(BuildConfig.SERVER_URL_MAPI_URL);
            params.put("ctl", "fenda_mine_answer");
            params.put("act", "answer_app");
            params.put("question_id", model.getQuestion_id());
            params.put("user_id", model.getUser_id());
            params.putFile("answer_file", new File(mPath));
            params.put("long_time", (int) longtime / 1000);
            params.put("r_type", "1");

            AppHttpUtil.getInstance().post(params, new AppRequestCallback<BaseActModel>()
                    {
                        protected SDProgressDialog mDialog;

                        @Override
                        protected void onStart()
                        {
                            super.onStart();
                            mDialog = new SDProgressDialog(mActivity);
                            mDialog.setMessage("正在上传");
                            mDialog.show();
                        }

                        @Override
                        protected void onSuccess(SDResponse resp)
                        {
                            //SDToast.showToast(actModel.getMsg());
                            customWebView.loadJsFunction("js_voiceRecord_sender_finished", "1");
                            //EventBus.getDefault().post(new SDBaseEvent(null, EnumEventTag.EVENT_RELOAD_WEBVIEW.ordinal()));
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
                            if (mDialog != null)
                            {
                                mDialog.dismiss();
                                mDialog = null;
                            }
                        }
                    }

            );
        } catch (Exception e)
        {
            SDToast.showToast("json解析异常");
        }
    }

    //2.Oc调js   0 失败 1成功  3其他   上传 0、1、3字段
    //js_voiceRecord_startVoice//     // FD 2.0 0：无权限 1：有权限，OC开始录音，H5开始倒计时
    //js_voiceRecord_sender_finished  // // FD 8.0 OC 发送完成
}
