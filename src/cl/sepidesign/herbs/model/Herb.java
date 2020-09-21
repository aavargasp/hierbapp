package cl.sepidesign.herbs.model;

import java.lang.reflect.Field;
import java.util.ArrayList;

import cl.sepidesign.herbs.R;

import com.google.gson.annotations.SerializedName;

public class Herb
{
	private int id;
	private String name;
	@SerializedName("short_name")
	private String shortName;
	private String wiki;
	@SerializedName("scientific_name")
	private String scientificName;
	@SerializedName("other_names")
	private ArrayList<String> otherNames;
	@SerializedName("important_parts")
	private String importantParts;
	private String habitat;
	private String characteristics;
	private ArrayList<String> symptoms;
	private String preparation;
	private String precautions;

	private String annotations;
	
	private Boolean isDummy;

	public Herb()
	{
		isDummy = false;
	}
	
	public Herb(Boolean dummy)
	{
		isDummy = dummy;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}
	
	public String getShortName()
	{
		return shortName;
	}
	
	public String getWiki()
	{
		return wiki;
	}

	public String getScientificName()
	{
		return scientificName;
	}

	public ArrayList<String> getOtherNames()
	{
		return otherNames;
	}
	
	public String getOtherNamesAsString()
	{
		String names = "";
		
		for (int i = 0; i < otherNames.size(); i++)
		{
			names += otherNames.get(i);
			
			if (i < otherNames.size() - 1)
				names += ", ";
		}
		
		return names;
	}

	public String getImportantParts()
	{
		return importantParts;
	}

	public String getHabitat()
	{
		return habitat;
	}

	public String getCharacteristics()
	{
		return characteristics;
	}

	public ArrayList<String> getSymptoms()
	{
		return symptoms;
	}
	
	public String getSymptomsAsString()
	{
		String result = "";
		String symptom = "";
		
		for (int i = 0; i < symptoms.size(); i++)
		{
			symptom = symptoms.get(i);
			
			if (i > 0)
				symptom = Character.toString(symptom.charAt(0)).toLowerCase() + symptom.substring(1);
			
			if (i < symptoms.size() - 1)
				symptom += ", ";
			
			result += symptom;
		}
		
		return result;
	}

	public String getPreparation()
	{
		return preparation;
	}

	public String getPrecautions()
	{
		return precautions;
	}

	public String getAnnotations()
	{
		if (annotations != null && annotations.length() > 0)
			annotations = annotations.trim();
		else
			annotations = null;
		
		return annotations;
	}

	public void setAnnotations(String annotations)
	{
		this.annotations = annotations.trim();
	}

	public int getDrawableId()
	{
		Class<R.drawable> res = R.drawable.class;
		Field field;

		int drawableId = -1;

		try
		{
			field = res.getField("herb" + this.id);
			drawableId = field.getInt(null);

		}
		catch (Exception e)
		{
		}

		return drawableId;
	}

	public int getFavDrawableId()
	{
		Class<R.drawable> res = R.drawable.class;
		Field field;

		int drawableId = -1;

		try
		{
			field = res.getField(this.shortName + "_cent");
			drawableId = field.getInt(null);

		}
		catch (Exception e)
		{
		}

		return drawableId;
	}
	
	public int getLeftFavDrawableId()
	{
		Class<R.drawable> res = R.drawable.class;
		Field field;

		int drawableId = -1;

		try
		{
			field = res.getField(this.shortName + "_izq");
			drawableId = field.getInt(null);

		}
		catch (Exception e)
		{
		}

		return drawableId;
	}
	
	public int getRightFavDrawableId()
	{
		Class<R.drawable> res = R.drawable.class;
		Field field;

		int drawableId = -1;

		try
		{
			field = res.getField(this.shortName + "_der");
			drawableId = field.getInt(null);

		}
		catch (Exception e)
		{
		}

		return drawableId;
	}

	public int[] getDetailsDrawablesId()
	{
		int[] drawables = new int[3];
		
		Class<R.drawable> res = R.drawable.class;
		Field field;

		int drawableId = -1;

		for (int i = 1; i <= 3; i++)
		{
			try
			{
				field = res.getField("herb" + this.id + "_" + i);
				drawableId = field.getInt(null);
				drawables[i - 1] = drawableId;
			}
			catch (Exception e)
			{
			}
		}
		

		return drawables;
	}
	
	public Boolean getIsDummy()
	{
		return isDummy;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
	
	public String toShare()
	{
		return "Te recomiendo " + this.name + ", planta medicinal con grandes beneficios. Para conocer m‡s descarga Hierbapp gratis #Hierbapp";	
	}
}
