package com.example.unitytest;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


/**
 * Created by SXJ on 2016/10/25 0025.
 */

public class CommonDialog extends Dialog {

    private TextView contentTv;
    private TextView cancleTv;
    private TextView confirmTv;

    public CommonDialog(Context context) {
        super(context, R.style.common_dialog);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        //加载布局文件
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.common_dialog, null);
        contentTv = (TextView) view.findViewById(R.id.common_dialog_content);
        cancleTv = (TextView) view.findViewById(R.id.common_dialog_cancle);
        confirmTv = (TextView) view.findViewById(R.id.common_dialog_confrim);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        show();
    }


    public void setContent(String content) {
        contentTv.setText(content);
    }

    public void setCancelTv(String str, View.OnClickListener leftListener) {
        cancleTv.setText(str);
        cancleTv.setFocusable(true);
        cancleTv.requestFocus();
        cancleTv.setFocusableInTouchMode(true);
        cancleTv.setOnClickListener(leftListener);
        cancleTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.animate().scaleX(1.05f).scaleY(1.05f).setDuration(300).start();
                } else
                    v.animate().scaleX(1.0f).scaleX(1.0f).setDuration(300).start();
            }
        });
    }

    public void setConfirmTv(String str, View.OnClickListener rightListener) {
        confirmTv.setText(str);
        confirmTv.setOnClickListener(rightListener);
        confirmTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.animate().scaleX(1.05f).scaleY(1.05f).setDuration(300).start();
                } else
                    v.animate().scaleX(1.0f).scaleX(1.0f).setDuration(300).start();
            }
        });
    }

    public void setCancelEnable(boolean isEnabled) {
        cancleTv.setEnabled(isEnabled);
    }
}
