import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


public class ConfigFile {
	File file;
	Map<String, Double> map;
	public ConfigFile(String pathname) {
		if (pathname != null)
			file = new File(pathname);
		else
			file = null;
		map = readMap(file);
	}
	
	public double getFromMap(String key) {
		if (map != null && map.containsKey(key)) {
			Double value = map.get(key);
			if (value != null)
				return value;
		}
		return 0.0;
	}
	
	private Map<String, Double> readMap(File file) {
		Map<String, Double> myMap = null;
		if (file != null) {
			try {
				String json = new String(Files.readAllBytes(file.toPath()));
				Gson gson = new Gson();
				Type type = new TypeToken<Map<String, Double>>(){}.getType();
				myMap = gson.fromJson(json, type);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return myMap;
	}
	
	public boolean writeConfigFile() {
		try(PrintWriter out = new PrintWriter(file)){
			Gson gson = new Gson();
		    out.println(gson.toJson(map));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
