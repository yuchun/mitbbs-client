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
import edu.ufl.cise.android.adapter.PostAdapter;
import edu.ufl.cise.android.data.Topic;
import edu.ufl.cise.android.dataModel.GuidanceDataModel;
import edu.ufl.cise.android.dataModel.PostDataModel;
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


/**
 * A fragment that launches other parts of the demo application.
 */
public class PostFragment extends Fragment 
						implements OnRefreshListener{

	private PostDataModel model;
	private Button btn_fPage;
	private Button btn_pPage;
	private Button btn_nPage;
	private EditText edt_pageNum;
	private Button btn_go;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
		Intent intent = getActivity().getIntent();
		String topic = intent.getStringExtra("topic");
		String link = intent.getStringExtra("link");
		getActivity().setTitle("主题：" + topic);
		
		model = new PostDataModel(topic, link);
		
		PostAdapter adapter = new PostAdapter(getActivity(), R.layout.listitem_post, model);
		ListView lv = (ListView)rootView.findViewById(R.id.list_post);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(adapter);
	
		SwipeRefreshLayout layout = (SwipeRefreshLayout)rootView.findViewById(R.id.post_container);
		if(layout != null)
			layout.setOnRefreshListener(this);
		
		btn_fPage = (Button)rootView.findViewById(R.id.button_firstpage);
		btn_pPage = (Button)rootView.findViewById(R.id.btn_prepage);
		btn_nPage = (Button)rootView.findViewById(R.id.btn_nextpage);
		btn_go = (Button)rootView.findViewById(R.id.btn_go);
		edt_pageNum = (EditText)rootView.findViewById(R.id.edit_page);
		
		setBtnEnable();
		
		btn_fPage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				model.getPage().setCurrentPage(1);
				setBtnEnable();
				model.update(getActivity());
			}
			
		});

		btn_pPage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				if(model.getPage().getCurrentPage() == 1)
					return;
				model.getPage().setCurrentPage(model.getPage().getCurrentPage()-1);
				setBtnEnable();
				model.update(getActivity());
			}
		});
		
		
		btn_nPage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				if(model.getPage().getCurrentPage() == model.getPage().getTotalPage())
					return;
				model.getPage().setCurrentPage(model.getPage().getCurrentPage()+1);
				setBtnEnable();
				model.update(getActivity());
			}
		});
			
		btn_nPage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				int page = Integer.getInteger(edt_pageNum.getText().toString()).intValue();
				if(page < 1 || page > model.getPage().getTotalPage())
					return;
				model.getPage().setCurrentPage(page);
				setBtnEnable();
				model.update(getActivity());
			}
		});

		edt_pageNum.clearFocus();
		
		edt_pageNum.setText(model.getPage().getCurrentPage()+"");
		return rootView;
	}
	
	@Override
	public void onStart(){
		super.onStart();
		if(model.empty())
			model.update(getActivity());		
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
		if(model.getPage().getCurrentPage() == 1){
			btn_fPage.setEnabled(false);
			btn_pPage.setEnabled(false);
		}
		if(model.getPage().getCurrentPage() == model.getPage().getTotalPage())
			btn_nPage.setEnabled(false);
		if(model.getPage().getTotalPage() == 1)
			btn_go.setEnabled(false);
			
	}
}