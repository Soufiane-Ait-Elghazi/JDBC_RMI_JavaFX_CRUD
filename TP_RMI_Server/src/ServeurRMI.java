import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import rmi.EtudiantRmiService;
import rmi.IEtudiantRemote;

public class ServeurRMI {
   public static void main(String[] args) {
	 try {
		 LocateRegistry.createRegistry(8888);
		 IEtudiantRemote od  = new EtudiantRmiService();
		 System.out.println(od.toString());
		 Naming.rebind("rmi://localhost:8888/SM", od);
		
	} catch (RemoteException | MalformedURLException e) {
		e.printStackTrace();
	} 
   }



}
