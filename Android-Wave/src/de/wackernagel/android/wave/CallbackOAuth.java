package de.wackernagel.android.wave;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class CallbackOAuth extends Activity {

	private static final String TAG = CallbackOAuth.class.getSimpleName();
	
	private OneWave ow;

	@Override
	public void onResume() {
		super.onResume();

		// extract the OAUTH access token if it exists
		Uri uri = this.getIntent().getData();

		if (uri != null) {
			ow = (OneWave) getApplication();

			// This should be here, instead a filter may be a better option
			ow.start();
			
			startActivity(new Intent(this, WaveList.class));
			finish(); //TODO: this is ugly
		} else {
			Log.e( TAG, "No OAUTH access token retrieved." );
		}
	}
}
