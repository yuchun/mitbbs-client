package edu.ufl.cise.android.fragment;

import edu.ufl.cise.android.R;
import edu.ufl.cise.android.R.layout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IndividualCenterFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_individual_center, container, false);

        return rootView;
    }
}
