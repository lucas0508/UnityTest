package com.example.unitytest;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blingo.sdk.ScriptSDK;
import com.example.unitytest.utils.JsonParser;
import com.example.unitytest.utils.SpeechUtil;
import com.example.unitytest.utils.ToastUtils;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends UnityPlayerActivity {




    //---------------------------------------------


    private static final String TAG = "MainActivity";
    private RelativeLayout unityView;
    private TextView tv;
    private SpeechUtil speechUtil;
    private SpeechRecognizer mIATC;// 语音听写对象cn
    private String voiceCResult = "";
    private static ScriptSDK sdk = ScriptSDK.getInstance();
    TextView tv11;
    private MyUnityPlayer mUnityPlayer;
 //   private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unityView = (RelativeLayout) findViewById(R.id.unityView);
        tv11 = (TextView) findViewById(R.id.tv1);
        tv = (TextView) findViewById(R.id.tv);
        mUnityPlayer= new MyUnityPlayer(this);

//        View view = mUnityPlayer.getView();
//        view = new UnityPlayer(this);



        unityView.addView(mUnityPlayer.getView());
        speechUtil = new SpeechUtil(MainActivity.this);
        mIATC = speechUtil.mIATC;
        voiceCResult = "";
        // mIATC.startListening(iatCListener);

//        ArrayList<String> nextLine = sdk.getNextLine("","","","","","");
//
//        Log.e(TAG, "getUnityMessage: nextLine " + nextLine);

//        File sdDir = Environment.getExternalStorageDirectory();
//        File path =new File(sdDir+File.separator+"course_family");
//        Log.e(TAG, "path: path " + path.getAbsolutePath());

        tv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  UnityPlayer.UnitySendMessage("AndroidToUnity", "OnQuitButtonClick", "");
                mUnityPlayer.quit();
              //  finish();
            }
        });


    }

    private class MyUnityPlayer extends UnityPlayer{

        public MyUnityPlayer(ContextWrapper contextWrapper) {
            super(contextWrapper);
        }

        @Override
        protected void kill() {
            super.kill();
        }
    }



    @Override
    public void onBackPressed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUnityPlayer.quit();
            }
        });
        super.onBackPressed();
    }

    /**
     * Unity 调用：是否开启麦克
     *
     * @param isClose true：start
     */
    public void GetUnityMessage(boolean isClose) {


        if (isClose) {

            voiceCResult = "";
            mIATC.startListening(iatCListener);

//            delayTimer();


        } else {
            if (mIATC != null && mIATC.isListening()) {
                mIATC.stopListening();
            }
        }

    }
    /**
     * 延时3秒执行
     */
    private void delayTimer() {
        // new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (mIATC != null && mIATC.isListening()) {
//                        mIATC.stopListening();
//                    }
//                }
//            }, 3000);
        TimerTask task = new TimerTask() {
            public void run() {
                Log.e(TAG, "run: task " + "111111111");
                if (mIATC != null && mIATC.isListening()) {
                    mIATC.stopListening();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }


    // 语音识别回调监听：IAT回调监听
    private RecognizerListener iatCListener = new RecognizerListener() {
        @Override
        public void onBeginOfSpeech() {
            Log.e(TAG, "开始说话");
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
        }

        @Override
        public void onError(SpeechError error) {

            if (error.getErrorDescription().equals("网络连接发生异常")) {
                ToastUtils.showToast(MainActivity.this, "网络不给力");
            } else if (error.getErrorCode() == 10118) {
                voiceCResult = "";
                mIATC.startListening(iatCListener);
//                delayTimer();
            } else {
                Log.e(TAG, "讯飞错误码" + error.getErrorCode());
            }
        }

        @Override
        public void onEndOfSpeech() {
        }
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            // 识别出语音内容
            voiceCResult = voiceCResult + JsonParser.parseIatResult(results.getResultString());
            // 确定识别完成
            if (!isLast) {
                Log.e(TAG, "结果" + voiceCResult);
                tv.setText(voiceCResult);
                UnityPlayer.UnitySendMessage("_Active", "Answer", voiceCResult);
            } else {
                mIATC.startListening(iatCListener);
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    @Override
    protected void onDestroy() {
        if (mIATC != null && mIATC.isListening()) {
            mIATC.stopListening();
        }
        super.onDestroy();
    }





    public String getsaveDirectory() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "ScreenRecord" + "/";

            File file = new File(rootDir);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }

            Toast.makeText(getApplicationContext(), rootDir, Toast.LENGTH_SHORT).show();

            return rootDir;
        } else {
            return null;
        }
    }

}
