import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//import oracle.net.aso.p;

/**
 * Classe mettant en oeuvre des requ�tes SQL.
 
 */
public class RequetesBD {


	
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
		System.out.println(res);
		return res;
	}


	public static String afficherRoutine(Connection conn) throws SQLException{

		String res = "";

		// Get a statement from the connection
		Statement stmt = conn.createStatement();

		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM ROUTINE");


		// Loop through the result set
		while (rs.next()) {
			res += "\n\nNumero routine: " +rs.getString("NUM_ROUTINE") + "\n"
					+ "Numero Superviseur: " +rs.getString("NUM_SUPERVISEUR") + "\n"
					+ "Immatriculation véhicule : " +rs.getString("IMMATRICULATION") + "\n"
					+ "Date routine : " +rs.getString("DATE_ROUTINE") + "\n"
					+ "\n*****************************************************************";

		}
		// Close the result set, statement and the connection
		rs.close();
		stmt.close();
		System.out.println(res);
		return res;
	}

	public static void insertRoutine(Connection conn, int numRoutine, int numSuperviseur, int immatriculationVehicule, String dateRoutine) throws SQLException, ParseException{
		// Get a statement from the connection
		String sql = "INSERT INTO ROUTINE VALUES (?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numRoutine);
		preparedStatement.setInt(2, numSuperviseur);
		preparedStatement.setInt(3, immatriculationVehicule);
		preparedStatement.setDate(4, convertStringToDate(dateRoutine));

		preparedStatement.executeUpdate();

		preparedStatement.close();
		//conn.close();
	}

	public static void insertAction(Connection conn, int numAction, int numRoutine, String lieuAction, String commentaireAction, String etatAction, String notificationAction, int prioriteAction, int nbDeplacement, String lieuActionArrive) throws SQLException, ParseException{
		// Get a statement from the connection
		String sql = "INSERT INTO ACTION VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numAction);
		preparedStatement.setInt(2, numRoutine);
		preparedStatement.setString(3, lieuAction);
		preparedStatement.setString(4, commentaireAction);
		preparedStatement.setString(5, etatAction);
		preparedStatement.setString(6, notificationAction);
		preparedStatement.setInt(7, prioriteAction);
		preparedStatement.setInt(8, nbDeplacement);
		preparedStatement.setString(9, lieuActionArrive);


		preparedStatement.executeUpdate();

		preparedStatement.close();
		//conn.close();
	}

	public static void afficherActions(Connection conn, int numRoutine) throws SQLException {
		String sql = "SELECT * FROM ACTION WHERE NUM_ROUTINE = ?";
		String res="";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numRoutine);
		ResultSet rs = preparedStatement.executeQuery();
		while(rs.next())
		{
			res += "\n\nNumero action : " +rs.getString("NUM_ACTION") + "\n"
					+ "Lieu : " +rs.getString("LIEU") + "\n"
					+ "Commentaire : " +rs.getString("COMMENTAIRE") + "\n"
					+ "Etat : " +rs.getString("ETAT") + "\n"
					+ "Notification : " +rs.getString("NOTIFICATION") + "\n"
					+ "Priorite : " +rs.getString("PRIORITE") + "\n"
					+ "Nombre deplacements : " +rs.getString("NBDEPLACEMENT") + "\n"
					+ "Lieu arrivee : " +rs.getString("LIEU_ARRIVE") + "\n"
					+ "\n*****************************************************************";
		}
		System.out.println(res);
		preparedStatement.close();	
		// Execute the query
	}


	public static void afficherActionCourante(Connection conn, int immatriculation) throws SQLException {
		String res="";
		String sql = "SELECT * FROM ACTION WHERE ETAT='null' AND NUM_ROUTINE = (SELECT NUM_ROUTINE FROM ROUTINE WHERE IMMATRICULATION = ?)";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, immatriculation);
		ResultSet rs = preparedStatement.executeQuery();
		boolean sortir = false;
		while(rs.next() && sortir==false)
		{
			res += "\n\nNumero action : " +rs.getString("NUM_ACTION") + "\n"
					+ "Lieu : " +rs.getString("LIEU") + "\n"
					+ "Commentaire : " +rs.getString("COMMENTAIRE") + "\n"
					+ "Etat : " +rs.getString("ETAT") + "\n"
					+ "Notification : " +rs.getString("NOTIFICATION") + "\n"
					+ "Priorite : " +rs.getString("PRIORITE") + "\n"
					+ "Nombre deplacements : " +rs.getString("NBDEPLACEMENT") + "\n"
					+ "Lieu arrivee : " +rs.getString("LIEU_ARRIVE") + "\n"
					+ "\n*****************************************************************";
			sortir=true;
		}
		System.out.println(res);
		preparedStatement.close();	
		// Execute the query
	}
	
	public static Date convertStringToDate(String date) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsed = format.parse(date);
		return new java.sql.Date(parsed.getTime());
	}
	
	public static Date convertStringToDateFormat(String date) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
		java.util.Date parsed = format.parse(date);
		return new java.sql.Date(parsed.getTime());
	}
	
	public static String dateActuelle(){
		String format ="dd/MM/yy H:mm:ss";
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
		java.util.Date date = new java.util.Date();
		return formater.format(date);
	}
	
	public static void insertAbonne(Connection conn, String nom, String prenom, String civilite, String dateNaissance, String adresse, int codePostal, String ville, int codeSecret, int numCB) throws SQLException, ParseException{
		// Get a statement from the connection
		String sql = "INSERT INTO CLIENT VALUES (idClient_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numCB);
		preparedStatement.setInt(2, codeSecret);
		preparedStatement.setString(3, "oui");
		preparedStatement.setString(4, nom);
		preparedStatement.setString(5, prenom);
		preparedStatement.setDate(6, convertStringToDate(dateNaissance));
		preparedStatement.setString(7, civilite);
		preparedStatement.setString(8, ville);
		preparedStatement.setString(9, adresse);
		preparedStatement.setInt(10, codePostal);
		preparedStatement.setDate(11, convertStringToDate(dateNaissance));
		preparedStatement.setDate(12, convertStringToDate(dateNaissance));
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		//conn.close();
		
	
	}

	public static int insertClient(Connection conn, int numCB) throws SQLException {
		int codeSecret = 112212;
		String sql = "INSERT INTO CLIENT VALUES (idClient_seq.nextval,?,?,?,null,null,null,null,null,null,null,null,null)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numCB);
		preparedStatement.setInt(2, codeSecret);
		preparedStatement.setString(3, "non");
		preparedStatement.executeUpdate();
		preparedStatement.close();
		
		
		
		return codeSecret;
	}

	public static String afficherStations(Connection conn) throws SQLException {
		String res = "|NUM_STATION \t| NOM_STATION \t\t| RUE  \n";

		// Get a statement from the connection
		Statement stmt = conn.createStatement();

		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM STATION");
	
		
		// Loop through the result set
		while (rs.next()) {
			res +="| "+  rs.getString("NUM_STATION") + "\t\t| " + rs.getString("NOM_STATION") + "\t\t| " + rs.getString("RUE") + " \n";		
		}

		// Close the result set, statement and the connection
		rs.close();
		stmt.close();
		return res;
	}

	public static void louerVelo(Connection conn, int idVelo,
			int identifiant) throws SQLException, ParseException {
		try{
			String sql="INSERT INTO LOCATION VALUES(idLocation_seq.nextval,?,?, to_date(?,?), to_date(?,?))";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1,identifiant);
			preparedStatement.setInt(2,idVelo);
			preparedStatement.setString(3, dateActuelle());
			preparedStatement.setString(4, "dd/mm/yy HH24:MI:SS");
			preparedStatement.setString(5, "01/01/01 00:00:00");
			preparedStatement.setString(6, "dd/mm/yy HH24:MI:SS");
			preparedStatement.executeQuery();
			preparedStatement.close();
			//modifier l'état du vélo
			sql="UPDATE VELO SET POSITION = ? WHERE ID_VELO=?";
		
			PreparedStatement preparedStatement2 = conn.prepareStatement(sql);
			preparedStatement2.setString(1, "location");
			preparedStatement2.setInt(2, idVelo);
			preparedStatement2.executeQuery();
			preparedStatement2.close();
		}catch(java.sql.SQLException e){
			System.out.print("La location multiple de velo est interdite.");
		}
		
	}
	
	
	
	public static void afficherStation(Connection conn) throws SQLException {
		String res = "";

		// Get a statement from the connection
		Statement stmt = conn.createStatement();

		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM STATION");


		// Loop through the result set
		while (rs.next()) {
			res += "\n\nNumero station: " +rs.getString("NUM_STATION") + "\n"
					+ "Nom station: " +rs.getString("NOM_STATION") + "\n"
					+ "Rue : " +rs.getString("RUE") + "\n"
					+ "Code postal : " +rs.getString("CODE_POSTAL") + "\n"
					+ "Ville : " +rs.getString("VILLE") + "\n"
					+ "Etat station : " +rs.getString("ETAT_STATION") + "\n"
					+ "\n*****************************************************************";

		}
		// Close the result set, statement and the connection
		rs.close();
		stmt.close();
		System.out.println(res);


	}


	public static void afficherNbVeloStation(Connection conn, int numStation) throws SQLException {

		String res="";
		String sql = "SELECT COUNT(*)" 
				+"FROM VELO INNER JOIN VELOBORNETTE ON VELO.ID_VELO = VELOBORNETTE.ID_VELO INNER JOIN BORNETTE ON VELOBORNETTE.NUM_BORNE=BORNETTE.NUM_BORNE INNER JOIN STATION ON BORNETTE.NUM_STATION=STATION.NUM_STATION "
				+"WHERE STATION.NUM_STATION=? AND(VELO.POSITION=? OR VELO.POSITION=?)";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numStation);
		preparedStatement.setString(2, "bornette");
		preparedStatement.setString(3, "reserve");
		ResultSet rs = preparedStatement.executeQuery();
		if(rs.next())
		{
			int nbvelos = rs.getInt(1);
			System.out.println("Il y a : "+nbvelos+" vélos dans la station "+numStation+".");
		}		
		preparedStatement.close();	
		// Execute the query
	}


	public static void afficherNbVeloStationEndommage(Connection conn,
			int numStation) throws SQLException {

		String res="";
		String sql = "SELECT COUNT(*)" 
				+"FROM VELO INNER JOIN VELOBORNETTE ON VELO.ID_VELO = VELOBORNETTE.ID_VELO INNER JOIN BORNETTE ON VELOBORNETTE.NUM_BORNE=BORNETTE.NUM_BORNE INNER JOIN STATION ON BORNETTE.NUM_STATION=STATION.NUM_STATION "
				+"WHERE STATION.NUM_STATION=? AND(VELO.POSITION=? OR VELO.POSITION=?) AND VELO.ETAT=?";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numStation);
		preparedStatement.setString(2, "bornette");
		preparedStatement.setString(3, "reserve");
		preparedStatement.setString(4, "hs");
		ResultSet rs = preparedStatement.executeQuery();
		if(rs.next())
		{
			int nbvelos = rs.getInt(1);
			System.out.println("Il y a : "+nbvelos+" vélos endommages dans la station "+numStation+".");
		}		
		preparedStatement.close();	
		// Execute the query
		
	}


	public static void afficherNbVeloStationLibres(Connection conn,
			int numStation) throws SQLException {


		String res="";
		String sql = "SELECT COUNT(*)" 
				+"FROM VELO INNER JOIN VELOBORNETTE ON VELO.ID_VELO = VELOBORNETTE.ID_VELO INNER JOIN BORNETTE ON VELOBORNETTE.NUM_BORNE=BORNETTE.NUM_BORNE INNER JOIN STATION ON BORNETTE.NUM_STATION=STATION.NUM_STATION "
				+"WHERE STATION.NUM_STATION=? AND(VELO.POSITION=? OR VELO.POSITION=?) ";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numStation);
		preparedStatement.setString(2, "location");
		preparedStatement.setString(3, "voiture");
		ResultSet rs = preparedStatement.executeQuery();
		if(rs.next())
		{
			int nbvelos = rs.getInt(1);
			System.out.println("Il y a : "+nbvelos+" places libres dans la station "+numStation+".");
		}		
		preparedStatement.close();	
		// Execute the query
		
	}


	public static void afficherActionRoutine(Connection conn, int numStation) throws SQLException {

		String res="";
		String sql = "SELECT * FROM ACTION WHERE LIEU_ARRIVE=?";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numStation);
		ResultSet rs = preparedStatement.executeQuery();
		boolean sortir=false;
		while(rs.next() && sortir==false)
		{
			res += "\n\nNumero action : " +rs.getString("NUM_ACTION") + "\n"
					+ "Lieu : " +rs.getString("LIEU") + "\n"
					+ "Commentaire : " +rs.getString("COMMENTAIRE") + "\n"
					+ "Etat : " +rs.getString("ETAT") + "\n"
					+ "Notification : " +rs.getString("NOTIFICATION") + "\n"
					+ "Priorite : " +rs.getString("PRIORITE") + "\n"
					+ "Nombre deplacements : " +rs.getString("NBDEPLACEMENT") + "\n"
					+ "Lieu arrivee : " +rs.getString("LIEU_ARRIVE") + "\n"
					+ "\n*****************************************************************";
			sortir=true;
		}		
		preparedStatement.close();	
		// Execute the query
		
	}
	
	public static int veloDisponible(Connection conn, int idStation) throws SQLException{
		int nb=-1;
		String res="";
		String sql="SELECT * FROM BORNETTE b, VELOBORNETTE vb, VELO v WHERE NUM_STATION = ? AND b.NUM_BORNE = vb.NUM_BORNE AND v.ID_VELO = vb.ID_VELO AND v.ETAT = ? AND V.POSITION = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, idStation);
		preparedStatement.setString(2, "ok");
		preparedStatement.setString(3, "bornette");
		ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				res="Le velo "+rs.getString("ID_VELO") + " est disponible à la bornette " + rs.getString("NUM_BORNE");
				nb = rs.getInt("ID_VELO");
			}
		if(nb==-1){
			res="Aucun velo disponible";
			
		}
		rs.close();
		preparedStatement.close();
		System.out.println(res);
		return nb;
		
	}


	public static boolean identification(Connection conn, int identifiant,
			int codeSecret) throws SQLException {
		int res=0;
		String sql="SELECT count(ID_CLIENT) FROM CLIENT WHERE ID_CLIENT=? AND CODE_SECRET=?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, identifiant);
		preparedStatement.setInt(2, codeSecret);
		ResultSet rs = preparedStatement.executeQuery();
		while(rs.next()){
			res= rs.getInt("count(ID_CLIENT)");
		}	
		return (res==1);
	}


	public static boolean isAbonne(Connection conn, int identifiant) throws SQLException {
		String sql = "SELECT * FROM CLIENT WHERE ID_CLIENT=?";
		String res = "";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, identifiant);
		ResultSet rs = preparedStatement.executeQuery();

		while(rs.next()){
			res=rs.getString("ABONNE");
		}
		
		return res.equals("oui");
	}


	public static int bornetteDisponible(Connection conn, int idStation) throws SQLException {
		String res="";
		int numBorne=-1;
		String sql="SELECT * FROM BORNETTE b, VELOBORNETTE vb, VELO v WHERE NUM_STATION = ? AND b.NUM_BORNE = vb.NUM_BORNE AND v.ID_VELO = vb.ID_VELO AND b.ETAT = ? AND V.POSITION IN(?,?)";
		
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
	
	public static boolean checkIdVelo(Connection conn, int identifiant, int idVelo) throws SQLException{
		int res=0;
		String sql="SELECT count(ID_VELO) FROM LOCATION WHERE ID_CLIENT=?  AND ID_VELO=? AND DATE_HEURE_FIN = to_date(?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, identifiant);
		preparedStatement.setInt(2, idVelo);
		preparedStatement.setString(3, "01/01/01 00:00:00");
		preparedStatement.setString(4, "dd/mm/yy HH24:MI:SS");
		ResultSet rs = preparedStatement.executeQuery();
		while(rs.next()){
			res= rs.getInt("count(ID_VELO)");
		}
		return (res==1);
		
	}


	public static void rendreVelo(Connection conn, int identifiant, int idVelo, int numBorne) throws SQLException, ParseException {
		
		String sql="UPDATE LOCATION SET DATE_HEURE_FIN=? WHERE ID_CLIENT=? AND  ID_VELO=? AND DATE_HEURE_FIN = to_date(?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setDate(1, convertStringToDateFormat(dateActuelle()));
		preparedStatement.setInt(2, identifiant);
		preparedStatement.setInt(3,idVelo);
		preparedStatement.setString(4, "01/01/01 00:00:00");
		preparedStatement.setString(5, "dd/mm/yy HH24:MI:SS");
		ResultSet rs = preparedStatement.executeQuery();
		rs.close();
		preparedStatement.close();
		//Passage de etat location a bornette pour le velo
		sql="UPDATE VELO SET POSITION = ? WHERE ID_VELO=?";
		PreparedStatement preparedStatement2 = conn.prepareStatement(sql);
		preparedStatement2.setString(1, "bornette");
		preparedStatement2.setInt(2, idVelo);
		preparedStatement2.executeQuery();
		preparedStatement2.close();
		//Nouvelle date
		sql="INSERT INTO \"DATE\" VALUES (to_date(?,?))";
		String dateInserte = dateActuelle();
		/*System.out.println(dateActuelle());
		PreparedStatement preparedStatement3 = conn.prepareStatement(sql);
		preparedStatement3.setString(1, dateInserte);
		preparedStatement3.setString(2, "dd/mm/yyyy HH24:MI:SS");
		preparedStatement3.executeQuery();
		preparedStatement3.close();*/
		sql="INSERT INTO VELOBORNETTE VALUES (?,?,to_date(?,?))";
		PreparedStatement preparedStatement4 = conn.prepareStatement(sql);
		preparedStatement4.setInt(1, numBorne);
		preparedStatement4.setInt(2, idVelo);
		preparedStatement4.setString(3,dateInserte);
		preparedStatement4.setString(4,"dd/mm/yyyy HH24:MI:SS");
		preparedStatement4.executeQuery();
		preparedStatement4.close();
		
	}


	public static void reserverVelo(Connection conn, int identifiant,
			int idStation, String date_location) {

		String sql = "INSERT INTO RESERVATION VALUES (idReservation_seq.nextval,?,?,?)" ;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, idStation);
			preparedStatement.setInt(2, identifiant);
			preparedStatement.setDate(3, convertStringToDateFormat(dateActuelle()));
			preparedStatement.executeQuery();
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}


