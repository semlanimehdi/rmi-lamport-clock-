import java.rmi.registry.*;
import java.rmi.server.*;

public class Server
{
	public static void main(String args[])
	{
		ServicesImplementation servicesImplementation = new ServicesImplementation();
		System.out.println("Initializing Server's Logical clock Value: " + servicesImplementation.getlamportClock());
		String ip = "127.0.0.1";
		System.setProperty("java.rmi.server.hostname", ip);
		
		Registry registry = null;
		
		try
		{
			int port = 10908;
			registry = LocateRegistry.createRegistry(port);
			servicesImplementation.Event("Create registry");
		}
		catch (Exception e)
		{
			System.out.println("Error: "+e);
		}
		
		try
		{
			ServicesInterface skeleton = (ServicesInterface)UnicastRemoteObject.exportObject(servicesImplementation, 0);
			
			registry.rebind("Services", skeleton);
			servicesImplementation.Event("Rebind object");
			
			System.out.println("Server is online.");
		}
		catch (Exception e)
		{
			System.out.println("Error: "+e);
		}
		
		return;
	}
}
