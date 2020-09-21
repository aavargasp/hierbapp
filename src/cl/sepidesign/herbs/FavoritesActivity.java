package cl.sepidesign.herbs;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cl.sepidesign.herbs.adapter.FavoritesAdapter;
import cl.sepidesign.herbs.manager.DataManager;
import cl.sepidesign.herbs.model.Herb;

public class FavoritesActivity extends Activity implements OnItemClickListener
{
	private GridView favGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);

		favGrid = (GridView) findViewById(R.id.favorites_herbs_list);
		favGrid.setOnItemClickListener(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void onResume()
	{
		ArrayList<Herb> favHerbs = (ArrayList<Herb>) DataManager.getFavoritesHerbs().clone();
		int times = 6 - favHerbs.size();

		for (int i = 0; i < times; i++)
		{
			favHerbs.add(new Herb(true));
		}

		FavoritesAdapter adapter = new FavoritesAdapter(this, favHerbs);
		favGrid.setAdapter(adapter);
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent = new Intent(this, HerbDetailActivity.class);
		intent.putExtra("id", (int) id);
		startActivity(intent);
	}

}
