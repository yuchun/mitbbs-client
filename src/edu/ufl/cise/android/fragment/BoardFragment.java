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

import edu.ufl.cise.android.PostActivity;
import edu.ufl.cise.android.R;
import edu.ufl.cise.android.R.id;
import edu.ufl.cise.android.R.layout;
import edu.ufl.cise.android.adapter.BoardListAdapter;
import edu.ufl.cise.android.adapter.PostAdapter;
import edu.ufl.cise.android.adapter.TopicListAdapter;
import edu.ufl.cise.android.data.Topic;
import edu.ufl.cise.android.dataModel.BaseDataModel;
import edu.ufl.cise.android.dataModel.GuidanceDataModel;
import edu.ufl.cise.android.dataModel.PostDataModel;
import edu.ufl.cise.android.dataModel.TopicListDataModel;
import edu.ufl.cise.android.task.LoadGuidanceTask;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A fragment that launches other parts of the demo application.
 */
public class BoardFragment extends Fragment 
						implements OnRefreshListener, BaseDataModel.OnDataModelChangeObserver{

	private TopicListDataModel model;
	private Button btn_fPage;
	private Button btn_pPage;
	private Button btn_nPage;
	private Button btn_lPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_board, container, false);
		Intent intent = getActivity().getIntent();
		String topic = intent.getStringExtra("topic");
		String link = intent.getStringExtra("link");
		getActivity().setTitle(topic);
		
		model = new TopicListDataModel(getActivity(), link);
		
		model.registerOnDataModelChangeObserver(this);
		
		TopicListAdapter adapter = new TopicListAdapter(getActivity(), R.layout.listitem_link, model, model.getTopicLists(), TopicListAdapter.LINKTYPE_POST);

		ListView lv = (ListView)rootView.findViewById(R.id.list_board);
		if(lv == null){
			Log.i("BoardFragment.onCreateView", "Cannot find list_board");
			return null;
		}
			
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(adapter);
	
		SwipeRefreshLayout layout = (SwipeRefreshLayout)rootView.findViewById(R.id.board_container);
		if(layout != null)
			layout.setOnRefreshListener(this);
		
		btn_fPage = (Button)rootView.findViewById(R.id.board_btn_firstpage);
		btn_pPage = (Button)rootView.findViewById(R.id.board_btn_prepage);
		btn_nPage = (Button)rootView.findViewById(R.id.board_btn_nextpage);
		btn_lPage = (Button)rootView.findViewById(R.id.board_btn_lastpage);
		
		setBtnEnable();
		
		btn_fPage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				model.getList().setCurrentPage(model.getList().getFirstPage());
				setBtnEnable();
				model.update(getActivity());
			}
			
		});

		btn_pPage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				model.getList().setCurrentPage(model.getList().getPreviousPage());
				setBtnEnable();
				model.update(getActivity());
			}
		});
		
		
		btn_nPage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				model.getList().setCurrentPage(model.getList().getNextPage());
				setBtnEnable();
				model.update(getActivity());
			}
		});
			
		btn_lPage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				
				model.getList().setCurrentPage(model.getList().getLastPage());
				model.update(getActivity());
			}
		});
		return rootView;
	}
	
	@Override
	public void onStart(){
		super.onStart();
		if(model.empty()){
			model.update(getActivity());
		}
	}


	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		model.update(getActivity());
	}
	
	public void returnBoard(){
		//TODO
	}
	
	private void setBtnEnable(){
		if(model.getList().getFirstPage().equals(""))
			btn_fPage.setEnabled(false);
		else
			btn_fPage.setEnabled(true);
		
		if(model.getList().getPreviousPage().equals(""))
			btn_pPage.setEnabled(false);
		else
			btn_pPage.setEnabled(true);
		
		if(model.getList().getNextPage().equals(""))
			btn_nPage.setEnabled(false);
		else
			btn_nPage.setEnabled(true);
		
		if(model.getList().getLastPage().equals(""))
			btn_lPage.setEnabled(false);
		else
			btn_lPage.setEnabled(true);
			
	}

	@Override
	public void onDataModelChanged() {
		// TODO Auto-generated method stub
		setBtnEnable();
	}
}