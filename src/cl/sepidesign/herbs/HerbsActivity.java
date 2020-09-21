package cl.sepidesign.herbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.adapter.HerbsAdapter;
import cl.sepidesign.herbs.manager.DataManager;

public class HerbsActivity extends Activity implements OnItemClickListener
{
	private GridView herbsGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_herbs);

		herbsGrid = (GridView) findViewById(R.id.herbs_grid);
		herbsGrid.setOnItemClickListener(this);

		HerbsAdapter adapter = new HerbsAdapter(this, DataManager.getHerbs());
		herbsGrid.setAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent = new Intent(this, HerbDetailActivity.class);
		intent.putExtra("id", (int) id);
		startActivity(intent);
	}

}
