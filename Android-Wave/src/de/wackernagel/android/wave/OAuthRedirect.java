package de.wackernagel.android.wave;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class OAuthRedirect extends Activity {

	private static final String TAG = OAuthRedirect.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		OneWave ow = (OneWave) getApplication();
		String url = ow.getWaveAPI().getUrl();

		if (url == null) {
			Log.e( TAG, "Phone off or Internet access is not allowed");
			return;
		}

		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
		finish(); //TODO this is ugly
	}

}