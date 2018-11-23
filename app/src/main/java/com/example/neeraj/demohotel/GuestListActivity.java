package com.example.neeraj.demohotel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.neeraj.demohotel.rest.ApiUrls;
import com.example.neeraj.demohotel.rest.RestAdapter;
import com.example.neeraj.demohotel.rest.RestClient;
import com.example.neeraj.demohotel.rest.models.GuestModel;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author neeraj on 13/9/18.
 */
public class GuestListActivity extends AppCompatActivity implements RestClient.ApiListeners {
    RecyclerView rv_guest;
    RestClient restClient;
    List<GuestModel.DataBean> dataBeans;
    GuestAdapter guestAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);
        initViews();
    }

    private void initViews() {
        dataBeans=new ArrayList<>();
        restClient = new RestClient(this, this);
        rv_guest = (RecyclerView) findViewById(R.id.rv_guest);
        rv_guest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
         guestAdapter = new GuestAdapter(dataBeans);
        rv_guest.setAdapter(guestAdapter);
        callGuestList();
    }

    private void callGuestList() {
        restClient.callApi(ApiUrls.GUESTAPI, RestAdapter.getAdapter().getGuests(UserPrefrences.getHotelId(this)));
    }

    @Override
    public void onResponseSucess(String response, int apiId) {
        restClient.dismissLoadingDialog();
        if (response != null) {
            try {
                GuestModel guestModel = new Gson().fromJson(response, GuestModel.class);
                if (guestModel != null) {
                    if (guestModel.getResult().equalsIgnoreCase("1")) {


                        dataBeans.clear();
                        dataBeans.addAll(guestModel.getData());
                        guestAdapter.notifyDataSetChanged();
                    }
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(String message) {
        restClient.dismissLoadingDialog();
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }
}
