package net.mybluemix.eu_de.maxterminatorx.android;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableScore extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<User> usersList;
    private Button btnGame, btnLeave, btnSetting;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private TextView emptyText, textView;
    private BroadcastReceiver broadcastReceiver;

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    private String score, figths, currentUID;
    private boolean stopServiceIndex = false;
    private Intent start;
    private FirebaseUser user;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_table);
        //ui buttons
        btnGame = findViewById(R.id.btnToGame);
        btnLeave = findViewById(R.id.btnLeaveGame);
        btnSetting = findViewById(R.id.btnSettings);
        btnLeave.setOnClickListener(this);
        btnGame.setOnClickListener(this);
        btnSetting.setOnClickListener(this);

        //recycler view
        recyclerView = findViewById(R.id.recyclerViewList);
        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lin);
        usersList = new ArrayList<>();
        adapter = new MyAdapter(usersList);
        recyclerView.setAdapter(adapter);


        emptyText = findViewById(R.id.tvNoData);
        textView = findViewById(R.id.textView);

        //database handling
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("user");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if (user != null) {
            currentUID = user.getUid();
        }

        Intent intent = this.getIntent();

        state = null;

        if(intent!=null && intent.getExtras()!=null){
            state = intent.getExtras().getString("state","");
        }




        start = new Intent(getApplicationContext(), UserLocation.class);
        //adding all user to list and adapter notification
        updateList();
        isEmpty();
        seyHelloToCurrentUser();

        startService(start);

//        if(!runtime_permissions()){
//          //enable_button();
//        }
    }

    //context menu realisation
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                remooveUser(item.getGroupId());
                break;
            case 1:
                changeUser(item.getGroupId());
                break;
        }

        return super.onContextItemSelected(item);
    }

    public void updateList() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                User user = dataSnapshot.getValue(User.class);

                if(state!=null && state.equals("win")){
                    Log.i(TAG,dataSnapshot.getKey());
                    if(dataSnapshot.getKey().equals(currentUID)){
                        user.setFights(user.getFights()+1);
                        user.setWins(user.getWins()+1);
                        user.setScore((user.getWins())*100-(user.getLooses()==null?0:user.getLooses())*50);
                        reference.child(currentUID).setValue(user);
                    }
                } else if(state!=null && state.equals("lose")){
                    Log.i(TAG,dataSnapshot.getKey());
                    if(dataSnapshot.getKey().equals(currentUID)){
                        user.setFights(user.getFights()+1);
                        user.setLooses(user.getLooses()+1);
                        reference.child(currentUID).setValue(user);
                    }
                }

                usersList.add(user);
                adapter.notifyDataSetChanged();
                isEmpty();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                User user = dataSnapshot.getValue(User.class);
//                int index = getItemIndex(user);
//                usersList.set(index,user);
//                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                int index = getItemIndex(user);
                usersList.remove(index);
                adapter.notifyItemRemoved(index);
                isEmpty();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getItemIndex(User user) {
        int index = -1;
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).key.equals(user.key)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void remooveUser(int position) {
        reference.child(usersList.get(position).key).removeValue();
    }

    private void changeUser(int position) {
        User user = usersList.get(position);
        user.setFights(10);
        Map<String, Object> userValues = user.toMap();
        Map<String, Object> newUser = new HashMap<>();
        newUser.put(user.key, userValues);
        reference.updateChildren(newUser);
    }

    private void isEmpty() {
        if (usersList.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.INVISIBLE);
        }
    }
    //method updated to start and stop location service


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnToGame:
                //new intent that start Userlocation servise


                //starting the game intent
                Intent intent = new Intent(this, AndroidLauncher.class);
                intent.putExtra("mode","user");
                startActivity(intent);
                finish();
                break;
            case R.id.btnLeaveGame:
                //new intent stoping Userlocation service
                finish();
                reference.onDisconnect();
                break;
            case R.id.btnSettings:
                Intent toSettingPage = new Intent(getApplicationContext(), UserAccountActivity.class);
                startActivity(toSettingPage);
                finish();

        }
    }

    //next five methods build to check gps permition use
    //by runtime
    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(TableScore.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(TableScore.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 100);
            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            } else {
                runtime_permissions();
            }
        }
    }

    //handled broadcast receiver
    //show location in login upper text View
    @Override
    protected void onResume() {
        super.onResume();
        startBroadcast();
    }

    //closing broadcast receiver
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        stopService(start);
    }


    public void seyHelloToCurrentUser() {


        Log.i("uid", currentUID);
        reference.child(currentUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String username = dataSnapshot
                        .child("username").getValue(String.class);

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toastlayout,
                        findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText("WELCOME " + username);
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void startBroadcast() {
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    final String loc = (String) intent.getSerializableExtra("area");
                    reference.child(firebaseAuth.getUid()).child("location").setValue(loc);
                    stopServiceIndex = true;
                }
            };
        }else{
            stopServiceIndex = false;
            try{
                broadcastReceiver.abortBroadcast();
            }
            catch(Exception ex){
            }
        }

        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
        if(stopServiceIndex){
            stopService(start);
        }
    }






}
