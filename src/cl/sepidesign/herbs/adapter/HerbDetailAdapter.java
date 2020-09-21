package cl.sepidesign.herbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.model.Herb;

public class HerbDetailAdapter extends BaseExpandableListAdapter
{
	private Context ctx;
	private Typeface tf;
	private Herb herb;

	static class GroupViewHolder
	{
		TextView title;
		int position;
	}

	static class ChildViewHolder
	{
		TextView content;
		int position;
	}

	public HerbDetailAdapter(Context context, Herb herb)
	{
		ctx = context;
		tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Light.ttf");
		this.herb = herb;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		GroupViewHolder holder;

		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (groupPosition == 7)
		{
			convertView = inflater.inflate(R.layout.item_list_herb_wiki, null);
			convertView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					String url = herb.getWiki();
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					ctx.startActivity(i);
				}
			});
		}
		else
			convertView = inflater.inflate(R.layout.item_list_herb_group, null);

		holder = new GroupViewHolder();
		holder.title = (TextView) convertView;

		String groupName = (String) getGroup(groupPosition);

		if (groupName != null)
		{
			holder.title.setText(groupName);
			holder.title.setTypeface(tf);
		}
		return convertView;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		String group = null;

		switch (groupPosition)
		{
			case 0:
				group = "Otros nombres";
				break;
			case 1:
				group = "Partes útiles";
				break;
			case 2:
				group = "Hábitat / Cultivo";
				break;
			case 3:
				group = "Características principales";
				break;
			case 4:
				group = "Síntomas";
				break;
			case 5:
				group = "Preparación";
				break;
			case 6:
				group = "Precauciones";
				break;
			case 7:
				group = ctx.getResources().getString(R.string.wikipedia_btn);
				break;
			default:
				break;
		}

		return group;
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	@Override
	public int getGroupCount()
	{
		return 8;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		ChildViewHolder holder;

		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_list_herb_child, null);

			holder = new ChildViewHolder();
			holder.content = (TextView) convertView;

			convertView.setTag(holder);
		}

		else
			holder = (ChildViewHolder) convertView.getTag();

		if (herb != null)
		{
			String child = null;

			switch (groupPosition)
			{
				case 0:
					child = herb.getOtherNamesAsString();
					break;
				case 1:
					child = herb.getImportantParts();
					break;
				case 2:
					child = herb.getHabitat();
					break;
				case 3:
					child = herb.getCharacteristics();
					break;
				case 4:
					child = herb.getSymptomsAsString();
					break;
				case 5:
					child = herb.getPreparation();
					break;
				case 6:
					child = herb.getPrecautions();
					break;

				default:
					break;
			}

			holder.content.setText(child);
			holder.content.setTypeface(tf);
		}

		return convertView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		if (groupPosition == 7)
			return 0;

		return 1;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return false;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}
}