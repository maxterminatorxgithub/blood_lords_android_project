package net.mybluemix.eu_de.maxterminatorx.android;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via instagram/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlayAsGuast, btnRegister, btnLogin;
    private ProgressDialog progressDialog;
    protected EditText etPassword, etEmail, etUserName;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    public String UID ;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            UID = user.getUid();

            Log.i("instagram",user.getEmail());
            Intent in = new Intent(LoginActivity.this, TableScore.class);
            in.putExtra("instagram", user.getEmail());
            startActivity(in);
            finish();
            return;
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();


        btnPlayAsGuast = (Button) findViewById(R.id.btn_enter_as_guast);
        btnPlayAsGuast.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnLogin = (Button) findViewById(R.id.btnLogIn);
        btnLogin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUserName = findViewById(R.id.etUserName);
    }

    @Override
    public void onClick(View v) {



        if(v.getId() == R.id.btn_enter_as_guast){
            Intent in = new Intent(this, AndroidLauncher.class);
            in.putExtra("mode","demo");
            startActivity(in);
            finish();
            return;
        }


        else if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            switch (v.getId()) {
                case R.id.btnRegister:
                    registerUser();
                    break;
                case R.id.btnLogIn:
                    userLogin("Login in...");
                    break;

            }

        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }



    }

    private void userLogin(String msg) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Email and Password cannot be empty", Snackbar.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage(msg);
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {

                            Snackbar.make(findViewById(android.R.id.content),
                                    "Logged in successfully", Snackbar.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, TableScore.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content),
                        e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void registerUser() {
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final String username = etUserName.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Email and Password cannot be empty", Snackbar.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Snackbar.make(findViewById(android.R.id.content),
                                    "Registered successfully", Snackbar.LENGTH_LONG).show();
                            String location = "Israel";
                            User newUser = new User(username, email, password, location);
                            //adding new user to firebase
                            DatabaseReference users = databaseReference.child("user");
                            users.setPriority(firebaseAuth.getUid());
                            users.child(firebaseAuth.getUid()).
                                    setValue(newUser);
                            //adding user settings info for private cabinet
                            addUserSettingsInfo();
                            startActivity(new Intent(LoginActivity.this, TableScore.class));
                            finish();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),
                                e.getMessage(), Snackbar.LENGTH_LONG).show();
                        Log.wtf("wtf", e);
                    }
                });

    }

    private void addUserSettingsInfo(){

        //adding new user setings to firebase
        UserSettings settings = new UserSettings("adress"
                ,"website","instagram","0");
        DatabaseReference usersSettings = databaseReference.child("settings");
        usersSettings.child(firebaseAuth.getUid()).
                setValue(settings);
    }

}

