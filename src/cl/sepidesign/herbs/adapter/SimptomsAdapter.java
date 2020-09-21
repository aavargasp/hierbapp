package cl.sepidesign.herbs.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import cl.sepidesign.herbs.R;

public class SimptomsAdapter extends ArrayAdapter<String> implements SectionIndexer
{
	private Context ctx;
	private Typeface tf;
	private HashMap<String, Integer> alphaIndexer;
	private String[] sections = new String[0];

	public SimptomsAdapter(Context context, ArrayList<String> items)
	{
		super(context, 0, items);
		ctx = context;
		tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Light.ttf");
		setNotifyOnChange(true);

		alphaIndexer = new HashMap<String, Integer>();

		for (int i = 0; i < items.size(); i++)
		{
			String s = items.get(i).substring(0, 1).toUpperCase();
			if (!alphaIndexer.containsKey(s))
				alphaIndexer.put(s, i);
		}

		Set<String> keys = alphaIndexer.keySet();
		Iterator<String> it = keys.iterator();
		ArrayList<String> keyList = new ArrayList<String>();
		while (it.hasNext())
			keyList.add(it.next());

		Collections.sort(keyList);
		sections = new String[keyList.size()];
		keyList.toArray(sections);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TextView view;

		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_list_simptom, null);
		}

		String string = getItem(position);

		if (string != null)
		{
			view = ((TextView) convertView);
			view.setText(string);
			view.setTypeface(tf);
		}

		return convertView;
	}

	@Override
	public int getPositionForSection(int section)
	{
		String letter = sections[section];
		return alphaIndexer.get(letter);
	}

	@Override
	public int getSectionForPosition(int position)
	{
		int prevIndex = 0;
		for (int i = 0; i < sections.length; i++)
		{
			if (getPositionForSection(i) > position && prevIndex <= position)
			{
				prevIndex = i;
				break;
			}
			prevIndex = i;
		}
		return prevIndex;
	}

	@Override
	public Object[] getSections()
	{
		return sections;
	}
}