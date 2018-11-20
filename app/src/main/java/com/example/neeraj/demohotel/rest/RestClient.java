package com.example.neeraj.demohotel.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by neeraj on 27/2/18.
 */

public class RestClient implements Callback<ResponseBody>,AlertMessageDialog.RetryListener {
    private ApiListeners apiListeners;
    private Context context;
    private ProgressDialog mProgressDialog;
    private int API_ID;
    Call<ResponseBody> mCall;
    AlertMessageDialog alertMessageDialog;

    public void callApi(int API_ID, Call<ResponseBody> mCall) {
        this.API_ID = API_ID;
        this.mCall=mCall;
        if (isNetworkAvailable()) {
            this.mCall.enqueue(this);
            showLoadingDialog(context, false);
        } else {
            //show no network retry dialog here
             alertMessageDialog=new AlertMessageDialog(context,"No Network Available");
            alertMessageDialog.setRetryListener(this);
            alertMessageDialog.show();
        }
    }
    public void dismissNoNetworkDialog(){
        if (alertMessageDialog!=null){
            alertMessageDialog.dismiss();

        }
    }

    public void showLoadingDialog(Context context, boolean isCancellable) {
        if (mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(isCancellable);
        mProgressDialog.show();
    }

    public void dismissLoadingDialog() {
        if (mProgressDialog != null && context!=null) {
            mProgressDialog.dismiss();
        }
    }

    public RestClient(Context context, ApiListeners apiListeners) {
        this.context = context;
        this.apiListeners = apiListeners;
        mProgressDialog = new ProgressDialog(context);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            if (apiListeners != null) {
                try {
                    apiListeners.onResponseSucess(response.body().string(), API_ID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            String message = response.message();
            if (apiListeners != null) {
                apiListeners.onFailure(message);
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        if (context != null) {
            mProgressDialog.dismiss();
            AlertMessageDialog alertMessageDialog=new AlertMessageDialog(context,"Something went wrong");
            alertMessageDialog.setRetryListener(this);
            alertMessageDialog.show();
        }
    }

    @Override
    public void onRetry() {
        if (mCall!=null && context!=null){
            callApi(API_ID,mCall.clone());
        }
    }

    public interface ApiListeners {
        void onResponseSucess(String response, int apiId);

        void onFailure(String message);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
