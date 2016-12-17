package com.example.dolphin.emergency_v60;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.net.Uri;

import com.activeandroid.query.Select;
import com.activeandroid.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText eName;
    EditText ePhone;
    EditText eAddress;
    EditText eInstitution;
    EditText eEmergency1;
    EditText eEmergency2;
    Spinner sBlood;
    Button bEdit;
    Button bSave;
    Button igallery;
    Button icamera;
    private static final int PICK_IMAGE = 100;
    private static final int TAKE_IMAGE = 1;
    private Bitmap bImageBitmap;
    private String sCurrentPhotoPath;
    private ImageView imageview;
    User_Info user_info;
    final Context context = this;
    private ImageButton ibutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eName = (EditText) findViewById(R.id.eName);
        ePhone = (EditText) findViewById(R.id.ePhone);
        eAddress = (EditText) findViewById(R.id.eAddress);
        eInstitution = (EditText) findViewById(R.id.eInstitution);
        eEmergency1 = (EditText) findViewById(R.id.eEmergency1);
        eEmergency2 = (EditText) findViewById(R.id.eEmergency2);
        sBlood = (Spinner) findViewById(R.id.sBlood);
        bEdit = (Button) findViewById(R.id.bEdit);
        bSave = (Button) findViewById(R.id.bSave);
        igallery = (Button) findViewById(R.id.iGallary);
        icamera = (Button) findViewById(R.id.iCamera);
        imageview = (ImageView)findViewById(R.id.imageView);
        ibutton = (ImageButton) findViewById(R.id.iButtonCall);
        bSave.setVisibility(View.INVISIBLE);
        eName.setEnabled(false);
        ePhone.setEnabled(false);
        eAddress.setEnabled(false);
        eInstitution.setEnabled(false);
        eEmergency1.setEnabled(false);
        eEmergency2.setEnabled(false);
        sBlood.setEnabled(false);

        ArrayAdapter<CharSequence> adapterBlood = ArrayAdapter.createFromResource(this,
                R.array.BloodGroup, android.R.layout.simple_spinner_item);
        adapterBlood.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBlood.setAdapter(adapterBlood);
        /*User_Info appearanceInfo = EmergencyApp.getWritableDatabaseUserInfo().readAppearanceInfo();
        if (appearanceInfo.getId() != -1) {
            eName.setText(appearanceInfo.getName());
            ePhone.setText(appearanceInfo.getPhone());
            eAddress.setText(appearanceInfo.getAddress());
            eInstitution.setText(appearanceInfo.getInstitution());
            eEmergency1.setText(appearanceInfo.getEmergency1());
            eEmergency2.setText(appearanceInfo.getEmergency2());
            sBlood.setSelection(adapterBlood.getPosition(appearanceInfo.getBloodgroup()));
        }*/

        try{
            user_info=new Select().from(User_Info.class).executeSingle();
        }
        catch (Exception e) {
            user_info = new User_Info();
        }

        if(user_info==null)user_info=new User_Info();
        else
        {
            eName.setText(user_info.getName());
            ePhone.setText(user_info.getPhone());
            eAddress.setText(user_info.getAddress());
            eInstitution.setText(user_info.getInstitution());
            eEmergency1.setText(user_info.getEmergency1());
            eEmergency2.setText(user_info.getEmergency2());
            sBlood.setSelection(adapterBlood.getPosition(user_info.getBloodgroup()));
        }
        bEdit.setOnClickListener(this);
        bSave.setOnClickListener(this);

        //this block is for phone call

        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        ibutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+((EditText)findViewById(R.id.ePhone)).getText()));
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {

                    startActivity(callIntent);
                }
            }

        });

        //end of phone call block within onCreate()

        igallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        icamera.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                openCamera();
            }
        });
        vanishKeyboard();
    }

    private void vanishKeyboard() {
        eName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ePhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        eAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        eInstitution.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        eEmergency1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        eEmergency2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }



    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onClick(View view){
        if(view == bSave) {
            /*if(!eName.getText().toString().equals("")) {
                 user_info = new User_Info(eName.getText().toString(), ePhone.getText().toString(), eAddress.getText().toString(),
                        eInstitution.getText().toString(), eEmergency1.getText().toString(), eEmergency2.getText().toString(), sBlood.getSelectedItem().toString());
                user_info.save();
                EmergencyApp.getWritableDatabaseUserInfo().insertUserInfo(user_info, true);
            */
            if (!eName.getText().toString().equals("")){
                user_info.setName(eName.getText().toString());
                user_info.setPhone(ePhone.getText().toString());
                user_info.setAddress(eAddress.getText().toString());
                user_info.setInstitution(eInstitution.getText().toString());
                user_info.setEmergency1(eEmergency1.getText().toString());
                user_info.setEmergency2(eEmergency2.getText().toString());
                user_info.setBloodgroup(sBlood.getSelectedItem().toString());
                user_info.save();
                Msg.COUT(this, "Saved Successfully");
            }
                bEdit.setVisibility(View.VISIBLE);
                bSave.setVisibility(View.INVISIBLE);
                eName.setEnabled(false);
                ePhone.setEnabled(false);
                eAddress.setEnabled(false);
                eInstitution.setEnabled(false);
                eEmergency1.setEnabled(false);
                eEmergency2.setEnabled(false);
                sBlood.setEnabled(false);
            }

        else if(view == bEdit){
            eName.setEnabled(true);
            ePhone.setEnabled(true);
            eAddress.setEnabled(true);
            eInstitution.setEnabled(true);
            eEmergency1.setEnabled(true);
            eEmergency2.setEnabled(true);
            sBlood.setEnabled(true);

            bEdit.setVisibility(View.INVISIBLE);
            bSave.setVisibility(View.VISIBLE);

            /*user_info.setName(eName.getText().toString());
            user_info.setPhone(ePhone.getText().toString());
            user_info.setAddress(eAddress.getText().toString());
            user_info.setInstitution(eInstitution.getText().toString());
            user_info.setEmergency1(eEmergency1.getText().toString());
            user_info.setEmergency2(eEmergency2.getText().toString());
            user_info.setBloodgroup(sBlood.getSelectedItem().toString());
            user_info.save();*/
            //Msg.COUT(this,"Informations Inserted Successfully");
        }
    }

    //for phone call

    //monitor phone call activities

    public class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                android.util.Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                android.util.Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                android.util.Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    android.util.Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }




    private void openGallery() {
        Intent gallery =new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void openCamera(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_IMAGE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            imageview.setImageURI(imageUri);
        }

        else if (requestCode == TAKE_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();

            /*FileInputStream inputStream = null;
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(photo);*/

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                // Log.d(TAG, String.valueOf(bitmap));

                imageview = (ImageView) findViewById(R.id.imageView);
                imageview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}