import java.util.ArrayList;
import java.util.Scanner;


public class UIMars extends UI {
	
	public UIMars() throws Exception {
		messages = new ArrayList<>();
		outpost = new Outpost(null, this);
	}
	
	public void sendMessage(String recipient, Message msg) {
		outpost.write(recipient, msg);
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		try {
			UIMars ui = new UIMars();
			System.out.println("Ready");
			String s;
			while (true) {
				s = scan.nextLine();
				int ind = s.indexOf(":");
				if (ind > 0) {
					String x = s.substring(0, ind);
					Message msg = new Message("", "", s.substring(ind + 1).trim(), null, null);
					ui.sendMessage(x, msg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		scan.close();
	}
}
