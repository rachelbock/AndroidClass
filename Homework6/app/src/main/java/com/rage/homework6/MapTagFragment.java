package com.rage.homework6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Fragment to display map and allow user to place tags on the map.
 */
public class MapTagFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mapView;
    private LatLng latLong;
    private User mainUser;
    @Bind(R.id.map_fragment_toggle_button)
    ToggleButton toggleButton;
    public static final String ARG_TAG_FRAGMENT = "MapTagFragment";
    public static final String TAG = MapTagFragment.class.getSimpleName();
    private UserLocalDatabaseSQLiteHelper userLocalDatabaseSQLiteHelper;



    public MapTagFragment() {
        // Required empty public constructor
    }

    public static MapTagFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_TAG_FRAGMENT, user);
        MapTagFragment fragment = new MapTagFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map_tag, container, false);
        ButterKnife.bind(this, rootView);
        mapView = (MapView) rootView.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        userLocalDatabaseSQLiteHelper = UserLocalDatabaseSQLiteHelper.getInstance(getContext());

        mainUser = getArguments().getParcelable(ARG_TAG_FRAGMENT);


        if (mainUser != null) {
            Log.d(TAG, "Logged in with user: " + mainUser.getName() + "; id: " + mainUser.getIdNumber());
        }
        else {
            Log.d(TAG, "mainUser is null");
        }

        return rootView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);

        ArrayList<MapTag> mapTags = userLocalDatabaseSQLiteHelper.getMapTagsForUser(mainUser.getIdNumber());

        if (!mapTags.isEmpty()) {
            createMapTagsForUser(mapTags);
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(Marker marker) {
                if (toggleButton.isChecked()) {
                    LatLng markerLatlng = marker.getPosition();
                    userLocalDatabaseSQLiteHelper.removeMapTag(markerLatlng);
                    marker.remove();
                    return true;
                }
                else {
                    return false;
                }

            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                latLong = latLng;

                if(toggleButton.isChecked()) {
                   //do nothing
                }
                else {
                    map.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .draggable(true)
                    );

                    MapTagDialogFragment dialogFragment = new MapTagDialogFragment();

                    dialogFragment.setTargetFragment(MapTagFragment.this, 0);
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");

                }

            }
        });

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }


    public void onPositiveButtonClicked(String title, String description) {
        MapTag mapTag = new MapTag(mainUser.getIdNumber(), latLong.latitude, latLong.longitude, title, description);
        Log.d(TAG, mapTag.toString());
        userLocalDatabaseSQLiteHelper.insertMapTag(mapTag);
    }


    public void createMapTagsForUser(ArrayList<MapTag> mapTags) {

        for (int i = 0; i < mapTags.size(); i++) {
            MapTag mapTag = mapTags.get(i);
            LatLng latLng = new LatLng(mapTag.getLatitude(), mapTag.getLongitude());
            map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(mapTag.getTagTitle())
                .snippet(mapTag.getTagDescription())
            );
        }

    }

}
