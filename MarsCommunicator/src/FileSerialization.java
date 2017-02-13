import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;


public class FileSerialization implements JsonSerializer<File>, JsonDeserializer<File> {
	File directory;
	public FileSerialization(String pathToDirectory) {
		File dir = new File(pathToDirectory);
		directory = dir.exists() && dir.isDirectory() ? dir : null;
	}
	@Override
	public File deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		File file = null;
		byte[] arr = arg0.getAsString().getBytes();
		int index = -1;
		for (int i = 0; i < arr.length; i++)
			if (arr[i] == (byte)':')
				index = i;
		if (index != -1) {
			byte[] nameBytes = Arrays.copyOfRange(arr, 0, index);
			String name = new String(nameBytes);
			arr = Base64.decodeBase64(Arrays.copyOfRange(arr, index, arr.length));
			try {
				file = new File(directory, name);
				FileUtils.writeByteArrayToFile(file, arr);
			} catch (IOException e) {
				e.printStackTrace();
				file = null;
			}
		}
		return file;
	}
	@Override
	public JsonElement serialize(File arg0, Type arg1, JsonSerializationContext arg2) {
		String str = arg0.getName() + ":";
		JsonPrimitive prim = null;
		try {
			prim = new JsonPrimitive(str + Base64.encodeBase64String(FileUtils.readFileToByteArray(arg0)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prim;
	}


}
