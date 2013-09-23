package de.wackernagel.android.wave;

import android.app.Activity;

public class Waves extends Activity {

	
	@Override
	protected void onResume() {
		super.onResume();
		this.getIntent();
	}
}
