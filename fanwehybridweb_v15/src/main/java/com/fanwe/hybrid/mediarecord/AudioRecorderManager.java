package com.fanwe.hybrid.mediarecord;

import com.fanwe.library.utils.SDCountDownTimer;
import com.fanwe.library.utils.SDToast;

/**
 * Created by Administrator on 2016/11/11.
 */

public class AudioRecorderManager
{
    private static final long INTERVAL_TIME = 1000;

    private SDCountDownTimer timer = new SDCountDownTimer();
    private long maxRecordTime = 60000;
    private long startTime;
    private AudioRecorderListener listener;

    public void setListener(AudioRecorderListener listener)
    {
        this.listener = listener;
    }

    private boolean canClean = false;

    private AudioRecorder2Mp3Util util = null;

    private static AudioRecorderManager instance;

    private AudioRecorderManager()
    {
    }

    public static AudioRecorderManager getInstance()
    {
        if (instance == null)
        {
            instance = new AudioRecorderManager();
        }
        return instance;
    }

    public void startAudioRecorderManager()
    {
        if (util == null)
        {
            util = new AudioRecorder2Mp3Util(null,
                    "/sdcard/test_audio_recorder_for_mp3.raw",
                    "/sdcard/test_audio_recorder_for_mp3.mp3");
        }
        if (canClean)
        {
            util.cleanFile(AudioRecorder2Mp3Util.MP3
                    | AudioRecorder2Mp3Util.RAW);
        }

        SDToast.showToast("开始录音");

        util.startRecording();
        startTime = System.currentTimeMillis();
        canClean = true;

        if (listener != null)
        {
            listener.onStart();
        }

        timer.start(maxRecordTime,INTERVAL_TIME, new SDCountDownTimer.SDCountDownTimerListener()
        {
            @Override
            public void onTick(long leftTime)
            {

            }

            @Override
            public void onFinish()
            {
                stopAudioRecorderManager();
            }
        });
    }

    public void stopAudioRecorderManager()
    {
        SDToast.showToast("正在转换格式");
        util.stopRecordingAndConvertFile();
        SDToast.showToast("转换完成");
        util.cleanFile(AudioRecorder2Mp3Util.RAW);
        // 如果要关闭可以
        util.close();
        util = null;

        if (listener != null)
        {
            long duration = System.currentTimeMillis() - startTime;
            listener.onStopped(duration);
        }
    }

    public interface AudioRecorderListener
    {
        void onStart();

        void onStopped(long duration);
    }
}
