
public class ServicesImplementation implements ServicesInterface
{
	int lamportClock = 0;
	
	public int getlamportClock()
	{
	    return this.lamportClock;
	}
	public Number[] Addition(Number[] getNumbers)
	{
		ReceiveNumbers(getNumbers);
		int result = (int)getNumbers[0] + (int)getNumbers[1];
		SendResult();
		Number[] resultToClient = new Number[3];
		resultToClient[0]=result;
		resultToClient[1]=this.lamportClock;
		return resultToClient;
	}
	public Number[] Subtraction(Number[] getNumbers)
	{
		ReceiveNumbers(getNumbers);
		int result = (int)getNumbers[0] - (int)getNumbers[1];
		SendResult();
		Number[] resultToClient = new Number[3];
		resultToClient[0]=result;
		resultToClient[1]=this.lamportClock;
		return resultToClient;
	}
	public void Event(String event)
	{
		this.lamportClock = this.lamportClock + 1;
		System.out.println("Event: \""+event+"\". Lamport clock Value: "+lamportClock);
	}
	private void ReceiveNumbers(Number[] incomingNumbers)
	{
		int senderlamportClock = (int)incomingNumbers[2];
		Event("Receive message");
		int maxL = 0;
		if (this.lamportClock > senderlamportClock)
			maxL =  this.lamportClock;
		else
			maxL = senderlamportClock;
		this.lamportClock = maxL + 1;
		System.out.println("\t\tClient sent Numbers. Updating clock value to: "+lamportClock);
	}
	private void SendResult()
	{
		Event("Send message");
		System.out.println("Sending result to client. Lamport clock value: "+this.lamportClock);
	}
}
