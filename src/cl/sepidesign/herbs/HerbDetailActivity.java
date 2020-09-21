package cl.sepidesign.herbs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.adapter.HerbDetailAdapter;
import cl.sepidesign.herbs.manager.DataManager;
import cl.sepidesign.herbs.manager.DataManager.FavoriteResult;
import cl.sepidesign.herbs.model.Herb;

public class HerbDetailActivity extends Activity implements OnCheckedChangeListener
{
	private Herb herb;
	private int[] drawablesIds;

	private ImageView img1;
	private ImageView img2;
	private ImageView img3;
	
	private ImageButton annotationsBtn;

	private TextView text;
	private CheckBox favorite;

	private ExpandableListView detailsListView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_herb_detail);

		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		img1 = (ImageView) findViewById(R.id.herb_detail_img1);
		img2 = (ImageView) findViewById(R.id.herb_detail_img2);
		img3 = (ImageView) findViewById(R.id.herb_detail_img3);
		
		annotationsBtn = (ImageButton) findViewById(R.id.imageButton1);

		text = (TextView) findViewById(R.id.herb_detail_title);
		favorite = (CheckBox) findViewById(R.id.herb_detail_favorite);

		detailsListView = (ExpandableListView) findViewById(R.id.herb_detail_list);

		int id = getIntent().getIntExtra("id", -1);

		if (id != -1)
			herb = DataManager.getHerbById(id);

		if (herb != null)
		{
			text.setText(herb.getName());
			text.setTypeface(tf);

			favorite.setChecked(DataManager.isFavorite(herb));

			HerbDetailAdapter adapter = new HerbDetailAdapter(this, herb);
			detailsListView.setAdapter(adapter);

			drawablesIds = herb.getDetailsDrawablesId();

			img1.setImageResource(drawablesIds[0]);
			img2.setImageResource(drawablesIds[1]);
			img3.setImageResource(drawablesIds[2]);
		}

		favorite.setOnCheckedChangeListener(this);
	}

	@Override
	protected void onResume()
	{
		if (herb != null)
		{
			if (herb.getAnnotations() != null)
				annotationsBtn.setImageResource(R.drawable.mis_apuntes_activo);
			else
				annotationsBtn.setImageResource(R.drawable.mis_apuntes);
		}
			
		super.onResume();
	}

	public void onAnnotationsClicked(View view)
	{
		startActivity(new Intent(this, AnnotationsActivity.class));
	}

	public void onShareClicked(View view)
	{
		if (herb != null)
		{
			Intent sendIntent = new Intent(Intent.ACTION_SEND);
			sendIntent.setType("image/*");

			String uriStr = "android.resource://" + getPackageName() + "/drawble/" + drawablesIds[0];
			Uri uri = Uri.parse(uriStr);
			sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

			sendIntent.putExtra(Intent.EXTRA_TEXT, herb.toShare() + " " + getString(R.string.app_link));

			startActivity(Intent.createChooser(sendIntent, getString(R.string.share)));
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if (isChecked)
		{
			FavoriteResult result = DataManager.addFavoriteHerb(herb);

			if (result.equals(FavoriteResult.FULL))
			{
				Toast.makeText(this, getString(R.string.favorites_full), Toast.LENGTH_SHORT).show();
				favorite.setChecked(false);
			}
		}
		else
			DataManager.removeFavoriteHerb(herb.getId());

		DataManager.saveFavoritesHerbs();
	}
}
