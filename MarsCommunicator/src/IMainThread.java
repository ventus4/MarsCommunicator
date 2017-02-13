import java.util.ArrayList;
import java.util.List;

public abstract class IMainThread extends Thread implements IWrite {
	//a list of catchers
    List<MessageListener> listeners = new ArrayList<MessageListener>();
    //a way to add someone to the list of catchers
    public void addThrowListener(MessageListener toAdd){
        listeners.add(toAdd);
    }

    public void onMessage(Message message) {
        //1 or more times, a Notification that an event happened is thrown.
        for (MessageListener ml : listeners) ml.onMessage(message);
    }
}
