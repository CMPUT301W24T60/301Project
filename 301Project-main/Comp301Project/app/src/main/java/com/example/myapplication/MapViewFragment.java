package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

public class MapViewFragment extends Fragment {

    private MapView mapView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map_view, container, false);
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK); // set the default tile source

        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        //IMapController mapController = mapView.getController();
        //mapController.setZoom(15.0);
        //GeoPoint startPoint = new GeoPoint(eventLatitude, eventLongitude);
        //mapController.setCenter(startPoint);

        // Add markers or overlays here
        // ...

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume(); // needed for compass, my location overlays
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();  // needed for compass, my location overlays
    }
}
