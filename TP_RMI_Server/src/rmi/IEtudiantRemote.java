package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;
import java.util.List;

import metier.Etudiant;

public interface IEtudiantRemote extends Remote {
	
	public Etudiant ajouterEtudiant(Etudiant e) throws RemoteException;
	public Etudiant getEtudiantById(int id) throws RemoteException ;
	public List<Etudiant> listAllEtudiants() throws RemoteException;
	public List<Etudiant> listEtudiantskeyWord(String kw) throws RemoteException;
	public Etudiant modifierEtudiant(int id , Etudiant e) throws RemoteException;
	public void supprimerEtudiant(int id) throws RemoteException;
	

}
