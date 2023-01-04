import java.rmi.registry.*;
import java.util.*;

public class Client
{
	static int lamportClock = 0;
	static int num1 = 22;
	static int num2 = 44;
	
	static int i =0;
	
	public static void main(String args[])
	{
		try
		
		{	System.out.println("Enter Num 1:");
			Scanner sc = new Scanner(System.in);
			String num_in1 = sc.nextLine();
			int num1 = Integer.parseInt(num_in1);
			System.out.println("Enter Num 2:");
			String num_in2 = sc.nextLine();
			int num2 = Integer.parseInt(num_in2);
			System.out.println("Intializing Client's Logical clock value: " + lamportClock);
			
			String ip = "127.0.0.1";
			int port = 10908;
			Registry registry = LocateRegistry.getRegistry(ip, port);
			Event("Get registry");
			
			ServicesInterface stub = (ServicesInterface)registry.lookup("Services");
			Event("Stub creation");
			
			Number[] getResult = new Number[2];
			Number[] numsAndClock = new Number[3];
			numsAndClock[0]=num1;
			numsAndClock[1]=num2;
			numsAndClock[2]=lamportClock;
			
			while(true)
			{
				if(i!=0){
					System.out.println("Enter Num 1:");
					num_in1 = sc.nextLine();
					num1 = Integer.parseInt(num_in1);
					System.out.println("Enter Num 2:");
					num_in2 = sc.nextLine();
					num2 = Integer.parseInt(num_in2);
				}
				System.out.println("Press 1 for Addition and 2 for Subtraction");
				String choice = sc.nextLine();
				
				Event("Choose operation");
				int service = Integer.parseInt(choice);
				SendNumbers();
				numsAndClock[0]=num1;
				numsAndClock[1]=num2;
				numsAndClock[2]=lamportClock;
				
				switch (service)
				{
					case 1:
						getResult = stub.Addition(numsAndClock);
						i = i+1;
						break;
					case 2:
						getResult = stub.Subtraction(numsAndClock);
						i = i+1;
						break;
				}
				
				GotResult(getResult);
				System.out.println("Answer is : "+getResult[0]);
			}
		}
		catch (Exception e)
		{
			System.out.println("Err: " + e);
		}
	}
	private static void Event(String event)
	{
		lamportClock = lamportClock+1;
		System.out.println("Event: \""+event+"\". Lamport clock Value: "+lamportClock);
	}
	private static void SendNumbers()
	{
		Event("Send Values");
		System.out.println("Sending Numbers to server. Lamport clock value: "+lamportClock);
	}
	private static void GotResult(Number[] fullResult)
	{
		int senderlamportClock = (int)fullResult[1];
		Event("Get Result");
		int maxL = 0;
		if (lamportClock > senderlamportClock)
			maxL =  lamportClock;
		else
			maxL = senderlamportClock;
		lamportClock = maxL + 1;
		System.out.println("\t\tServer sent the result. Updating clock value to: "+lamportClock);
	}
}
