package taiwansafety.com.accidentsummary.View;

import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import taiwansafety.com.accidentsummary.Model.Accident;
import taiwansafety.com.accidentsummary.Parser.AccidentParser;
import taiwansafety.com.accidentsummary.R;
import taiwansafety.com.accidentsummary.Util.Config;
import taiwansafety.com.accidentsummary.Util.JSONAssetHelper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;

    private ArrayList<Accident> accidents;

    private GoogleApiClient mGoogleApiClient;

    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initVars();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        if(mLastLocation != null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 16f));
        }

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     * Feed the json data into AccidentObjects
     */
    private void initVars() {

        JSONArray jsonArray = JSONAssetHelper.loadJSON(MapsActivity.this, Config.FILENAME);
        accidents = AccidentParser.parseAccident(jsonArray);

    }

    public void performAccidentSearch(View view) {

        if (mLastLocation != null) {
            showAccidents(accidents);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mMap != null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 16f));
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Show nearby {@link Accident} in an alertDialog
     *
     * */
    private void showAccidents(ArrayList<Accident> accidents) {

        List<String> mAccidents = new ArrayList<String>();
        for(Accident accident : accidents){
            mAccidents.add(accident.getAccident());
        }

        final CharSequence[] Accidents = mAccidents.toArray(new String[mAccidents.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("附近車禍資料 (請小心駕駛)");
        dialogBuilder.setItems(Accidents, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();

    }

    private ArrayList<Accident> findNearbyAccidents(ArrayList<Accident> accidents){

        ArrayList<Accident> nearbyAccidents = new ArrayList<>();

        for(Accident accident : accidents){
            if(accident.getLocation() != null && accident.getLocation().distanceTo(mLastLocation) < Config.RADIUS){
                nearbyAccidents.add(accident);
            }
        }

        return nearbyAccidents;
    }

}
