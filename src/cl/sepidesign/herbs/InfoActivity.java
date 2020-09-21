package cl.sepidesign.herbs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class InfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
	}

	public void goToBotica(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.boticadelalma.cl/")));
	}
}
