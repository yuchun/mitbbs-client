package edu.ufl.cise.android.dataModel;

import java.util.ArrayList;
import java.util.List;

public class BaseDataModel {
	public static interface OnDataModelChangeObserver{
		public void onDataModelChanged();
	}
	
	private List<OnDataModelChangeObserver> observerList = new ArrayList<OnDataModelChangeObserver>();
	
	public void registerOnDataModelChangeObserver(OnDataModelChangeObserver observer){
		observerList.add(observer);
	}
	
	public void unregisterOnDataModelChangeObserver(OnDataModelChangeObserver observer){
		observerList.remove(observer);
	}
	
	public void notifyObservers(){
		for(OnDataModelChangeObserver observer: observerList)
			observer.onDataModelChanged();
	}
}
