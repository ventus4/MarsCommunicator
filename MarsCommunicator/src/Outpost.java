import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.net.Socket;

public class Outpost implements IWrite {
	public final static int PORT_NUMBER = 5463;
	String name;
	String ipAddress;
	boolean isMars;
	IMainThread mainThread;
	ProblemSimulator pSim;
	
	public Outpost(String marsHostName, MessageListener ml) throws Exception {
		InetAddress address = InetAddress.getLocalHost();
		ipAddress = address.getHostAddress();
		isMars = marsHostName == null;
		pSim = new ProblemSimulator(null);
		if (isMars) {
			name = "Mars";
			mainThread = new MarsMainThread(PORT_NUMBER, ml);
			mainThread.start();
		}
		else {
			name = address.getHostName();
			InetAddress mars = InetAddress.getByName(marsHostName);
			try {
				Socket socket = new Socket(mars.getHostAddress(), PORT_NUMBER);
				mainThread = new ListenerThread(socket);
				mainThread.start();
				if (mainThread != null && ml != null) {
					mainThread.addThrowListener(ml);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void write(String name1, Message msg) {
		if (name1 == null) {
			msg.senderName = this.name;
			msg.receiverName = "Mars";
		}
		else {
			msg.senderName = "Mars";
			msg.receiverName = name1;
		}
		mainThread.write(name1, pSim.createProblems(msg));
	}
	
	public static void main(String[] args) {
		try {
			new Outpost(null, null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try { 
			InetAddress address = InetAddress.getLocalHost();
			System.out.println(address.getHostName());
			byte[] arr = address.getAddress();
			int[] a1 = new int[arr.length];
			for (int i = 0; i < arr.length; i++)
				if (((int)arr[i]) < 0) a1[i] = 256 + ((int)arr[i]);
				else a1[i] = (int) arr[i];
			System.out.println(Arrays.toString(a1));
			for (int i = 1; i < 10; i++)
				System.out.println(InetAddress.getByName("TURINGLAB0" + i));
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}