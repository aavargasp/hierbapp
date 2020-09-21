package cl.sepidesign.herbs;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import cl.sepidesign.herbs.manager.DataManager;
import cl.sepidesign.herbs.model.Herb;

public class AnnotationsActivity extends Activity {
	private Herb selectedHerb;
	private EditText annotation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_annotations);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");

		annotation = (EditText) findViewById(R.id.herb_detail_annotation);

		selectedHerb = DataManager.getLastHerb();

		if (selectedHerb != null) {
			annotation.setTypeface(tf);

			if (selectedHerb.getAnnotations() != null)
				annotation.setText(selectedHerb.getAnnotations());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.annotations_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.save_annotation:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		selectedHerb.setAnnotations(annotation.getText().toString());
		DataManager.saveHerbs();
		Toast.makeText(this, "Apuntes guardados", Toast.LENGTH_SHORT).show();
		super.onBackPressed();
	}
}
