package com.example.neeraj.demohotel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neeraj.demohotel.rest.ApiUrls;
import com.example.neeraj.demohotel.rest.RestAdapter;
import com.example.neeraj.demohotel.rest.RestClient;
import com.example.neeraj.demohotel.rest.RestService;
import com.example.neeraj.demohotel.rest.models.SignupModel;
import com.google.gson.Gson;

import okhttp3.Call;

/**
 * @author neeraj on 13/9/18.
 */
public class SignupActivity extends AppCompatActivity implements View.OnClickListener, RestClient.ApiListeners {
    EditText name, email, password, address, contact;
    TextView signup;
    RestClient restClient;
    String st_name ;
    String st_email ;
    String st_pass ;
    String st_contact;
    String st_address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
    }

    private void initViews() {
        restClient = new RestClient(this, this);
        name = (EditText) findViewById(R.id.ed_name);
        email = (EditText) findViewById(R.id.ed_email);
        password = (EditText) findViewById(R.id.ed_password);
        address = (EditText) findViewById(R.id.ed_address);
        contact = (EditText) findViewById(R.id.ed_contact);
        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:
                if (isValidateParam()) {
                    doSignup();

                }
                break;
        }
    }

    private void doSignup() {

          restClient.callApi(ApiUrls.SIGNUPAPI, RestAdapter.getAdapter().doSignup(st_email,st_contact,st_pass,st_address,st_name));
    }

    private boolean isValidateParam() {
         st_name = name.getText().toString();
         st_email = email.getText().toString();
         st_pass = password.getText().toString();
         st_contact = contact.getText().toString();
         st_address = address.getText().toString();
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

    @Override
    public void onResponseSucess(String response, int apiId) {
        if (apiId == ApiUrls.SIGNUPAPI) {
            try {
                SignupModel signupModel= new Gson().fromJson(response,SignupModel.class);
                if (signupModel!=null){
                    if (signupModel.getResult().equalsIgnoreCase("1")){
                        //signup success full high the flag of login here push
                        UserPrefrences.setLogin(this, true);
                        UserPrefrences.setEmail(this,st_email);
                        UserPrefrences.setAddress(this,st_address);
                        UserPrefrences.setMobile(this,st_contact);
                        UserPrefrences.setUserName(this,st_name);
                        UserPrefrences.setPassword(this,st_pass);
                        UserPrefrences.setHotelId(signupModel.getData().getId(),this);
                        Intent intent=new Intent(SignupActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
