package com.example.neeraj.demohotel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neeraj.demohotel.rest.ApiUrls;
import com.example.neeraj.demohotel.rest.RestAdapter;
import com.example.neeraj.demohotel.rest.RestClient;
import com.example.neeraj.demohotel.rest.models.LoginModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

/**
 * @author neeraj on 13/9/18.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RestClient.ApiListeners {
    EditText username, password;
    TextView login, signup;
    RestClient restClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UserPrefrences.getLogin(this)) {
            //call main activity here
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
        PermissionHelperNew.requestingPermissionFromSetting = false;
        if (!PermissionHelperNew.checkAllPermissionGranted(this)) {
            PermissionHelperNew.needPermissions(this);
        } else {
            initViews();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionHelperNew.requestingPermissionFromSetting) {
            PermissionHelperNew.requestingPermissionFromSetting = false;
            if (PermissionHelperNew.checkAllPermissionGranted(this)) {
                initViews();
            }
        } else {
            if (PermissionHelperNew.checkAllPermissionGranted(this)) {
                initViews();
            }
        }
    }

    public void initViews() {
        restClient = new RestClient(this, this);
        username = (EditText) findViewById(R.id.ed_username);
        password = (EditText) findViewById(R.id.ed_password);
        login = (TextView) findViewById(R.id.login);
        signup = (TextView) findViewById(R.id.signup);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        if (!UserPrefrences.getEmail(this).equalsIgnoreCase("")){
            username.setText(UserPrefrences.getEmail(this));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                String username_txt = username.getText().toString();
                String password_txt = password.getText().toString();
                if (username_txt.isEmpty()) {
                    username.setError("username required!");
                } else if (password_txt.isEmpty()) {
                    password.setError("password required!");
                } else {
                    doLogin(username_txt, password_txt);
                }
                break;
            case R.id.signup:
                doSignup();
                break;
        }
    }

    private void doLogin(String user, String pass) {
//start main activity here
        restClient.callApi(ApiUrls.LOGINAPI, RestAdapter.getAdapter().doLogin(user, pass));

    }

    private void doSignup() {
        //start signup activity here.
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionHelperNew.onRequestPermissionsResult(this,
                requestCode, permissions, grantResults)) {
            if (PermissionHelperNew.checkAllPermissionGranted(this)) {
                initViews();
            } else {
                PermissionHelperNew.needPermissions(this);
            }
        }
    }

    @Override
    public void onResponseSucess(String response, int apiId) {
        try {
            restClient.dismissLoadingDialog();
            LoginModel loginModel = new Gson().fromJson(response, LoginModel.class);
            if (loginModel != null) {
                if (loginModel.getResult().equalsIgnoreCase("1")) {
                    //login success
                    //set data in preference here
                    UserPrefrences.setLogin(this, true);
                    UserPrefrences.setEmail(this,loginModel.getMessage().getEmail());
                    UserPrefrences.setAddress(this,loginModel.getMessage().getAddress());
                    UserPrefrences.setMobile(this,loginModel.getMessage().getPhonenumber());
                    UserPrefrences.setUserName(this,loginModel.getMessage().getName());
                    UserPrefrences.setPassword(this,password.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (Exception e) {
            JsonObject jsonParser = (JsonObject) new JsonParser().parse(response);
            if (jsonParser != null) {

                Toast.makeText(getApplicationContext(), jsonParser.get("message").getAsString(), Toast.LENGTH_LONG).show();

            }

            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
