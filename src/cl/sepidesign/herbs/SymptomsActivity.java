package cl.sepidesign.herbs;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.adapter.SearchAdapter;
import cl.sepidesign.herbs.adapter.SimptomsAdapter;
import cl.sepidesign.herbs.manager.DataManager;
import cl.sepidesign.herbs.model.Herb;

public class SymptomsActivity extends Activity implements OnItemClickListener
{
	private Boolean isSimptomsVisible = true;

	private ListView simptomsList;

	private SimptomsAdapter simptomsAdapter;
	private SearchAdapter searchAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_symptoms);

		loadSymptoms();
	}

	private void loadSymptoms()
	{
		simptomsList = (ListView) findViewById(R.id.simptoms_list);

		if (simptomsAdapter == null)
			simptomsAdapter = new SimptomsAdapter(this, DataManager.getSimptoms());

		simptomsList.setAdapter(simptomsAdapter);
		simptomsList.setOnItemClickListener(this);
	}

	private void loadHerbsBySymptom(String symptom)
	{
		searchAdapter = new SearchAdapter(this, R.layout.item_list_herb_search, DataManager.getHerbsBySymptom(symptom));
		simptomsList.setAdapter(searchAdapter);
		simptomsList.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id)
	{
		if (isSimptomsVisible)
		{
			String symptom = simptomsAdapter.getItem(pos);
			ArrayList<Herb> resultList = DataManager.getHerbsBySymptom(symptom);

			if (resultList.size() > 1)
			{
				loadHerbsBySymptom(symptom);
				isSimptomsVisible = false;
			}
			else
			{
				Intent intent = new Intent(this, HerbDetailActivity.class);
				intent.putExtra("id", resultList.get(0).getId());
				startActivity(intent);
			}
		}

		else
		{
			Intent intent = new Intent(this, HerbDetailActivity.class);
			intent.putExtra("id", (int) id);
			startActivity(intent);
		}

	}

	@Override
	public void onBackPressed()
	{
		if (!isSimptomsVisible)
		{
			loadSymptoms();
			isSimptomsVisible = true;
		}

		else
			super.onBackPressed();
	}
}