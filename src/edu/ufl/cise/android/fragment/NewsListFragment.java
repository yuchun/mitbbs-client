package edu.ufl.cise.android.fragment;

import edu.ufl.cise.android.R;
import edu.ufl.cise.android.R.layout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsListFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newslist, container, false);
        return rootView;
    }
}
