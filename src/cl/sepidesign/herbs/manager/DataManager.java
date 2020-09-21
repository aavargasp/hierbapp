package cl.sepidesign.herbs.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.util.Log;
import cl.sepidesign.herbs.R;
import cl.sepidesign.herbs.model.Herb;
import cl.sepidesign.herbs.model.Method;
import cl.sepidesign.herbs.utils.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DataManager
{
	private static final String TAG = "DataManager";
	private static final String HERBS_FILENAME = "herbs.json";
	private static final String FAV_HERBS_FILENAME = "favorites_herbs.json";

	private static Context ctx;

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static FileUtils fileUtils;

	private static ArrayList<Method> methodsList;
	private static ArrayList<Herb> herbsList;
	private static ArrayList<Herb> favoritesHerbsList;
	private static ArrayList<String> simptomsList;

	private static int lastHerbId = -1;
	private static Herb lastHerb;

	private static String lastSymptom = null;
	private static ArrayList<Herb> lastHerbsList;

	private static Type herbsCollectionType = new TypeToken<ArrayList<Herb>>()
	{
	}.getType();

	public static enum FavoriteResult
	{
		ADDED, EXIST, FULL, NOTADDED;
	}

	public DataManager()
	{
	}

	public static void setContext(Context context)
	{
		ctx = context;
		fileUtils = new FileUtils(ctx);
	}

	private static void loadMethods()
	{
		methodsList = new ArrayList<Method>(4);
		methodsList.add(new Method(1, "Cataplasma"));
		methodsList.add(new Method(2, "Decocción"));
		methodsList.add(new Method(3, "Infusión"));
		methodsList.add(new Method(4, "Maceración"));
	}

	public static ArrayList<Method> getMethods()
	{
		if (methodsList == null)
			loadMethods();

		return methodsList;
	}

	public static Method getMethodById(int id)
	{
		if (methodsList != null)
		{
			for (Method method : methodsList)
			{
				if (method.getId() == id)
					return method;
			}
		}

		return null;
	}

	public static void loadHerbs()
	{
		InputStream is = fileUtils.readInputStreamFromMemory(HERBS_FILENAME);
		InputStreamReader isr = null;

		if (is != null)
			isr = new InputStreamReader(is);

		else
		{
			is = ctx.getResources().openRawResource(R.raw.herbs);

			if (is != null)
				isr = new InputStreamReader(is);
		}

		if (isr != null)
			herbsList = gson.fromJson(isr, herbsCollectionType);

		try
		{
			is.close();
			isr.close();
		}
		catch (IOException e)
		{
			Log.e(TAG, "Error closing streams", e);
		}
	}

	public static ArrayList<Herb> getHerbs()
	{
		if (herbsList == null || herbsList.size() < 1)
			loadHerbs();

		return herbsList;
	}

	public static Herb getLastHerb()
	{
		return lastHerb;
	}

	public static Herb getHerbById(int id)
	{
		if (lastHerbId != -1 && lastHerbId == id)
			return getLastHerb();

		else
		{
			if (herbsList != null)
			{
				for (Herb herb : herbsList)
				{
					if (herb.getId() == id)
					{
						lastHerb = herb;
						return herb;
					}
				}
			}
		}

		return null;
	}

	private static ArrayList<Herb> getLastHerbsList()
	{
		return lastHerbsList;
	}

	public static ArrayList<Herb> getHerbsBySymptom(String symptom)
	{
		if (lastSymptom != null && lastSymptom.equals(symptom))
			return getLastHerbsList();

		lastHerbsList = new ArrayList<Herb>();
		lastSymptom = symptom;

		for (Herb herb : herbsList)
		{
			if (lastHerbsList.contains(herb))
				break;

			for (String symp : herb.getSymptoms())
			{
				if (symp.equals(symptom))
				{
					lastHerbsList.add(herb);
					break;
				}
			}
		}

		return lastHerbsList;
	}

	public static void saveHerbs()
	{
		if (herbsList != null)
			fileUtils.saveInMemory(HERBS_FILENAME, gson.toJson(herbsList).getBytes());
	}

	public static void loadFavoritesHerbs()
	{
		InputStreamReader isr = null;
		InputStream is = fileUtils.readInputStreamFromMemory(FAV_HERBS_FILENAME);

		if (is != null)
		{
			isr = new InputStreamReader(is);

			if (isr != null)
			{
				favoritesHerbsList = gson.fromJson(isr, herbsCollectionType);

				try
				{
					is.close();
					isr.close();
				}
				catch (Exception e)
				{
					Log.e(TAG, "Error closing streams", e);
				}
			}
		}

		if (favoritesHerbsList == null)
			favoritesHerbsList = new ArrayList<Herb>(6);
	}

	public static ArrayList<Herb> getFavoritesHerbs()
	{
		if (favoritesHerbsList == null)
			loadFavoritesHerbs();

		return favoritesHerbsList;
	}

	public static Boolean isFavorite(Herb herb)
	{
		if (herb != null)
		{
			if (favoritesHerbsList != null)
			{
				if (favoritesHerbsList.contains(herb))
					return true;
				else
				{
					for (Herb h : favoritesHerbsList)
					{
						if (h != null && h.getId() == herb.getId())
							return true;
					}
				}
			}
		}

		return false;
	}

	public static FavoriteResult addFavoriteHerb(Herb herb)
	{
		if (favoritesHerbsList != null)
		{
			if (favoritesHerbsList.size() == 6)
				return FavoriteResult.FULL;

			else if (favoritesHerbsList.size() < 6 && favoritesHerbsList.contains(herb))
				return FavoriteResult.EXIST;

			else if (favoritesHerbsList.size() < 6 && !favoritesHerbsList.contains(herb))
			{
				favoritesHerbsList.add(herb);
				return FavoriteResult.ADDED;
			}
		}

		return FavoriteResult.NOTADDED;
	}

	public static void removeFavoriteHerb(int herbId)
	{
		Herb selectedHerb = null;

		if (favoritesHerbsList != null)
		{
			for (Herb herb : favoritesHerbsList)
			{
				if (herb.getId() == herbId)
					selectedHerb = herb;
			}

			if (selectedHerb != null)
				favoritesHerbsList.remove(selectedHerb);
		}
	}

	public static void saveFavoritesHerbs()
	{
		if (favoritesHerbsList != null)
			fileUtils.saveInMemory(FAV_HERBS_FILENAME, gson.toJson(favoritesHerbsList).getBytes());
	}

	public static void loadSimptoms()
	{
		if (simptomsList == null)
			simptomsList = new ArrayList<String>();

		if (herbsList != null)
		{
			for (Herb herb : herbsList)
			{
				for (String simptom : herb.getSymptoms())
				{
					if (!simptomsList.contains(simptom))
						simptomsList.add(simptom);
				}
			}
		}

		Collections.sort(simptomsList, new Comparator<String>()
		{
			@Override
			public int compare(String s1, String s2)
			{
				return s1.compareToIgnoreCase(s2);
			}
		});
	}

	public static ArrayList<String> getSimptoms()
	{
		if (simptomsList == null || simptomsList.size() < 1)
			loadSimptoms();

		return simptomsList;
	}
}
