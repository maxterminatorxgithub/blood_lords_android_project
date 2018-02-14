package net.mybluemix.eu_de.maxterminatorx.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSaveInfo,btnBack;
    private TextView tvAdress,tvWebsite,tvInstagramm,tvTelephone,tvUsername;
    private TextInputEditText etAddress,etWebsit,etInstagramm,etTelephone;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private String curentUserUID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_settings);
        //buttons
        btnSaveInfo = findViewById(R.id.btnSaveInfo);
        btnBack = findViewById(R.id.btnBackToTable);
        btnBack.setOnClickListener(this);
        btnSaveInfo.setOnClickListener(this);

        //text views
        tvAdress = findViewById(R.id.txtAddress);
        tvWebsite = findViewById(R.id.txtWebsite);
        tvInstagramm = findViewById(R.id.txtInstagramm);
        tvTelephone = findViewById(R.id.txtTelephoneNum);
        //tvUsername = findViewById(R.id.tvSettingUsername);

        //edit text views
        etAddress = findViewById(R.id.etAdress);
        etWebsit = findViewById(R.id.etWebsite);
        etInstagramm = findViewById(R.id.etInstagramm);
        etTelephone = findViewById(R.id.etTelephone);

        //firebase settings
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            curentUserUID = user.getUid();
        }
        Log.wtf("uid",curentUserUID);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        //show user information in private cabinet
        showUserInfo();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveInfo:
                //adding new info to settings child on firebase Json
                saveUserInfo();
                break;
            case R.id.btnBackToTable:
                startActivity(new Intent(this, TableScore.class));
                finish();
                break;
        }
    }

    private void saveUserInfo() {

        final String address = etAddress.getText().toString().trim();
        final String website = etWebsit.getText().toString().trim();
        final String instagramm = etInstagramm.getText().toString().trim();
        final String telephone = etTelephone.getText().toString().trim();

        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(website) || TextUtils.isEmpty(instagramm)
                || TextUtils.isEmpty(telephone)) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Name and Address cannot be empty", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Saving user information...");
        progressDialog.show();


        databaseReference.child("settings").child(firebaseAuth.getUid()).child("adress").setValue(address);
        databaseReference.child("settings").child(firebaseAuth.getUid()).child("website").setValue(website);
        databaseReference.child("settings").child(firebaseAuth.getUid()).child("instagram").setValue(instagramm);
        databaseReference.child("settings").child(firebaseAuth.getUid()).child("telephone").setValue(telephone);


    }

    private void showUserInfo(){
        databaseReference.child("settings").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String adress = dataSnapshot.child("adress").getValue(String.class);
                String website = dataSnapshot.child("website").getValue(String.class);
                String instagramm = dataSnapshot.child("instagram").getValue(String.class);
                String telephone = dataSnapshot.child("telephone").getValue(String.class);
                tvAdress.setText("name :"+ adress);
                tvWebsite.setText("website :"+ website);
                tvInstagramm.setText("instagram :" + instagramm);
                tvTelephone.setText("telephone :" + telephone);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
