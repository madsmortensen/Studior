package madsmortensen.studior;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

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
    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        // Add a marker in Copenhagen and move the camera
//        LatLng Copenhagen = new LatLng(55.67, 12.56);
//        mMap.addMarker(new MarkerOptions().position(Copenhagen).title("Marker in Copenhagen"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Copenhagen));
//    }

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

        mChildEventListener = mProfileRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseMarker marker = dataSnapshot.getValue(FirebaseMarker.class);
                String StudioName = marker.getStudioName();
                String StudioAdress = marker.getStudioAddress();
                String StudioDescription = marker.getStudioDescription();
                double latitude = marker.getLatitude();
                double longitude = marker.getLongitude();

                LatLng location = new LatLng(latitude,longitude);
                map.addMarker(new MarkerOptions()
                        .position(location)
                        .title(StudioName)
                        .snippet(StudioAdress)
                        .snippet(StudioDescription));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}