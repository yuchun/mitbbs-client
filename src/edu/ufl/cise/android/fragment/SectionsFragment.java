package edu.ufl.cise.android.fragment;

import edu.ufl.cise.android.R;
import edu.ufl.cise.android.R.layout;
import edu.ufl.cise.android.adapter.TopicListAdapter;
import edu.ufl.cise.android.dataModel.GuidanceDataModel;
import edu.ufl.cise.android.dataModel.TopicListDataModel;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SectionsFragment extends Fragment 
					implements OnRefreshListener{
	private TopicListDataModel model;
	private static final String sectionURL = "www.mitbbs.com/mitbbs_bbssec.php";
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sections, container, false);
        model = new TopicListDataModel(getActivity(), sectionURL);
        
        Log.i("onCreateView", "done");
        TopicListAdapter adapter = new TopicListAdapter(getActivity(), R.layout.listitem_link, model, model.getTopicLists(), TopicListAdapter.LINKTYPE_BOARD);
        ListView lv = (ListView)rootView.findViewById(R.id.list_hotboards);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(adapter);

        if(model.empty())
        	model.update(getActivity());
        
        SwipeRefreshLayout layout = (SwipeRefreshLayout)rootView.findViewById(R.id.board_section_container);
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
