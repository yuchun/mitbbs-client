package edu.ufl.cise.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BoardActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board);
	}
}
