package edu.ufl.cise.android;
import android.app.Application;


public class MitbbsApplication extends Application {
	private static MitbbsApplication application;
	
	private MitbbsApplication(){
	}
	
	public static MitbbsApplication getInstance(){
		if(application == null)
			application = new MitbbsApplication();
		return application;
	}
}
