package com.example.neeraj.demohotel.rest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.neeraj.demohotel.R;


/**
 * Created by neeraj on 2/4/18.
 */

public class AlertMessageDialog extends Dialog implements View.OnClickListener {
    TextView retry,message_txt;
    RetryListener retryListener;

    public void setRetryListener(RetryListener retryListener) {
        this.retryListener = retryListener;
    }

    public AlertMessageDialog(@NonNull Context context, String message) {
        super(context);
        init(message);
    }
    public void init(String message){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.alert_message_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        retry=(TextView)findViewById(R.id.retry);
        message_txt=(TextView)findViewById(R.id.message);
        message_txt.setText(message);
        retry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retry:
                dismiss();
                //call back to the listener
                if (retryListener!=null){
                    retryListener.onRetry();
                }
                break;
        }
    }
    public interface RetryListener{
        void onRetry();
    }
}
