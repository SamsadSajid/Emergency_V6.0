package com.example.dolphin.emergency_v60;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.URI;


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
    ImageView imageview;

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
        igallery = (Button) findViewById(R.id.igallary);
        icamera = (Button) findViewById(R.id.icamera);
        imageview = (ImageView)findViewById(R.id.imageView);
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
        User_Info appearanceInfo = EmergencyApp.getWritableDatabaseUserInfo().readAppearanceInfo();
        if (appearanceInfo.getId() != -1) {
            eName.setText(appearanceInfo.getName());
            ePhone.setText(appearanceInfo.getPhone());
            eAddress.setText(appearanceInfo.getAddress());
            eInstitution.setText(appearanceInfo.getInstitution());
            eEmergency1.setText(appearanceInfo.getEmergency1());
            eEmergency2.setText(appearanceInfo.getEmergency2());
            sBlood.setSelection(adapterBlood.getPosition(appearanceInfo.getBloodgroup()));
        }
        bEdit.setOnClickListener(this);
        bSave.setOnClickListener(this);
        igallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opengallary();
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
        if(view == bSave){
            eName.setEnabled(false);
            ePhone.setEnabled(false);
            eAddress.setEnabled(false);
            eInstitution.setEnabled(false);
            eEmergency1.setEnabled(false);
            eEmergency2.setEnabled(false);
            sBlood.setEnabled(false);
            if(!eName.getText().toString().equals("")) {
                User_Info userinfo = new User_Info(eName.getText().toString(), ePhone.getText().toString(), eAddress.getText().toString(),
                        eInstitution.getText().toString(), eEmergency1.getText().toString(), eEmergency2.getText().toString(), sBlood.getSelectedItem().toString());
                EmergencyApp.getWritableDatabaseUserInfo().insertUserInfo(userinfo, true);
                Msg.COUT(this, "Saved Successfully");
                bEdit.setVisibility(View.VISIBLE);
                bSave.setVisibility(View.GONE);
            }
        }
        else if(view == bEdit){
            eName.setEnabled(true);
            ePhone.setEnabled(true);
            eAddress.setEnabled(true);
            eInstitution.setEnabled(true);
            eEmergency1.setEnabled(true);
            eEmergency2.setEnabled(true);
            sBlood.setEnabled(true);
            bEdit.setVisibility(View.GONE);
            //bEdit.setVisibility(View.INVISIBLE);
            bSave.setVisibility(View.VISIBLE);
            User_Info userinfo = new User_Info(eName.getText().toString(),ePhone.getText().toString(),eAddress.getText().toString(),
                    eInstitution.getText().toString(),eEmergency1.getText().toString(),eEmergency2.getText().toString(),sBlood.getSelectedItem().toString());
            EmergencyApp.getWritableDatabaseUserInfo().insertUserInfo(userinfo, true);
            Msg.COUT(this,"Informations Inserted Successfully");
        }
    }

    /*private void opengallary(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }
    @Override
    protected void OnActivityResult(int requestcode,int resultcode,Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        if(resultcode == RESULT_OK && requestcode == PICK_IMAGE){
            URI imageURI = data.getData();
            imageview.setImageURI(imageURI);
        }
    }*/

}