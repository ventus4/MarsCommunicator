import java.io.File;

public class Message {
	public static final String LOCATION = "C:\\Users\\admin\\workspace\\MarsCommunicator\\";
	String senderName;
	String receiverName;
	String text;
	File image;
	File audio;
	
	public Message(String sender, String receiver, String txt, File img, File audioFile) {
		senderName = sender;
		receiverName = receiver;
		text = txt;
		image = img;
		audio = audioFile;
	}
	
	public Message(Message m) {
		senderName = m.senderName;
		receiverName = m.receiverName;
		text = m.text;
		image = m.image;
		audio = m.audio;
	}

}
