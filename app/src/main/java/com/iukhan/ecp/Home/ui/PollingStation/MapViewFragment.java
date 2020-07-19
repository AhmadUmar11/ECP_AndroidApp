package com.iukhan.ecp.Home.ui.PollingStation;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.iukhan.ecp.R;
import com.iukhan.ecp.UserConstants;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MapViewFragment extends Fragment implements RoutingListener {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final String MyPREFERENCES = "MyPrefs";
    protected LatLng start = null;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted = false;
    private MapView mMapView;
    private List<Polyline> polylines = null;
    private String name, email, lat, lon;
    private GoogleMap googleMap;
    private LatLng ps_location, dest;
    private Location mLastKnownLocation;

    public MapViewFragment() {
    }

    public void doIt(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String name, email, lat, lon;

        lat =  prefs.getString("lat", "No lattitude defined");
        lon =  prefs.getString("long", "No Longitude defined");

        double l = Double.parseDouble(lat);
        double x = Double.parseDouble(lon);
        ps_location = new LatLng(l, x);
        System.out.println(lat+" "+lon);
        dest = ps_location;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, null, false);
        doIt(getActivity().getApplicationContext());
        Button directions = view.findViewById(R.id.route);
        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });
        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
                updateLocationUI();
                googleMap.addMarker(new MarkerOptions().position(ps_location).title("Polling Station").snippet("Location of Polling Station"));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(ps_location).zoom(14).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        return view;
    }

    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void getDeviceLocation() {
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        mLastKnownLocation = task.getResult();
                        System.out.println("hereeeeeeeeeeeee"+mLastKnownLocation);
                        Findroutes(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), ps_location);
                    } else {
                        Log.d("Error", "Current location is null. Using defaults.");
                        Log.e("Error", "Exception: %s", task.getException());
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }
            });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void Findroutes(LatLng Start, LatLng End) {
        if (Start == null || End == null) {
            Toast.makeText(getActivity(), "Unable to get location", Toast.LENGTH_LONG).show();
        } else {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(Start).zoom(14).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key(getResources().getString(R.string.google_api_key))
                    .build();
            routing.execute();
        }
    }

    @Override
    public void onRoutingFailure(com.directions.route.RouteException e) {
        Toast.makeText(getContext(), "Route error: " + e, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(getActivity(), "Finding Route...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingCancelled() {

    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if (polylines != null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i < route.size(); i++) {

            if (i == shortestRouteIndex) {
                polyOptions.color(getResources().getColor(R.color.colorPrimary));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = googleMap.addPolyline(polyOptions);
                polylineStartLatLng = polyline.getPoints().get(0);
                int k = polyline.getPoints().size();
                polylineEndLatLng = polyline.getPoints().get(k - 1);
                polylines.add(polyline);

            } else {

            }

        }

//
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        googleMap.addMarker(endMarker);
    }
}