package cl.sepidesign.herbs;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.adapter.SearchAdapter;
import cl.sepidesign.herbs.manager.DataManager;

public class MainActivity extends TabActivity implements OnTabChangeListener, TextWatcher, OnItemClickListener
{
	private TabHost mTabHost;

	private View titleBar;
	private EditText searchText;

	private Boolean isSearchVisible = false;
	private ListView herbsList;
	private SearchAdapter adapter;

	private Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		searchText = (EditText) findViewById(R.id.herbs_search);
		searchText.setTypeface(tf);
		searchText.addTextChangedListener(this);

		setupTabs();

		adapter = new SearchAdapter(this, R.layout.item_list_herb_search, DataManager.getHerbs());

		herbsList = (ListView) findViewById(R.id.herbs_list);
		herbsList.setAdapter(adapter);
		herbsList.setTextFilterEnabled(true);
		herbsList.setOnItemClickListener(this);
	}

	private void setupTabs()
	{
		mTabHost = getTabHost();
		mTabHost.setOnTabChangedListener(this);

		setupTab(new Intent(this, MethodsActivity.class), "m", tf, R.layout.item_tab_methods);
		setupTab(new Intent(this, SymptomsActivity.class), "s", tf, R.layout.item_tab_symptoms);
		setupTab(new Intent(this, HerbsActivity.class), "h", tf, R.layout.item_tab_herbs);
		setupTab(new Intent(this, FavoritesActivity.class), "f", tf, R.layout.item_tab_favorites);
		setupTab(new Intent(this, InfoActivity.class), "i", tf, R.layout.item_tab_info);

		mTabHost.setCurrentTab(2);
	}

	private void setupTab(Intent intent, final String tag, final Typeface tf, final int layoutId)
	{
		View tabview = createTabView(mTabHost.getContext(), tag, tf, layoutId);
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(intent);
		mTabHost.addTab(setContent);
	}

	private static View createTabView(final Context context, final String tag, final Typeface tf, final int layoutId)
	{
		View tab = LayoutInflater.from(context).inflate(layoutId, null);
		((TextView) tab.findViewById(R.id.tab_text)).setTypeface(tf);

		return tab;
	}

	@Override
	public void onTabChanged(String tabId)
	{
		if (isSearchVisible)
		{
			herbsList.setVisibility(View.GONE);
			findViewById(android.R.id.tabcontent).setVisibility(View.VISIBLE);
			isSearchVisible = false;
		}

		titleBar = findViewById(R.id.titlebar);

		int color = 0;

		if (tabId.equals("m"))
			color = R.color.morado;
		else if (tabId.equals("s"))
			color = R.color.amarillo;
		else if (tabId.equals("h"))
			color = R.color.verde;
		else if (tabId.equals("f"))
			color = R.color.rojo;
		else if (tabId.equals("i"))
			color = R.color.celeste;
		else
			color = android.R.color.darker_gray;

		titleBar.setBackgroundResource(color);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_SEARCH)
		{
			searchText.requestFocus();
			searchText.requestFocusFromTouch();
			return true;
		}

		else
			return super.onKeyDown(keyCode, event);
	}

	@Override
	public void afterTextChanged(Editable s)
	{
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		if (before == 0 && count == 1)
		{
			findViewById(android.R.id.tabcontent).setVisibility(View.GONE);
			herbsList.setVisibility(View.VISIBLE);
			isSearchVisible = true;
		}

		else if (before == 1 && count == 0)
		{
			herbsList.setVisibility(View.GONE);
			findViewById(android.R.id.tabcontent).setVisibility(View.VISIBLE);
			isSearchVisible = false;
		}

		adapter.getFilter().filter(s);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent = new Intent(this, HerbDetailActivity.class);
		intent.putExtra("id", (int) id);
		startActivity(intent);
	}
}
