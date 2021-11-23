package com.example.duanaeth.FragmentLayout.NhaTro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duanaeth.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Tab_MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(21.030653, 	105.847130);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Nhà trọ AETH HOUSE"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            LatLng sydney2 = new LatLng(12.709979, 	108.074630);
            googleMap.addMarker(new MarkerOptions().position(sydney2).title("Nhà trọ 2"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney2));

            LatLng sydney3 = new LatLng(18.709979, 	108.074430);
            googleMap.addMarker(new MarkerOptions().position(sydney3).title("Nhà trọ 3"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney3));

            LatLng sydney5 = new LatLng(13.709979, 	109.074630);
            googleMap.addMarker(new MarkerOptions().position(sydney5).title("Nhà trọ 4"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney5));

            LatLng sydney4 = new LatLng(16.709979, 	107.074630);
            googleMap.addMarker(new MarkerOptions().position(sydney4).title("Nhà trọ 5"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney4));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab__maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}