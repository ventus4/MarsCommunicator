import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class UIEarth extends UI {
	
	public UIEarth(String host) throws Exception {
		messages = new ArrayList<>();
		outpost = new Outpost(host, this);
	}
	
	public void sendMessage(Message msg) {
		outpost.write(null, msg);
	}

	public static void main(String[] args) {
		System.out.println("Enter mars hostname:");
		Scanner scan = new Scanner(System.in);
		String s = scan.nextLine();
		try {
			UIEarth ui = new UIEarth("admin-PC");
			while (true) {
				s = scan.nextLine();
				File image = null;
				if (s.contains("file")) {
					image = new File(Message.LOCATION + "mars-comm-uml.png");
				}
				Message msg = new Message("", "", s, image, null);
				ui.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		scan.close();	
	}
}
