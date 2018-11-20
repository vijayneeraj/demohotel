package com.example.neeraj.demohotel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.neeraj.demohotel.rest.ApiUrls;
import com.example.neeraj.demohotel.rest.RestAdapter;
import com.example.neeraj.demohotel.rest.RestClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author neeraj on 13/9/18.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddPersonDialog.DialogListener, RestClient.ApiListeners {
    Toolbar toolbar;
    TextView register, btn_id, btn_pic, add_person;
    LinearLayout lin_lay;
    ImageView id_image, id_profile;
    String date_txt;
    String time_txt;
    EditText arrival_time, Departure_time, dob, id_no, id_name, gt_name, gt_email, gt_contact, gt_address, gt_room;
    RadioGroup rg_gender;
    int temp_arrival = 0;
    private int CAMERA_REQUEST = 22;
    int CAMERA_CALL = 0;
    List<PersonModel> viesAdded = new ArrayList<>();
    AddPersonDialog addPersonDialog;
    RestClient restClient;
    String guestIdpic = "";
    String guestProfilePic = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        initViews();
    }

    int gender_id = -1;

    private void initViews() {
        restClient = new RestClient(this, this);
        gt_name = findViewById(R.id.gt_name);
        gt_email = findViewById(R.id.gt_email);
        gt_contact = findViewById(R.id.gt_contact);
        gt_address = findViewById(R.id.gt_address);
        gt_room = findViewById(R.id.gt_room);
        id_name = findViewById(R.id.id_name);
        id_no = findViewById(R.id.id_no);
        add_person = (TextView) findViewById(R.id.add_person);
        lin_lay = (LinearLayout) findViewById(R.id.lin_lay);
        btn_id = (TextView) findViewById(R.id.btn_id);
        btn_pic = (TextView) findViewById(R.id.btn_pic);
        id_image = (ImageView) findViewById(R.id.id_image);
        id_profile = (ImageView) findViewById(R.id.id_profile);
        register = (TextView) findViewById(R.id.register);
        Departure_time = (EditText) findViewById(R.id.Departure_time);
        arrival_time = (EditText) findViewById(R.id.arrival_time);
        dob = (EditText) findViewById(R.id.dob);
        rg_gender = (RadioGroup) findViewById(R.id.rg_gender);
        btn_id.setOnClickListener(this);
        btn_pic.setOnClickListener(this);
        add_person.setOnClickListener(this);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.male) {
                    gender_id = 1;
                    Toast.makeText(MainActivity.this, "male clicked", Toast.LENGTH_LONG).show();
                } else if (i == R.id.female) {
                    gender_id = 0;
                    Toast.makeText(MainActivity.this, "Female clicked", Toast.LENGTH_LONG).show();

                }
            }
        });
        dob.setOnClickListener(this);
        arrival_time.setOnClickListener(this);
        Departure_time.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                //click on logout icon
                UserPrefrences.setLogin(this, false);
                Intent intent10 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent10);
                finish();
                break;
            case R.id.guest_list:
                //call guest list activity here.
                Intent intent = new Intent(MainActivity.this, GuestListActivity.class);
                startActivity(intent);
                break;
            case R.id.profile:
                Intent intent1 = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                //call guest register api here
                if (isValidateParam()) {
                    registerGuest();
                }

                break;
            case R.id.arrival_time:
                temp_arrival = 0;
                showDateTime(0);

                break;
            case R.id.Departure_time:
                temp_arrival = 1;
                showDateTime(0);

                break;
            case R.id.dob:
                showDateTime(1);
                break;
            case R.id.btn_id:
                CAMERA_CALL = 0;
                openCamera();
                break;
            case R.id.btn_pic:
                CAMERA_CALL = 1;
                openCamera();
                break;
            case R.id.add_person:
                callOpenDialog(0);
                break;
        }
    }

    private void callOpenDialog(int checksum) {
        addPersonDialog = new AddPersonDialog(this, checksum);
        addPersonDialog.setDialogListener(this);
        addPersonDialog.show();
    }

    private void addViewToLayout(String personName, String id_path, String profile_path) {
        View view = ((LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.lin_item, null);
        PersonModel personModel = new PersonModel();
        personModel.setProfile_path(profile_path);
        personModel.setPersonNAme(personName);
        personModel.setId_path(id_path);
        viesAdded.add(personModel);
        lin_lay.addView(view);
        view.setTag(viesAdded.size());
        ImageView delete = (ImageView) view.findViewById(R.id.delete);
        ImageView edit = (ImageView) view.findViewById(R.id.edit);
        ImageView profile_img = (ImageView) view.findViewById(R.id.profile_img);
        ImageView uid_img = (ImageView) view.findViewById(R.id.uid_img);
        TextView person_name = (TextView) view.findViewById(R.id.person_name);
        person_name.setText(personName);
        Picasso.with(this).load(new File(profile_path)).placeholder(R.drawable.no_image).into(profile_img);
        Picasso.with(this).load(new File(id_path)).placeholder(R.drawable.no_image).into(uid_img);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) ((View) view.getParent().getParent()).getTag();
                viesAdded.remove(tag - 1);
                lin_lay.removeView((View) view.getParent().getParent());
            }
        });
    }

    String mCurrentPhotoPath;

    private void openCamera() {
        Intent cameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.neeraj.demohotel.fileprovider",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void registerGuest() {
        restClient.callApi(ApiUrls.GUESTAPI, RestAdapter.getAdapter().doGuestSignup(st_email, st_contact, "", st_address, st_name, st_room, arv_date, deprt_time,
                date_birth, String.valueOf(lin_lay.getChildCount()), String.valueOf(gender_id), idName, idNumber, guestIdpic, guestProfilePic));
    }

    private void showDateTime(final int check) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date_txt = year + "-" + dayOfMonth + "-" + (monthOfYear + 1);
                        if (check == 0) {
                            tiemPicker();
                        } else {
                            //set dob here
                            dob.setText(date_txt);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        time_txt = +hourOfDay + ":" + minute + ":00";
                        //set arrival and departure time here.
                        if (temp_arrival == 0) {
                            arrival_time.setText(date_txt + " " + time_txt);
                        } else {
                            Departure_time.setText(date_txt + " " + time_txt);
                        }

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == CAMERA_REQUEST) {
                    if (CAMERA_CALL == 0) {
                        if (addPersonDialog != null && addPersonDialog.isShowing()) {
                            addPersonDialog.setUidImage(mCurrentPhotoPath);
                        } else {
                            Picasso.with(this).load(new File(mCurrentPhotoPath)).placeholder(R.drawable.no_image).into(id_image);
                            Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            guestIdpic = Base64.encodeToString(b, Base64.DEFAULT);
                            Log.e("IMAGESTRING", guestIdpic);

                        }

                    } else {
                        if (addPersonDialog != null && addPersonDialog.isShowing()) {
                            addPersonDialog.setProfileImg(mCurrentPhotoPath);
                        } else {
                            Picasso.with(this).load(new File(mCurrentPhotoPath)).placeholder(R.drawable.no_image).into(id_profile);
                            Picasso.with(this).load(new File(mCurrentPhotoPath)).placeholder(R.drawable.no_image).into(id_image);
                            Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            guestProfilePic = Base64.encodeToString(b, Base64.DEFAULT);
                            Log.e("IMAGESTRING", guestProfilePic);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onCancel() {
        // do nothing
        addPersonDialog = null;
    }

    @Override
    public void onSubmit(String path_id, String path_profile, String personName) {
        //add new view to layout
        addViewToLayout(personName, path_id, path_profile);
        addPersonDialog = null;
    }

    @Override
    public void openDialogCamera(int call) {
        CAMERA_CALL = call;
        openCamera();
    }

    String st_name;
    String st_email;
    String st_room;
    String st_contact;
    String st_address;
    String arv_date;
    String deprt_time;
    String date_birth;
    String idName;
    String idNumber;

    private boolean isValidateParam() {
        st_name = gt_name.getText().toString();
        st_email = gt_email.getText().toString();
        st_room = gt_room.getText().toString();
        st_contact = gt_contact.getText().toString();
        st_address = gt_address.getText().toString();
        arv_date = arrival_time.getText().toString();
        deprt_time = Departure_time.getText().toString();
        date_birth = dob.getText().toString();
        idName = id_name.getText().toString();
        idNumber = id_no.getText().toString();

        if (st_name.isEmpty()) {
            gt_name.setError("name required!");
            return false;
        } else if (st_email.isEmpty()) {
            gt_email.setError("email required!");
            return false;
        } else if (st_room.isEmpty()) {
            gt_room.setError("room number required!");
            return false;
        } else if (st_contact.isEmpty()) {
            gt_contact.setError("contact required!");
            return false;
        } else if (st_address.isEmpty()) {
            gt_address.setError("address required!");
            return false;
        } else if (idNumber.isEmpty()) {
            id_no.setError("Id number required!");
            return false;
        } else if (idName.isEmpty()) {
            id_name.setError("Id Name required!");
            return false;
        } else if (arv_date.isEmpty()) {
            arrival_time.setError("arrival time required!");
            return false;
        } else if (deprt_time.isEmpty()) {
            Departure_time.setError("departure time required!");
            return false;
        } else if (date_birth.isEmpty()) {
            dob.setError("dob required!");
            return false;
        } else if (gender_id == -1) {
            Toast.makeText(getApplicationContext(), "Please select a gender", Toast.LENGTH_LONG).show();
            return false;
        } else if (guestProfilePic.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please add guest picture", Toast.LENGTH_LONG).show();

            return false;
        } else if (guestIdpic.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please add guest Id picture", Toast.LENGTH_LONG).show();

            return false;
        }
        return true;
    }

    @Override
    public void onResponseSucess(String response, int apiId) {
        if (response != null) {
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(response);
            if (jsonObject.get("result").getAsString().equalsIgnoreCase("1")) {
                Toast.makeText(getApplicationContext(), jsonObject.get("message").getAsString(), Toast.LENGTH_LONG).show();
                resetData();
            }
        }
    }

    private void resetData() {
        //reset all values here
        gt_name.setText("");
        gt_name.setHint("Guest Name");
        gt_address.setText("");
        gt_address.setHint("Guest Address");
        gt_email.setText("");
        gt_email.setHint("Email");
        gt_room.setText("");
        gt_room.setHint("Room no");
        gt_contact.setText("");
        gt_contact.setHint("Contact Number");
        arrival_time.setText("");
        arrival_time.setHint("Arrival date & time");
        Departure_time.setText("");
        Departure_time.setHint("Departure date & time");
        dob.setText("");
        dob.setHint("Date Of Birth");
        id_name.setText("");
        id_name.setHint("IdName");
        id_no.setText("");
        id_no.setHint("IdNumber");
        lin_lay.removeAllViews();
        guestProfilePic="";
        guestIdpic="";
        id_profile.setImageResource(R.drawable.no_image);
        id_image.setImageResource(R.drawable.no_image);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }
}
