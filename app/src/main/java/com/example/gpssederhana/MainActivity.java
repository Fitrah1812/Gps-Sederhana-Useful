package com.example.gpssederhana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private LocationManager lm, mylocationManager;
    private LocationListener ls, mylocationListener;
    GoogleMap map;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button go = (Button) findViewById(R.id.idGo);
        mylocationManager = (LocationManager) getSystemService
                (Context.LOCATION_SERVICE);
        mylocationListener = new lokasiListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mylocationManager.requestLocationUpdates
                (LocationManager.GPS_PROVIDER, 0, 200,
                        mylocationListener);
        go.setOnClickListener(op);

    }

//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        map = googleMap;
//        LatLng bengkulu = new LatLng(-3.808590, 102.264435);
//        map.addMarker(new MarkerOptions().position(bengkulu).title("Bengkulu"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(bengkulu));
//    }
    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.idGo:sembunyikanKeyBoard(view);
                    gotoLokasi();break;
            }
        }
    };

    private void gotoLokasi()  {
        EditText lat = (EditText) findViewById(R.id.txtLat);
        EditText lng = (EditText) findViewById(R.id.txtLong);
        EditText zoom = (EditText) findViewById(R.id.korz);

        Double dbllat = Double.parseDouble(lat.getText().toString());
        Double dbllng = Double.parseDouble(lng.getText().toString());
        Float dblzoom = Float.parseFloat(zoom.getText().toString());

        Toast.makeText(this,"Move to Lat:" +dbllat + " Long:" +dbllng,Toast.LENGTH_LONG).show();
        gotoPeta(dbllat,dbllng,dblzoom);
    }
    private class lokasiListener implements LocationListener {
        private TextView txtLat, txtLong;

        @Override
        public void onLocationChanged(Location location) {
            txtLat = (TextView) findViewById(R.id.txtLat);
            txtLong = (TextView) findViewById(R.id.txtLong);

            txtLat.setText(String.valueOf(location.getLatitude()));
            txtLong.setText(String.valueOf(location.getLongitude()));
            Toast.makeText(getBaseContext(), "GPS capture",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ITS = new LatLng(-7.28,112.79);
        mMap.addMarker(new MarkerOptions().position(ITS).
                title("Marker in ITS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ITS,8));
    }

    private void sembunyikanKeyBoard(View v){
        InputMethodManager a = (InputMethodManager)
                getSystemService(INPUT_METHOD_SERVICE);
        a.hideSoftInputFromWindow(v.getWindowToken(),0);
    }
    private void gotoPeta(Double lat, Double lng, float z){
        LatLng Lokasibaru = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().
                position(Lokasibaru).
                title("Marker in  " +lat +":" +lng));
        mMap.moveCamera(CameraUpdateFactory.
                newLatLngZoom(Lokasibaru,z));

    }

}
