package cl.sepidesign.herbs;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.manager.DataManager;
import cl.sepidesign.herbs.model.Method;

public class MethodDetailActivity extends Activity
{
	private Method selectedMethod;

	private TextView title;
	private ImageView img;
	private TextView desc;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_method_detail);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		title = (TextView) findViewById(R.id.method_title);
		img = (ImageView) findViewById(R.id.method_img);
		desc = (TextView) findViewById(R.id.method_desc);

		int id = getIntent().getIntExtra("id", -1);

		if (id != -1)
			selectedMethod = DataManager.getMethodById(id);

		if (selectedMethod != null)
		{
			title.setText(selectedMethod.getName());
			title.setTypeface(tf);
			img.setImageResource(selectedMethod.getImgId());
			desc.setText(selectedMethod.getDescId());
			desc.setTypeface(tf);
		}
	}
}
