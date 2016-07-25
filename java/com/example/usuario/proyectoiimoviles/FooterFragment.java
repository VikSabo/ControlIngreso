package com.example.usuario.proyectoiimoviles;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Usuario on 10/05/2016.
 */
public class FooterFragment extends Fragment{

    AnalyticsTracker analyticsTracker;

    public FooterFragment () {super();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analyticsTracker = AnalyticsTracker.getAnalyticsTracker(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_footer, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsTracker.trackScreen("FooterFragment");
    }
}
