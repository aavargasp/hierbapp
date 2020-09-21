package cl.sepidesign.herbs.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.model.Method;

public class MethodsAdapter extends ArrayAdapter<Method>
{
	private Context ctx;
	private Typeface tf;

	public MethodsAdapter(Context context, ArrayList<Method> items)
	{
		super(context, 0, items);

		ctx = context;
		tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Light.ttf");

		setNotifyOnChange(true);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TextView view;

		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_herb, null);
		}

		Method method = getItem(position);

		if (method != null)
		{
			view = ((TextView) convertView);
			view.setText(method.getName());
			view.setCompoundDrawablesWithIntrinsicBounds(null, ctx.getResources().getDrawable(method.getIconId()), null, null);
			view.setTypeface(tf);
		}

		return convertView;
	}

	@Override
	public long getItemId(int position)
	{
		return getItem(position).getId();
	}
}