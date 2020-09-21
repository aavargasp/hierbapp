package cl.sepidesign.herbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.adapter.MethodsAdapter;
import cl.sepidesign.herbs.manager.DataManager;

public class MethodsActivity extends Activity implements OnItemClickListener
{
	private GridView methodGrid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_methods);
		
		methodGrid = (GridView) findViewById(R.id.methods_grid);
		methodGrid.setOnItemClickListener(this);
		
		MethodsAdapter adapter = new MethodsAdapter(this, DataManager.getMethods());
		methodGrid.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent = new Intent(this, MethodDetailActivity.class);
		intent.putExtra("id", (int) id);
		startActivity(intent);
	}

}
