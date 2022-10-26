package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import metier.*;



public class EtudiantDaoImpl implements IEtudiantDao{

	@Override
	public Etudiant save(Etudiant e) {
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("INSERT INTO ETUDIANTS (ID,NOM,PRENOM) VALUES (?,?,?)");
			ps.setString(2,e.getNom());
			ps.setString(3,e.getPrenom());
			
				
				PreparedStatement ps2 = connection.prepareStatement("SELECT MAX(ID) AS MAX_ID FROM ETUDIANTS ");
				ResultSet rs=ps2.executeQuery();
				if(rs.next())
				{
					e.setId((int) rs.getLong("MAX_ID"));;
				}
				ps2.close();
			
			ps.setInt(1, e.getId()+1);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} 
		 
		return e;
		
	}

	@Override
	public List<Etudiant> EtudiantParMC(String mc) {
		List<Etudiant> Etudiants = new ArrayList<>();
		 Connection connection = SingletonConnection.getConnection();
		 mc = "%"+mc+"%";
		 try
		 {
			PreparedStatement ps =connection.prepareStatement("SELECT * FROM ETUDIANTS WHERE NOM LIKE ?");
			ps.setString(1, mc);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Etudiant e = new Etudiant();
				e.setId((int) rs.getLong("ID"));
				e.setNom(rs.getString("NOM"));
				e.setPrenom(rs.getString("PRENOM"));
				Etudiants.add(e);
			}
			
		 } catch (SQLException ex)
		 {
			ex.printStackTrace();
		}
			return Etudiants;
	}

	@Override
	public Etudiant getEtudiant(int id) {
		Etudiant e = null;
		Connection connection = SingletonConnection.getConnection();
		try 
		{
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM ETUDIANTS WHERE ID = ?");
		    ps.setLong(1,id);
		    ResultSet rs = ps.executeQuery();
		    if(rs.next())
		    {
		    	e = new Etudiant();
				e.setId((int) rs.getLong("ID"));
				e.setNom(rs.getString("NOM"));
				e.setPrenom(rs.getString("PRENOM"));
		    }
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return e;

	}

	@Override
	public Etudiant Update(int id, Etudiant e) {
		Connection connection =SingletonConnection.getConnection();
		String requet ="UPDATE ETUDIANTS SET NOM = ?, PRENOM = ? WHERE ID=? ";
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(requet);
			ps.setString(1, e.getNom());
			ps.setString(2, e.getPrenom());
			ps.setLong(3, id);
			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		e.setId(id);
		return e;
	}

	@Override
	public void deleteEtudiant(Long id) {
		Connection connection =SingletonConnection.getConnection();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("DELETE  FROM ETUDIANTS WHERE ID=?");
			ps.setLong(1,id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

	public List<Etudiant> Etudiants() {
		List<Etudiant> Etudiants = new ArrayList<>();
		 Connection connection = SingletonConnection.getConnection();
		 try
		 {
			PreparedStatement ps =connection.prepareStatement("SELECT * FROM ETUDIANTS");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Etudiant e = new Etudiant();
				e.setId((int) rs.getLong("ID"));
				e.setNom(rs.getString("NOM"));
				e.setPrenom(rs.getString("PRENOM"));
				Etudiants.add(e);
			}
			
		 } catch (SQLException ex)
		 {
			ex.printStackTrace();
		}
			return Etudiants;
	}

}
