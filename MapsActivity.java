package madsmortensen.studior;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mProfileRef = firebaseDatabase.getReference("Studios");
    ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button addAStudio = (Button) findViewById(R.id.addAStudio);
        addAStudio.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, Rent.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        googleMap.setOnMarkerClickListener(this);
        LatLng  Copenhagen = new LatLng(55.67, 12.56);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Copenhagen, 18));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //get marker info from Firebase Database and add to map
        addMarkersToMap(googleMap);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onStop(){
        if(mChildEventListener != null)
            mProfileRef.removeEventListener(mChildEventListener);
        super.onStop();
    }

    private void addMarkersToMap(final GoogleMap map){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference studiosRef = rootRef.child("Studios");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    FirebaseMarker marker = ds.getValue(FirebaseMarker.class);
                    String StudioName = marker.getStudioName();
                    String StudioAdress = marker.getStudioAddress();
                    String StudioDescription = marker.getStudioDescription();
                    double latitude = marker.getLatitude();
                    double longitude = marker.getLongitude();
                    LatLng location = new LatLng(latitude, longitude);

                    map.addMarker(new MarkerOptions()
                            .position(location)
                            .title(StudioName)
                            .snippet(StudioAdress)
                            .snippet(StudioDescription));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        studiosRef.addListenerForSingleValueEvent(valueEventListener);


        }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


}