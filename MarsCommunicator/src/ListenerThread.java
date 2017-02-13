import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ListenerThread extends IMainThread {
	Socket socket;
	
	public ListenerThread(Socket s) {
		socket = s;
	}

	@Override
	public void run() {
		String inputLine;
		StringBuilder jsonBuilder = new StringBuilder();
		try {
			System.out.println("listening");
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				jsonBuilder.append(inputLine);
				if (jsonBuilder.charAt(jsonBuilder.length() - 1) == '}') {
					Gson gson = new GsonBuilder().registerTypeAdapter(File.class, new FileSerialization(Message.LOCATION + "src")).create();
					Message message = gson.fromJson(jsonBuilder.toString(), Message.class);
					onMessage(message);
					jsonBuilder.setLength(0);
				}
			}
		} 
		catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	@Override
	public void write(String name, Message msg) {
		if (!socket.isOutputShutdown()) {
			onMessage(msg);
			Gson gson = new GsonBuilder().registerTypeAdapter(File.class, new FileSerialization(Message.LOCATION + "src")).create();
			try {
				//TODO output message, deal with files, maybe base64?
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				out.write(gson.toJson(msg));
				out.newLine();
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
