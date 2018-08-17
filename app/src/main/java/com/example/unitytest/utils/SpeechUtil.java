package com.example.unitytest.utils;

import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;


/**
 * Created by SXJ on 2016/12/6 0006.
 */

public class SpeechUtil {
    public SpeechRecognizer mIATC;// 语音听写中文
    private Context context;

    public SpeechUtil(Context context) {
        this.context = context;
        setmIATC();
    }

    private void setmIATC() {
        mIATC = SpeechRecognizer.createRecognizer(context, null);
        // 设置语言
        mIATC.setParameter(SpeechConstant.LANGUAGE, "en_us");
        // 设置(默认)
        mIATC.setParameter(SpeechConstant.DOMAIN, "iat");
        // 设置语言区域(默认)
        mIATC.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语言焦点
        mIATC.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false");
        // 设置语音前端点
        mIATC.setParameter(SpeechConstant.VAD_BOS, "10000");
        // 设置语音后端点
        mIATC.setParameter(SpeechConstant.VAD_EOS, "3000");
        // 设置标点符号
        mIATC.setParameter(SpeechConstant.ASR_PTT, "0");
        // 设定时间设置超时时间-1 表示无超时限制(默认)
        mIATC.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "60000");
        //设置录音格式
        mIATC.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
    }
}
