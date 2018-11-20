package com.example.neeraj.demohotel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author neeraj on 13/9/18.
 */
public class GuestListActivity extends AppCompatActivity {
    RecyclerView rv_guest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);
        initViews();
    }

    private void initViews() {
        rv_guest = (RecyclerView) findViewById(R.id.rv_guest);
        rv_guest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        GuestAdapter guestAdapter = new GuestAdapter();
        rv_guest.setAdapter(guestAdapter);
    }
}
