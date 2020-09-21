package cl.sepidesign.herbs.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.model.Herb;

public class FavoritesAdapter extends ArrayAdapter<Herb>
{
	private Context ctx;
	private Typeface tf;

	public FavoritesAdapter(Context context, ArrayList<Herb> items)
	{
		super(context, 0, items);

		ctx = context;
		tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Light.ttf");

		setNotifyOnChange(true);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_favorite, null);
		}

		Herb herb = getItem(position);

		if (herb != null)
		{
			ImageView img = (ImageView) convertView.findViewById(R.id.item_favorite_img);
			TextView text = (TextView) convertView.findViewById(R.id.item_favorite_text);
			text.setTypeface(tf);

			switch (position)
			{
				case 0:
				case 3:
					if (herb.getIsDummy())
					{
						img.setImageResource(R.drawable.tabla_izq);
						text.setVisibility(View.GONE);
					}
					else
					{
						img.setImageResource(herb.getLeftFavDrawableId());
						text.setText(herb.getName());
					}
					break;

				case 2:
				case 5:
					if (herb.getIsDummy())
					{
						img.setImageResource(R.drawable.tabla_der);
						text.setVisibility(View.GONE);
					}
					else
					{
						img.setImageResource(herb.getRightFavDrawableId());
						text.setText(herb.getName());
					}
					break;

				default:
					if (herb.getIsDummy())
					{
						img.setImageResource(R.drawable.tabla_cent);
						text.setVisibility(View.GONE);
					}
					else
					{
						img.setImageResource(herb.getFavDrawableId());
						text.setText(herb.getName());
					}
					break;
			}
		}

		return convertView;
	}

	@Override
	public long getItemId(int position)
	{
		return getItem(position).getId();
	}
}