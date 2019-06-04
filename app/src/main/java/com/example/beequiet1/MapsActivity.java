package com.example.beequiet1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Button button;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
   /* GoogleMap gogleMap;
    LatLng tg = gogleMap.getCameraPosition().target;
    CircleOptions circle = new CircleOptions()
            .center(new LatLng(tg.latitude,tg.longitude))
            .radius(100)
            .strokeColor(Color.BLUE)//border color
            .strokeWidth(3.0f)//border width
            .fillColor(0x200000ff);
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();


    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() +
                            "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    final SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(MapsActivity.this);


                }
            }


        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng ltLg = googleMap.getCameraPosition().target;
       final CircleOptions circle = new CircleOptions()
                .center(new LatLng(ltLg.latitude,ltLg.longitude))
                .radius(500)
                .strokeColor(Color.BLUE)//border color
                .strokeWidth(3.0f)//border width
                .fillColor(0x200000ff);
        googleMap.addCircle(circle);

        final LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).
                title("I am here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        //googleMap.addMarker(markerOptions);


        button = (Button) findViewById(R.id.button);
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
        googleMap.setMyLocationEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng ltLg = googleMap.getCameraPosition().target;
                //circle.center(new LatLng(ltLg.latitude,ltLg.longitude));
                CircleOptions circle = new CircleOptions()
                        .center(new LatLng(ltLg.latitude,ltLg.longitude))
                        .radius(100)
                        .strokeColor(Color.BLUE)//border color
                        .strokeWidth(3.0f)//border width
                        .fillColor(0x200000ff);
               circle.center(new LatLng(ltLg.latitude,ltLg.longitude));
                MarkerOptions marker = new MarkerOptions().position(ltLg).
                        title(ltLg.toString());
                //googleMap.addMarker(marker);
                Toast.makeText(getApplicationContext(), ltLg.toString(),Toast.LENGTH_SHORT).show();


                googleMap.addCircle(circle);
                System.out.print(ltLg.toString());
                setActivity(ltLg.toString());
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_CODE:

                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }


    }
    private void setActivity(String ltLg) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("coordinates", ltLg);
        startActivity(i);
    }
}
