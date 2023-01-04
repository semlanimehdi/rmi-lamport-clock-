import java.rmi.*;

public interface ServicesInterface extends Remote
{
	Number[] Addition(Number[] sendNumbers) throws RemoteException;
	Number[] Subtraction(Number[] sendNumbers) throws RemoteException;
}