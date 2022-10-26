package dao;

import java.util.List;
import metier.*;


public class test {
	public static void main(String[] args) {
		
		EtudiantDaoImpl Edao = new EtudiantDaoImpl();
		Etudiant e1 =Edao.save(new Etudiant("Nadia", "El amrani"));
		Etudiant e2 =Edao.save(new Etudiant("karim", "Ait elghazi"));
		Etudiant e3 =Edao.save(new Etudiant("ahmed", "nassiri"));
		Etudiant e4 =Edao.save(new Etudiant("laila", "Karbali"));
		List<Etudiant> list = Edao.EtudiantParMC("S");
			for(Etudiant e:list)
			{
		     	System.out.println(e.toString());
			}
	
			
		}
	

}
