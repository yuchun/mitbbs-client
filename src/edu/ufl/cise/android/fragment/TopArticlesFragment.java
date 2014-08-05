package edu.ufl.cise.android.fragment;

import edu.ufl.cise.android.R;
import edu.ufl.cise.android.adapter.TopicListAdapter;
import edu.ufl.cise.android.dataModel.GuidanceDataModel;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A fragment that launches other parts of the demo application.
 */
public class TopArticlesFragment extends Fragment 
						implements OnRefreshListener{

	private GuidanceDataModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_toparticles, container, false);
        model = new GuidanceDataModel(getActivity());
        
        Log.i("onCreateView", "done");
        TopicListAdapter adapter = new TopicListAdapter(getActivity(), R.layout.listitem_link, model, model.getToparticles(), TopicListAdapter.LINKTYPE_POST);
        ListView lv = (ListView)rootView.findViewById(R.id.list_toparticles);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(adapter);

        if(model.empty())
        	model.update(getActivity());
        
        SwipeRefreshLayout layout = (SwipeRefreshLayout)rootView.findViewById(R.id.toparticles_container);
        if(layout != null){
        	layout.setOnRefreshListener(this);
        }
        
        return rootView;
    }

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		model.update(getActivity());
	}
 
}
