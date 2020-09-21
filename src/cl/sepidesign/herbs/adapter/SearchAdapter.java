package cl.sepidesign.herbs.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.model.Herb;
import cl.sepidesign.herbs.utils.StringUtils;

public class SearchAdapter extends ArrayAdapter<Herb>
{
	public ArrayList<Herb> filtered;
	public ArrayList<Herb> original;
	private Context context;
	private Typeface tf;
	private Filter filter;
	private LayoutInflater inflater;

	private class ViewHolder
	{
		TextView textView;
	}

	@SuppressWarnings("unchecked")
	public SearchAdapter(Context context, int textViewResourceId, ArrayList<Herb> items)
	{
		super(context, textViewResourceId, (ArrayList<Herb>) items.clone());
		this.original = (ArrayList<Herb>) items.clone();
		this.context = context;
		tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
		this.filter = new HerbFilter();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;

		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_list_herb_search, null);
			holder.textView = (TextView) convertView;
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();

		Herb h = getItem(position);

		if (h != null)
		{
			holder.textView.setTypeface(tf);
			holder.textView.setText(h.getName());
			holder.textView.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(h.getDrawableId()), null, null, null);
		}

		return convertView;
	}

	@Override
	public Herb getItem(int position)
	{
		return super.getItem(position);
	}
	
	@Override
	public long getItemId(int position)
	{
		return getItem(position).getId();
	}

	@Override
	public Filter getFilter()
	{
		if (filter == null)
			filter = new HerbFilter();
		return filter;
	}

	private class HerbFilter extends Filter
	{
		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			FilterResults results = new FilterResults();
			List<Herb> filtered = new ArrayList<Herb>();

			if (constraint == null || constraint.length() == 0)
			{
				// set the Original result to return
				results.count = original.size();
				results.values = original;
			}
			else
			{
				constraint = constraint.toString().toLowerCase();
				constraint = StringUtils.removeDiacriticalMarks(constraint.toString());
				
				for (Herb h : original)
				{
					String herbName = h.getName();
					herbName = herbName.toLowerCase();
					herbName = StringUtils.removeDiacriticalMarks(herbName);
					
					if (filtered.contains(h))
						break;

					if (herbName.startsWith(constraint.toString()))
						filtered.add(h);

					else
					{
						for (String symptom : h.getSymptoms())
						{
							symptom = StringUtils.removeDiacriticalMarks(symptom);
							symptom = symptom.toLowerCase();
							
							if (symptom.contains(constraint.toString()))
							{
								filtered.add(h);
								break;
							}
						}
					}
				}
				// set the Filtered result to return
				results.count = filtered.size();
				results.values = filtered;
			}
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results)
		{
			clear();
			for (int i = 0; i < results.count; i++)
			{
				add(((ArrayList<Herb>) results.values).get(i));
			}

			notifyDataSetChanged();
		}
	}
}
