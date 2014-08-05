package edu.ufl.cise.android.task;

import java.util.List;

import org.htmlparser.util.ParserException;
import edu.ufl.cise.android.dataModel.GuidanceDataModel;
import edu.ufl.cise.android.dataModel.GuidanceDataModel.Guidances;
import edu.ufl.cise.android.util.WebCrawler;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LoadGuidanceTask extends AsyncTask<String, Integer, List<Guidances>> {

	GuidanceDataModel model;
	ProgressDialog dialog;
	
	public LoadGuidanceTask(Context ctx, GuidanceDataModel guidanceDataModel){
		this.model = guidanceDataModel;
		dialog = new ProgressDialog(ctx);
	}
	
	@Override
	protected void onPreExecute(){
		dialog.setMessage("Loading Guidance...");
		dialog.show();
	}
	
	@Override
	protected List<Guidances> doInBackground(String... params) {
		// TODO Auto-generated method stub
		List<Guidances> links = null;
		try {
			links = WebCrawler.getInstance().parseGuidance(model);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialog.cancel();
			
		return links;
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress){
		;//
	}
	
	@Override
	protected void onPostExecute(List<Guidances> result){
		model.refresh(result);
	}
}
