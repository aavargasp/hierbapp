package cl.sepidesign.herbs.utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;

public class FileUtils
{
	private Context ctx;
	
	public FileUtils(Context ctx)
	{
		this.ctx = ctx;
	}
	
	public InputStream readInputStreamFromMemory(String fileName)
	{
		InputStream is = null;

		try
		{
			is = new BufferedInputStream(ctx.openFileInput(fileName));
		}

		catch (Exception e)
		{
			Log.e("DataManager", "Error reading file: " + fileName + " error: " + e.getMessage());
		}

		return is;
	}

	public void saveInMemory(String fileName, byte[] fileData)
	{
		FileOutputStream fos;

		try
		{
			fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(fileData);
			fos.flush();
			fos.close();
		}

		catch (Exception e)
		{
			Log.e("DataManager", "Error saving file: " + fileName + " error: " + e.getMessage());
		}
	}
}
