package cl.sepidesign.herbs.model;

import java.lang.reflect.Field;

import cl.sepidesign.herbs.R;

public class Method
{
	private int id;
	private String name;

	public Method(int id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public int getIconId()
	{
		Class<R.drawable> res = R.drawable.class;
		Field field;

		int drawableId = -1;

		try
		{
			field = res.getField("method" + this.id);
			drawableId = field.getInt(null);

		}
		catch (Exception e)
		{
		}

		return drawableId;
	}
	
	public int getImgId()
	{
		Class<R.drawable> res = R.drawable.class;
		Field field;

		int drawableId = -1;

		try
		{
			field = res.getField("info_method" + this.id);
			drawableId = field.getInt(null);

		}
		catch (Exception e)
		{
		}

		return drawableId;
	}

	public int getDescId()
	{
			Class<R.string> res = R.string.class;
			Field field;

			int stringId = -1;

			try
			{
				field = res.getField("method" + this.id);
				stringId = field.getInt(null);

			}
			catch (Exception e)
			{
			}

			return stringId;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
