
public class ProblemSimulator {
	ConfigFile configFile;
	long delayMilliseconds;
	double noisePercent;
	double dropPercent;
	
	public ProblemSimulator(String configFilePath) {
		configFile = new ConfigFile(configFilePath);
		delayMilliseconds = (long) configFile.getFromMap("delayMilliseconds");
		noisePercent = configFile.getFromMap("noisePercent");
		dropPercent = configFile.getFromMap("dropPercent");
	}
	
	public Message createProblems(Message message) {
		Message problemMessage = new Message(message);//copy of the message so we don't change the original
		//TODO create the problems!
		return problemMessage;
	}
}
