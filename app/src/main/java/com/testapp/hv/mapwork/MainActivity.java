package com.testapp.hv.mapwork;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;

import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView tv1;
    double lat = 20.423156;
    double lng = -27.084917;
    LocationManager mLocationManager = null;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //          Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
//                String maplLabel = "ABC Label";
//        Uri.parse("geo:0,0?q=24.423156,-67.084917");
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);   //default
        tv1 = (TextView) findViewById(R.id.tv1);
//        changeTolondon();
        Timestamp();
        //Toast.makeText(getApplicationContext(),String.valueOf(GregorianCalendar.getInstance().getTime())+"\n",Toast.LENGTH_LONG).show();
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                int g=5/0;
                forceCrash();
*/

                /*
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=29.9774614,31.1329645&cbp=0,30,0,0,-15");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
*/
/*
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
               Uri.parse("geo:0,0?q="+lat+","+lng));//+"&z=16 (" + maplLabel + ")"));
                startActivity(intent);
*/
/*
        final Intent intent = new
                Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?" +
                "saddr="+ lat + "," + lng + "&daddr=" + 24.78 + "," +
                67.8901));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);
*/
                Uri gmmIntentUri = Uri.parse("google.navigation:q=Sunway Lagoon Water Park,Karachi+Pakistan");
                // Uri gmmIntentUri = Uri.parse("google.navigation:q=Minar-e-Pakistan,Lahore+Pakistan");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                //    showDirections();
            }
        });
        criteria.setCostAllowed(false);
        // get the best provider depending on the criteria
        String provider = mLocationManager.getBestProvider(criteria, false);
        //   mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        //         mLocationManager.requestLocationUpdates(provider, 2000, 1, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        geocoder = new Geocoder(
                MainActivity.this, Locale
                .getDefault());
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();
        // TODO: Move this to where you establish a user session
        logUser();
        // TODO: Use your own attributes to track content views in your app
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Tweet")
                .putContentType("Video")
                .putContentId("1234")
                .putCustomAttribute("Favorites Count", 20)
                .putCustomAttribute("Screen Orientation", "Landscape"));
    }
    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("Hassaan121");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User2");
    }
    public void forceCrash() {
        throw new RuntimeException("This is a crash");
    }
    // TODO: Move this method and use your own event name to track your key metrics
    public void onKeyMetric() {
        // TODO: Use your own string attributes to track common values over time
        // TODO: Use your own number attributes to track median value over time
        Answers.getInstance().logCustom(new CustomEvent("Video Played")
                .putCustomAttribute("Category", "Comedy")
                .putCustomAttribute("Length", 350));
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(getApplicationContext(), "Status:\n" + lat, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getApplicationContext(), "Enabled" + lat, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplicationContext(), "Disabled" + lat, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(final Location location) {
        //your code here            showDirections();

        lat = location.getLatitude();
        lng = location.getLongitude();
        String area = null, CityName = null, country = null;
        try {
            Log.v("log_tag", "latitude" + lat);
            Log.v("log_tag", "longitude" + lng);
            addresses = geocoder.getFromLocation(lat,
                    lng, 1);
            Log.v("log_tag", "addresses+)_+++" + addresses);
//           CityName = addresses.get(0).getAddressLine(0);
            area = addresses.get(0).getAddressLine(0);
            CityName = addresses.get(0).getAddressLine(1);
            country = addresses.get(0).getAddressLine(2);
            Log.v("log_tag", "CityName" + CityName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //      tv1.setText(addresses.toString());
//        Toast.makeText(getApplicationContext(),  "\n"+getMy10DigitPhoneNumber()+"\n"+area+"\n"+CityName+"\n"+country,Toast.LENGTH_LONG).show();
        Location.distanceBetween(24.39247923, 67.9878921312, lat, lng, res);
    }

    float[] res = {2.823f, 6.234f};

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }
    }

    private String getMyPhoneNumber() {
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    private String getMy10DigitPhoneNumber() {
        String s = getMyPhoneNumber();
        return s != null && s.length() > 2 ? s.substring(2) : null;
    }

    public void showDirections() {
        final Intent intent = new
                Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                "saddr=" + lat + "," + lng + "&daddr=" + 24.78 + "," +
                67.8901));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
    long unixTime = 0;
    public void Timestamp() {
        unixTime = System.currentTimeMillis() / 1000L;
//   unixTime=1451635688;
        TimeZone  london = TimeZone.getTimeZone("Europe/London");
        unixTime = unixTime + london.getOffset(unixTime);
        convertTime();
    }

    public void convertTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy z");
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss a");

        sdf.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        sdf1.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        sdf.format(new Date(unixTime * 1000));
        Toast.makeText(getApplicationContext(), String.valueOf(sdf.format(unixTime * 1000)) + "\n" + unixTime, Toast.LENGTH_LONG).show();
        tv1.setText("\n\n"+unixTime + "\n\n" + String.valueOf(sdf.format(unixTime * 1000))+"\n"+String.valueOf(sdf1.format(unixTime * 1000)));
    }
    public void changeTolondon() {
        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
        f.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        String text = String.valueOf(f.format(GregorianCalendar.getInstance().getTimeInMillis()));
        Timestamp ts = new Timestamp(Date.parse(text));
        Calendar c1 = new GregorianCalendar().getInstance();
        c1.setTimeZone(f.getTimeZone());
        String myfrmt = c1.get(Calendar.YEAR) + "" + c1.get(Calendar.MONTH) + "" + c1.get(Calendar.DAY_OF_MONTH) + "" + c1.get(Calendar.HOUR_OF_DAY) + "" + c1.get(Calendar.MINUTE) + "";
     //   tv1.setText(String.valueOf(f.format(GregorianCalendar.getInstance().getTime())) + "\n" + myfrmt);
        // Toast.makeText(getApplicationContext(),String.valueOf(text)+"\n",Toast.LENGTH_LONG).show();
   //     unixTime = Long.valueOf(myfrmt);
        convertTime();
    }
}
