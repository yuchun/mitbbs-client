package edu.ufl.cise.android.dataModel;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import edu.ufl.cise.android.R;
import edu.ufl.cise.android.data.Link;
import edu.ufl.cise.android.task.LoadGuidanceTask;

public class GuidanceDataModel extends BaseDataModel{
	public static final int ENUM_TOPARTICLES = 0;
	public static final int ENUM_HOTBOARDS = 1;
	public static final int ENUM_HOTCLUBS = 2;
	public static final int ENUM_END = 3;
	
	public static class Guidances{
		String sectionName;
		ArrayList<Link> contents;
		public Guidances(String name){
			sectionName = name;
			contents = new ArrayList<Link>();
		}
		public String getName(){
			return sectionName;
		}
		public void setName(String name){
			sectionName = name;
		}
		public List<Link> getContents(){
			return contents;
		}
		public void setContents(List<Link> newContents){
			contents.clear();
			contents.addAll(newContents);
		}
		public void refresh(Guidances newG){
			sectionName = newG.getName();
			setContents(newG.getContents());
		}
	}
	
	ArrayList<Guidances> guidances;

	private Context context;
	
	public GuidanceDataModel(Context ctx){
		context = ctx;
		guidances = new ArrayList<Guidances>();
		Resources r = context.getResources();
		Guidances g = new Guidances(r.getString(R.string.toparticles));
		guidances.add(g);
		
		g = new Guidances(r.getString(R.string.hotboards));
		guidances.add(g);
		
		g = new Guidances(r.getString(R.string.hotclubs));
		guidances.add(g);

	}
	
	public void refresh(List<Guidances> result){
		if(result.size() > 0){
			for(int i=0; i<ENUM_END; i++){
				guidances.get(i).refresh(result.get(i));
			}
			notifyObservers();
		}else
			Log.e("GuidanceDataModel.refresh()", "Failed to get results.");
	}
	
	public boolean empty(){
		return guidances.get(0).contents.size() == 0;
	}
	
	public void update(Context ctx){       
        new LoadGuidanceTask(ctx, this).execute("");
	}
	
	public List<Link> getToparticles(){
		return guidances.get(ENUM_TOPARTICLES).contents;
	}
	
	
	public List<Link> getHotboards(){
		return guidances.get(ENUM_HOTBOARDS).contents;
	}
	
	
	public List<Link> getHotclubs(){
		return guidances.get(ENUM_HOTCLUBS).contents;
	}
	
	public ArrayList<Guidances> getGuidances(){
		return guidances;
	}
}
