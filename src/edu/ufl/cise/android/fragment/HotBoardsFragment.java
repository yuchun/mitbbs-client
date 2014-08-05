package edu.ufl.cise.android.fragment;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import edu.ufl.cise.android.MitbbsApplication;
import edu.ufl.cise.android.R;
import edu.ufl.cise.android.R.id;
import edu.ufl.cise.android.R.layout;
import edu.ufl.cise.android.adapter.TopicListAdapter;
import edu.ufl.cise.android.data.Topic;
import edu.ufl.cise.android.dataModel.GuidanceDataModel;
import edu.ufl.cise.android.task.LoadGuidanceTask;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A fragment that launches other parts of the demo application.
 */
public class HotBoardsFragment extends Fragment 
						implements OnRefreshListener{

	private GuidanceDataModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hotboards, container, false);
        model = new GuidanceDataModel(getActivity());
        
        Log.i("onCreateView", "done");
        TopicListAdapter adapter = new TopicListAdapter(getActivity(), R.layout.listitem_link, model, model.getHotboards(), TopicListAdapter.LINKTYPE_BOARD);
        ListView lv = (ListView)rootView.findViewById(R.id.list_hotboards);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(adapter);

        if(model.empty())
        	model.update(getActivity());
        
        SwipeRefreshLayout layout = (SwipeRefreshLayout)rootView.findViewById(R.id.hotboards_container);
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
