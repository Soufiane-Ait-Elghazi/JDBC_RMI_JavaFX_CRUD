package rmi;

import java.rmi.RemoteException;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.EtudiantDaoImpl;
import metier.Etudiant;

public class EtudiantRmiService extends UnicastRemoteObject implements IEtudiantRemote{
	EtudiantDaoImpl etudiantDao = new EtudiantDaoImpl();
	public EtudiantRmiService() throws RemoteException {super();}

	@Override
	public Etudiant ajouterEtudiant(Etudiant e) throws RemoteException {
		return etudiantDao.save(e);
	}

	@Override
	public Etudiant getEtudiantById(int id) throws RemoteException {
		return etudiantDao.getEtudiant(id) ;
	}

	@Override
	public List<Etudiant> listAllEtudiants() throws RemoteException {
	
		return etudiantDao.Etudiants();
	}

	@Override
	public List<Etudiant> listEtudiantskeyWord(String kw) throws RemoteException {
		return etudiantDao.EtudiantParMC(kw);
	}

	@Override
	public Etudiant modifierEtudiant(int id, Etudiant e) throws RemoteException {
		return etudiantDao.Update(id, e);
	}

	@Override
	public void supprimerEtudiant(int id) throws RemoteException{
		etudiantDao.deleteEtudiant((long) id);
	}

	
	
	


}
