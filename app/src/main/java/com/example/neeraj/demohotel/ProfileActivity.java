package com.example.neeraj.demohotel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author neeraj on 13/9/18.
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name, email, password, address, contact;
    TextView update;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.profile);
        initViews();
    }

    private void initViews() {
        name = (EditText) findViewById(R.id.ed_name);
        email = (EditText) findViewById(R.id.ed_email);
        password = (EditText) findViewById(R.id.ed_password);
        address = (EditText) findViewById(R.id.ed_address);
        contact = (EditText) findViewById(R.id.ed_contact);
        update = (TextView) findViewById(R.id.update);
        update.setOnClickListener(this);
        setData();
    }

    private void setData() {
        name.setText(UserPrefrences.getUserName(this));
        email.setText(UserPrefrences.getEmail(this));
        password.setText(UserPrefrences.getPassword(this));
        address.setText(UserPrefrences.getAddress(this));
        contact.setText(UserPrefrences.getMobile(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_profile:
                makeEditable(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeEditable(boolean v) {
        name.setEnabled(true);
        contact.setEnabled(true);
        address.setEnabled(true);
        email.setEnabled(true);
        password.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update:
                if (isValidateParam()) {
                    doSignup();

                }
                break;
        }
    }

    private void doSignup() {
        //call update profile here


    }

    private boolean isValidateParam() {
        String st_name = name.getText().toString();
        String st_email = email.getText().toString();
        String st_pass = password.getText().toString();
        String st_contact = contact.getText().toString();
        String st_address = address.getText().toString();
        if (st_name.isEmpty()) {
            name.setError("name required!");
            return false;
        } else if (st_email.isEmpty()) {
            email.setError("email required!");
            return false;
        } else if (st_pass.isEmpty()) {
            password.setError("password required!");
            return false;
        } else if (st_contact.isEmpty()) {
            contact.setError("contact required!");
            return false;
        } else if (st_address.isEmpty()) {
            address.setError("address required");
            return false;
        }
        return true;
    }
}
