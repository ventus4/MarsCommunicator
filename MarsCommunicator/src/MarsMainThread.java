import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MarsMainThread extends IMainThread {
	int port;
	boolean listening;
	Map<String, ListenerThread> listenerThreads;
	MessageListener messageListener;
	
	public MarsMainThread(int portNum, MessageListener ml) {
		port = portNum;
		listenerThreads = new HashMap<>();
		messageListener = ml;
	}
	
	@Override
	public void run() {
		listening = true;
		try (ServerSocket serverSocket = new ServerSocket(port)) { 
            while (listening) {
            	System.out.println("Waiting...");
            	Socket s = serverSocket.accept();
                ListenerThread lt = new ListenerThread(s);
                if (lt != null && messageListener != null) {
                	lt.addThrowListener(messageListener);
                }
                String name = s.getInetAddress().getCanonicalHostName();
                int x = name.indexOf(".");
                if (x > -1) 
                	name = name.substring(0, x).trim();
                listenerThreads.put(name, lt);
                lt.start();
                System.out.println("Started a connection with: " + name);
            }
        } catch (Exception e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }
	}
	
	@Override
	public void write(String name, Message msg) {
		if (listenerThreads.containsKey(name)) {
			ListenerThread thread = listenerThreads.get(name);
			if (thread.isAlive()) {
				listenerThreads.get(name).write(name, msg);
			}
			else {
				listenerThreads.remove(name);
			}
		}
	}
}
