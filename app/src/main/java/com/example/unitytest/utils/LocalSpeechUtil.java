package com.example.unitytest.utils;

import android.content.Context;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;

/**
 * Created by SXJ on 2017/2/11 0011.
 */

public class LocalSpeechUtil {

    public SpeechRecognizer mAsr;// 语音听写中文
    private Context context;

    public LocalSpeechUtil(Context context) {
        this.context = context;
        setmIATC();
    }

    private void setmIATC() {
        //1.创建SpeechRecognizer对象，需传入初始化监听器
        mAsr = SpeechRecognizer.createRecognizer(context, mInitListener);
        //2.构建语法（本地识别引擎目前仅支持BNF语法），方法同在线语法识别，详见Demo
        //3.开始识别,设置引擎类型为本地
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        //设置本地识别使用语法id(此id在语法文件中定义)、门限值
        //设置语法构建生成路径，buildpath是生成路径，可以设置在sd卡内
        // 设置语言
        mAsr.setParameter(SpeechConstant.LANGUAGE, "en_us");
        mAsr.setParameter(SpeechConstant.DOMAIN,"iat");
        mAsr.setParameter(SpeechConstant.ACCENT,"mandarin");
        // 设置语言焦点
        mAsr.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false");
        // 设置语音前端点
        mAsr.setParameter(SpeechConstant.VAD_BOS, "10000");
        // 设置语音后端点
        mAsr.setParameter(SpeechConstant.VAD_EOS, "3000");
        // 设置标点符号
        mAsr.setParameter(SpeechConstant.ASR_PTT, "0");
        // 设定时间设置超时时间-1 表示无超时限制(默认)
        mAsr.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "60000");
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d("tag", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.d("tag", "初始化失败，错误码：" + code);
            }
        }
    };
}
