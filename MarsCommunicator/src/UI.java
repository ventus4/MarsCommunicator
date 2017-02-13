import java.io.File;
import java.util.List;

public abstract class UI implements MessageListener {
	List<Message> messages;
	Outpost outpost;
	
	public void displayNew(Message message){
		if (!outpost.name.equals(message.senderName)) {
			System.out.println(message.senderName + " --> " + message.receiverName + ": " + message.text);
			File[] files = {message.image, message.audio};
			for (File f : files) {
				if (f != null) {
					System.out.println("Image named " + f.getName() + " saved at " + f.getAbsolutePath());
				}
			}
		}
	}
	
	@Override
	public void onMessage(Message message) {
		messages.add(message);
		displayNew(message);
	}
}
