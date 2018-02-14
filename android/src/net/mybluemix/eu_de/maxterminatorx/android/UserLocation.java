package net.mybluemix.eu_de.maxterminatorx.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


public class UserLocation extends Service implements Serializable{
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String area;
    List<Address> addresses;
//    public static Location userlocation;
//    private Context context;
//    private RequiresPermission requiresPermission;


    public static Service activetedService;

    public UserLocation(){
        //dafault comstructor
        activetedService = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                /*Toast.makeText(getApplicationContext(),
                        "location"+location.getAltitude()+location.getLongitude()
                        ,Toast.LENGTH_LONG).show();*/
                try{
                    Geocoder geo = new Geocoder(UserLocation.this.getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses.isEmpty()) {
                        //yourtextfieldname.setText("Waiting for Location");
                        //Toast.makeText(getApplicationContext(),"waiting",Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (addresses.size() > 0) {
                            area =(String) " "+addresses.get(0).getLocality();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"error "+e,Toast.LENGTH_LONG).show();
                }
                //final String loc  = ""+location.getAltitude()+""+location.getLongitude();
                Intent i = new Intent("location_update");
                if(area!=null) {
                    i.putExtra("area", area);
                    sendBroadcast(i);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent location = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                location.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(location);
                Toast.makeText(getApplicationContext(),"Turn it on ,HA HA HA !!", Toast.LENGTH_LONG).show();
            }
        };
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,10,locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,3000,10,locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager!=null){
            locationManager.removeUpdates(locationListener);
        }
    }


}
