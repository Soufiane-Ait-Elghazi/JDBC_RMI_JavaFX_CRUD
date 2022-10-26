package dao;
import java.util.List;
import metier.*;

public interface IEtudiantDao {
	public Etudiant save(Etudiant e);
	public List<Etudiant> EtudiantParMC(String mc);
	public Etudiant getEtudiant(int id);
	public Etudiant Update(int id, Etudiant e);
	public void deleteEtudiant(Long id);
}