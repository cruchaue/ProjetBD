import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Classe mettant en oeuvre des requ�tes SQL.
 
 */
public class RequetesVehicule {


	
	/**
	 * Afficher tout les clients
	 * 
	 * @param conn
	 *            connexion � la base de donn�es
	 * @throws SQLException
	 *             en cas d'erreur d'acc�s � la base de donn�es
	 */
	public static String afficherClients(Connection conn) throws SQLException{

			String res = "";

			// Get a statement from the connection
			Statement stmt = conn.createStatement();
			// Execute the query
			ResultSet rs = stmt.executeQuery("SELECT * FROM CLIENT");
		
			
			// Loop through the result set
			while (rs.next()) {
				res += "Nom : " +rs.getString("NOM") + "\n";		
			}

			// Close the result set, statement and the connection
			rs.close();
			stmt.close();
			//System.out.println(res);
			return res;
		}
	
	
	
	
	public static String dateActuelle(){
		String format ="dd/MM/yy H:mm:ss";
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
		java.util.Date date = new java.util.Date();
		return formater.format(date);
		}
	
	public static java.sql.Date convertStringToDateFormat(String date) throws ParseException, java.text.ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
		java.util.Date parsed = format.parse(date);
		return new java.sql.Date(parsed.getTime());
		}
	
	
	
	
	public static int bornetteDisponible(Connection conn, int idStation) throws SQLException {
		String res="";
		int numBorne=-1;
		String sql="SELECT distinct(b.num_borne) FROM BORNETTE b, VELOBORNETTE vb, VELO v WHERE NUM_STATION = ? AND b.NUM_BORNE = vb.NUM_BORNE AND v.ID_VELO = vb.ID_VELO AND b.ETAT = ? AND V.POSITION IN(?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, idStation);
		preparedStatement.setString(2, "ok");
		preparedStatement.setString(3, "location");
		preparedStatement.setString(4, "voiture");
		ResultSet rs = preparedStatement.executeQuery();
		while(rs.next()){
		res="Deposez le vélo à la bornette " + rs.getInt("NUM_BORNE");
		numBorne= rs.getInt("NUM_BORNE");
		}
		if(numBorne==-1){
		res="Désolé aucune bornette disponible";
		}
		rs.close();
		preparedStatement.close();
		System.out.println(res);
		return numBorne;
		}
	
	
	
	
	
	
	
	/*--------------------------------------*/
	
	
	
	
	
	
	public static ArrayList afficherAction(Connection conn, int immatriculation, String Date) throws SQLException{

		// Get a statement from the connection
		PreparedStatement st = conn.prepareStatement("select * from action inner join routine on action.num_routine=routine.num_routine inner join vehicule on routine.immatriculation=vehicule.immatriculation where vehicule.immatriculation = ? and action.etat = 'null' and routine.date_routine = ? order by action.PRIORITE");
		// Execute the query
		st.setInt(1, immatriculation);
		st.setString(2, Date);
		ResultSet rs = st.executeQuery();
		//a rajouter where l'imattriculation et trier par jour et par orde de priorité et dont la notification est null pour afficher que celle dont il dit s'occuper  	
		ArrayList tabTest = new ArrayList();
		
		// Loop through the result set
		while (rs.next()) {
			ActionPropriete action = new ActionPropriete(rs.getInt("NUM_ACTION"),rs.getInt("NUM_ROUTINE"),rs.getString("LIEU"),rs.getString("COMMENTAIRE"),rs.getString("ETAT"),rs.getString("NOTIFICATION"),rs.getInt("PRIORITE"),rs.getInt("NBDEPLACEMENT"),rs.getString("LIEU_ARRIVE"));
			tabTest.add(action);
		}

		// Close the result set, statement and the connection
		rs.close();
		st.close();
		//System.out.println(res);
		return tabTest;
	}



	public static void modifierEtatBornette(Connection conn, String lieu, int numBornette) throws SQLException {
		//4 - 5
		PreparedStatement st = conn.prepareStatement("UPDATE bornette SET etat = 'ok' WHERE num_borne= ?");
		// Execute the query
		st.setInt(1, numBornette);		
		ResultSet rs = st.executeQuery();
		rs.close();
		st.close();
		
	}



	public static void modifierEtatAction(Connection connection, int numAction) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement st = connection.prepareStatement("UPDATE action SET etat = 'valide' WHERE num_action= ?");
		// Execute the query
		st.setInt(1, numAction);		
		ResultSet rs = st.executeQuery();
		rs.close();
		st.close();
		
	}



	public static void modifierEtatVelo(Connection connection, String lieu,	int numVelo) throws SQLException {
		PreparedStatement st = connection.prepareStatement("UPDATE velo SET etat = 'ok' WHERE id_velo= ?");
		// Execute the query
		st.setInt(1, numVelo);		
		ResultSet rs = st.executeQuery();
		rs.close();
		st.close();
		
	}



	public static String afficherVeloDispo(Connection connection, String lieu) throws SQLException {
		// TODO Auto-generated method stub
		String res = "";
		PreparedStatement st = connection.prepareStatement("SELECT distinct(velo.id_velo) FROM VELO INNER JOIN VELOBORNETTE ON VELO.ID_VELO = VELOBORNETTE.ID_VELO INNER JOIN BORNETTE ON VELOBORNETTE.NUM_BORNE=BORNETTE.NUM_BORNE WHERE Bornette.NUM_STATION=? and velo.position = 'bornette'");
		// Execute the query
		st.setString(1, lieu);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			res += "liste vélos : " +rs.getString("id_velo") + "\n";		
		}
		rs.close();
		st.close();
		return res;
	}



	public static void embarquerVelo(Connection connection,	ArrayList veloEmbarque, ActionPropriete action, int immat) throws SQLException, ParseException, java.text.ParseException {
		// changer etat velo
		
		for(int i = 0; i<veloEmbarque.size();i++){
			PreparedStatement st = connection.prepareStatement("UPDATE velo SET position = 'voiture' WHERE id_velo= ?");
			// Execute the query
			st.setInt(1, (int) veloEmbarque.get(i));		
			ResultSet rs = st.executeQuery();
			rs.close();
			st.close();	
		
			String dateActuelle = dateActuelle();
			
			PreparedStatement st2 = connection.prepareStatement("Insert into TransporterVehiculeVelo values (?, ?, ?)");
			// Execute the query
			st2.setInt(1, (int) veloEmbarque.get(i));
			st2.setInt(2, immat);
			st2.setDate(3, convertStringToDateFormat(dateActuelle));		
			ResultSet rs2 = st2.executeQuery();
			rs2.close();
			st2.close();	
			
		}

		//creer entite vehiculevelo
		
		
	}




	public static void modifierEtatNotificationAction(Connection connection, String raisonErreur, int num_action) throws SQLException {
		PreparedStatement st = connection.prepareStatement("UPDATE action SET etat = 'invalide', notification = ? WHERE num_action = ?");
		// Execute the query
		st.setString(1, raisonErreur);
		st.setInt(2, num_action);		
		ResultSet rs = st.executeQuery();
		rs.close();
		st.close();	
	}



	public static String listeBornetteVide(Connection connection, int idStation) throws SQLException {
		// TODO Auto-generated method stub

			String res="";
			String sql="SELECT distinct(b.num_borne) FROM BORNETTE b, VELOBORNETTE vb, VELO v WHERE NUM_STATION = ? AND b.NUM_BORNE = vb.NUM_BORNE AND v.ID_VELO = vb.ID_VELO AND b.ETAT = ? AND V.POSITION IN(?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idStation);
			preparedStatement.setString(2, "ok");
			preparedStatement.setString(3, "location");
			preparedStatement.setString(4, "voiture");
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
			res +="bornette vide : " + rs.getInt("NUM_BORNE")+"\n";
			}
			rs.close();
			preparedStatement.close();
			return res;
	}

	

	public static String listerVeloVehicule(Connection connection, int immat) throws SQLException {
		// TODO Auto-generated method stub
		String res = "";
		PreparedStatement st = connection.prepareStatement("select * from transportervehiculevelo inner join velo on transportervehiculevelo.id_velo=velo.id_velo where transportervehiculevelo.immatriculation = ? and velo.position = 'voiture'");
		// Execute the query
		st.setInt(1, immat);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			res += "liste vélos : " +rs.getString("id_velo") + "\n";		
		}
		rs.close();
		st.close();
		return res;	
	}




	public static void debarquerVelo(Connection connection, Object object,	Object object2, ActionPropriete action) throws SQLException, ParseException, java.text.ParseException {
		
		PreparedStatement st = connection.prepareStatement("UPDATE velo SET position = 'bornette' WHERE id_velo= ?");
		// Execute the query
		st.setInt(1, (int) object);		
		ResultSet rs = st.executeQuery();
		rs.close();
		st.close();	
		
		String dateActuelle = dateActuelle();
		
		PreparedStatement st2 = connection.prepareStatement("Insert into velobornette values (?, ?, ?)");
		// Execute the query
		st2.setInt(1, (int) object2);
		st2.setInt(2, (int) object);
		st2.setDate(3, convertStringToDateFormat(dateActuelle));		
		ResultSet rs2 = st2.executeQuery();
		rs2.close();
		st2.close();
		
	}
	
}