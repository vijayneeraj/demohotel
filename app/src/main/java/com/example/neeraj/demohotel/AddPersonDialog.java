package com.example.neeraj.demohotel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author neeraj on 18/9/18.
 */
public class AddPersonDialog extends Dialog implements View.OnClickListener {
    ImageView id_profile, id_image;
    TextView btn_pic, btn_id, submit, cancel;
    EditText person_name,id_name,id_no;
    DialogListener dialogListener;
    private int CAMERA_REQUEST = 22;
    int CAMERA_CALL = 0;
    Context context;
    int checksum;
    String pathID = "";
    String pathProfile = "";

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public AddPersonDialog(@NonNull Context context, int checksum) {
        super(context);
        this.context = context;
        this.checksum = checksum;
        initViews();
    }

    public void setProfileImg(String path) {
        if (id_profile != null) {
            pathProfile = path;
            Picasso.with(context).load(new File(path)).placeholder(R.drawable.no_image).into(id_profile);
        }
    }

    public void setUidImage(String path) {
        if (id_image != null) {
            pathID = path;
            Picasso.with(context).load(new File(path)).placeholder(R.drawable.no_image).into(id_image);
        }
    }

    private void initViews() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.add_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.height= WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.width=WindowManager.LayoutParams.MATCH_PARENT;
        wlp.gravity = Gravity.CENTER;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        id_image = findViewById(R.id.id_image);
        id_profile = findViewById(R.id.id_profile);
        btn_id = findViewById(R.id.btn_id);
        btn_pic = findViewById(R.id.btn_pic);
        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);
        person_name = findViewById(R.id.person_name);
        id_no=findViewById(R.id.id_no);
        id_name=findViewById(R.id.id_name);
        btn_id.setOnClickListener(this);
        btn_pic.setOnClickListener(this);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (dialogListener != null) {
                    if (validateParam()){
                        dialogListener.onSubmit(pathID, pathProfile, person_name.getText().toString(),id_name.getText().toString(),id_no.getText().toString());
                        dismiss();
                    }
                }

                break;
            case R.id.cancel:
                if (dialogListener != null) {
                    dialogListener.onCancel();
                }
                dismiss();
                break;
            case R.id.btn_id:
                if (dialogListener != null) {
                    dialogListener.openDialogCamera(0);
                }
                break;
            case R.id.btn_pic:
                if (dialogListener != null) {
                    dialogListener.openDialogCamera(1);
                }
                break;
        }
    }


    public interface DialogListener {
        void onCancel();

        void onSubmit(String path_id, String path_profile, String personName,String idName,String idNo);

        void openDialogCamera(int call);
    }
    private boolean validateParam(){

        String pName=person_name.getText().toString();
        String iName=id_name.getText().toString();
        String idNO=id_no.getText().toString();
        if (pName.isEmpty()){
            person_name.setError("Person name required!");
            return false;
        }else if (iName.isEmpty()){
            id_name.setError("Idname required!");
            return false;
        }else  if (idNO.isEmpty()){
            id_no.setError("IdNumber required!");
            return false;
        }else if (pathProfile.isEmpty()){
            Toast.makeText(context,"Profile pic required",Toast.LENGTH_LONG).show();
            return false;
        }else if (pathID.isEmpty()){
            Toast.makeText(context,"Id pic required",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
