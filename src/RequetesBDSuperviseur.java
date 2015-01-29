import java.sql.Connection;

import java.sql.Date;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.text.ParseException;

import java.text.SimpleDateFormat;

public class RequetesBDSuperviseur {

	/**
	 * Afficher tout les clients
	 * 	 * @param conn
	 *            connexion ? la base de donn?es
	 * @throws SQLException
	 *  en cas d'erreur d'acc?s ? la base de donn?es
	 */

	public static String afficherClients(Connection conn) throws SQLException {
		String res = "";
		// Get a statement from the connection
		Statement stmt = conn.createStatement();
		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM CLIENT");
		// Loop through the result set
		while (rs.next()) {
			res += "Nom : " + rs.getString("NOM") + "\n";
		}
		// Close the result set, statement and the connection
		rs.close();
		stmt.close();
		System.out.println(res);
		return res;
	}

	public static String afficherRoutine(Connection conn) throws SQLException {
		String res = "";
		// Get a statement from the connection
		Statement stmt = conn.createStatement();
		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM ROUTINE");
		// Loop through the result set
		while (rs.next()) {
			res += "\n\nNumero routine: "
					+ rs.getString("NUM_ROUTINE")
					+ "\n"
					+ "Numero Superviseur: "
					+ rs.getString("NUM_SUPERVISEUR")
					+ "\n"
					+ "Immatriculation véhicule : "
					+ rs.getString("IMMATRICULATION")
					+ "\n"
					+ "Date routine : "
					+ rs.getString("DATE_ROUTINE")
					+ "\n"
					+ "\n*****************************************************************";
		}
		// Close the result set, statement and the connection
		rs.close();
		stmt.close();
		System.out.println(res);
		return res;
	}

	public static void insertRoutine(Connection conn,
			int numSuperviseur, int immatriculationVehicule, String dateRoutine)
					throws SQLException, ParseException {

		// Get a statement from the connection
		String sql = "INSERT INTO ROUTINE VALUES (sequence_routine.nextval,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numSuperviseur);
		preparedStatement.setInt(2, immatriculationVehicule);
		preparedStatement.setDate(3, convertStringToDate(dateRoutine));
		preparedStatement.executeUpdate();
		preparedStatement.close();
		// conn.close();
	}
	public static Date convertStringToDate(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsed = format.parse(date);
		return new java.sql.Date(parsed.getTime());
	}

	public static void insertAction(Connection conn,
			int numRoutine, String lieuAction, String commentaireAction,
			String etatAction, String notificationAction, int prioriteAction,
			int nbDeplacement, String lieuActionArrive) throws SQLException,
			ParseException {

		// Get a statement from the connection
		String sql = "INSERT INTO ACTION VALUES (sequence_action.nextval,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numRoutine);
		preparedStatement.setString(2, lieuAction);
		preparedStatement.setString(3, commentaireAction);
		preparedStatement.setString(4, etatAction);
		preparedStatement.setString(5, notificationAction);
		preparedStatement.setInt(6, prioriteAction);
		preparedStatement.setInt(7, nbDeplacement);
		preparedStatement.setString(8, lieuActionArrive);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		// conn.close();
	}

	public static void afficherActions(Connection conn, int numRoutine)
			throws SQLException {

		String sql = "SELECT * FROM ACTION WHERE NUM_ROUTINE = ?";
		String res = "";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numRoutine);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next())
		{
			res += "\n\nNumero action : "
					+ rs.getString("NUM_ACTION")
					+ "\n"
					+ "Lieu : "
					+ rs.getString("LIEU")
					+ "\n"
					+ "Commentaire : "
					+ rs.getString("COMMENTAIRE")
					+ "\n"
					+ "Etat : "
					+ rs.getString("ETAT")
					+ "\n"
					+ "Notification : "
					+ rs.getString("NOTIFICATION")
					+ "\n"
					+ "Priorite : "
					+ rs.getString("PRIORITE")
					+ "\n"
					+ "Nombre deplacements : "
					+ rs.getString("NBDEPLACEMENT")
					+ "\n"
					+ "Lieu arrivee : "
					+ rs.getString("LIEU_ARRIVE")
					+ "\n"
					+ "\n*****************************************************************";
		}
		System.out.println(res);
		preparedStatement.close();
		// Execute the query
	}

	public static void afficherActionCourante(Connection conn,
			int immatriculation) throws SQLException {

		String res = "";
		String sql = "SELECT * FROM ACTION WHERE ETAT='null' AND NUM_ROUTINE = (SELECT NUM_ROUTINE FROM ROUTINE WHERE IMMATRICULATION = ?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, immatriculation);
		ResultSet rs = preparedStatement.executeQuery();
		boolean sortir = false;
		while (rs.next() && sortir == false)
		{
			res += "\n\nNumero action : "
					+ rs.getString("NUM_ACTION")
					+ "\n"
					+ "Lieu : "
					+ rs.getString("LIEU")
					+ "\n"
					+ "Commentaire : "
					+ rs.getString("COMMENTAIRE")
					+ "\n"
					+ "Etat : "
					+ rs.getString("ETAT")
					+ "\n"
					+ "Notification : "
					+ rs.getString("NOTIFICATION")
					+ "\n"
					+ "Priorite : "
					+ rs.getString("PRIORITE")
					+ "\n"
					+ "Nombre deplacements : "
					+ rs.getString("NBDEPLACEMENT")
					+ "\n"
					+ "Lieu arrivee : "
					+ rs.getString("LIEU_ARRIVE")
					+ "\n"
					+ "\n*****************************************************************";
			sortir = true;
		}
		System.out.println(res);
		preparedStatement.close();
		// Execute the query
	}

	public static void afficherStation(Connection conn) throws SQLException {
		String res = "";
		// Get a statement from the connection
		Statement stmt = conn.createStatement();
		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM STATION");
		// Loop through the result set
		while (rs.next()) {
			res += "\n\nNumero station: "
					+ rs.getString("NUM_STATION")
					+ "\n"
					+ "Nom station: "
					+ rs.getString("NOM_STATION")
					+ "\n"
					+ "Rue : "
					+ rs.getString("RUE")
					+ "\n"
					+ "Code postal : "
					+ rs.getString("CODE_POSTAL")
					+ "\n"
					+ "Ville : "
					+ rs.getString("VILLE")
					+ "\n"
					+ "Etat station : "
					+ rs.getString("ETAT_STATION")
					+ "\n"
					+ "\n*****************************************************************";
		}
		// Close the result set, statement and the connection
		rs.close();
		stmt.close();
		System.out.println(res);
	}

	public static void afficherNbVeloStation(Connection conn, int numStation)
			throws SQLException {
		String res = "";
		String sql = "SELECT COUNT(*)"
				+ "FROM VELO INNER JOIN VELOBORNETTE ON VELO.ID_VELO = VELOBORNETTE.ID_VELO INNER JOIN BORNETTE ON VELOBORNETTE.NUM_BORNE=BORNETTE.NUM_BORNE INNER JOIN STATION ON BORNETTE.NUM_STATION=STATION.NUM_STATION "
				+ "WHERE STATION.NUM_STATION=? AND(VELO.POSITION=? OR VELO.POSITION=?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numStation);
		preparedStatement.setString(2, "bornette");
		preparedStatement.setString(3, "reserve");
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next())
		{
			int nbvelos = rs.getInt(1);
			System.out.println("Il y a : " + nbvelos
					+ " vélos dans la station " + numStation + ".");
		}
		preparedStatement.close();
		// Execute the query
	}

	public static void afficherNbVeloStationEndommage(Connection conn,
			int numStation) throws SQLException {
		String res = "";
		String sql = "SELECT COUNT(*)"
				+ "FROM VELO INNER JOIN VELOBORNETTE ON VELO.ID_VELO = VELOBORNETTE.ID_VELO INNER JOIN BORNETTE ON VELOBORNETTE.NUM_BORNE=BORNETTE.NUM_BORNE INNER JOIN STATION ON BORNETTE.NUM_STATION=STATION.NUM_STATION "
				+ "WHERE STATION.NUM_STATION=? AND(VELO.POSITION=? OR VELO.POSITION=?) AND VELO.ETAT=?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numStation);
		preparedStatement.setString(2, "bornette");
		preparedStatement.setString(3, "reserve");
		preparedStatement.setString(4, "hs");
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next())
		{
			int nbvelos = rs.getInt(1);
			System.out.println("Il y a : " + nbvelos
					+ " vélos endommages dans la station " + numStation + ".");
		}
		preparedStatement.close();
		// Execute the query
	}

	public static void afficherNbVeloStationLibres(Connection conn,
			int numStation) throws SQLException {
		String res = "";
		String sql = "SELECT COUNT(*)"
				+ "FROM VELO INNER JOIN VELOBORNETTE ON VELO.ID_VELO = VELOBORNETTE.ID_VELO INNER JOIN BORNETTE ON VELOBORNETTE.NUM_BORNE=BORNETTE.NUM_BORNE INNER JOIN STATION ON BORNETTE.NUM_STATION=STATION.NUM_STATION "
				+ "WHERE STATION.NUM_STATION=? AND(VELO.POSITION=? OR VELO.POSITION=?) ";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numStation);
		preparedStatement.setString(2, "location");
		preparedStatement.setString(3, "voiture");
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next())
		{
			int nbvelos = rs.getInt(1);
			System.out.println("Il y a : " + nbvelos
					+ " places libres dans la station " + numStation + ".");
		}
		preparedStatement.close();
		// Execute the query
	}

	public static void afficherActionRoutine(Connection conn, String numStation)
			throws SQLException {
		String res = "";
		String sql = "SELECT * FROM ACTION WHERE LIEU_ARRIVE=?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, numStation);
		ResultSet rs = preparedStatement.executeQuery();
		boolean sortir = false;
		while (rs.next() && sortir == false)
		{
			res += "\n\nNumero action : "
					+ rs.getString("NUM_ACTION")
					+ "\n"
					+ "Lieu : "
					+ rs.getString("LIEU")
					+ "\n"
					+ "Commentaire : "
					+ rs.getString("COMMENTAIRE")
					+ "\n"
					+ "Etat : "
					+ rs.getString("ETAT")
					+ "\n"
					+ "Notification : "
					+ rs.getString("NOTIFICATION")
					+ "\n"
					+ "Priorite : "
					+ rs.getString("PRIORITE")
					+ "\n"
					+ "Nombre deplacements : "
					+ rs.getString("NBDEPLACEMENT")
					+ "\n"
					+ "Lieu arrivee : "
					+ rs.getString("LIEU_ARRIVE")
					+ "\n"
					+ "\n*****************************************************************";
			sortir = true;
		}
		System.out.println(res);
		preparedStatement.close();
		// Execute the query
	}

	public static void afficherNbVeloVehicule(Connection conn, int numVehicule) throws SQLException {		
		String res = "";
		String sql = "SELECT COUNT(*)"
				+ "FROM ROUTINE INNER JOIN VEHICULE ON ROUTINE.IMMATRICULATION = VEHICULE.IMMATRICULATION INNER JOIN TRANSPORTERVEHICULEVELO ON VEHICULE.IMMATRICULATION=TRANSPORTERVEHICULEVELO.IMMATRICULATION INNER JOIN VELO ON TRANSPORTERVEHICULEVELO.ID_VELO=VELO.ID_VELO "
				+ "WHERE ROUTINE.IMMATRICULATION=? AND VELO.POSITION=?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numVehicule);
		preparedStatement.setString(2, "voiture");
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next())
		{
			int nbvelos = rs.getInt(1);
			System.out.println("Il y a : " + nbvelos
					+ " velos dans le vehicule " + numVehicule + ".");
		}
		preparedStatement.close();
		// Execute the query
	}

	public static void afficherPlagesHoraires(Connection conn) throws SQLException {
		String res = "";
		// Get a statement from the connection
		Statement stmt = conn.createStatement();
		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM PLAGEHORAIRE");
		// Loop through the result set
		while (rs.next()) {
			res += "\n\nNumero station: "
					+ rs.getString("NUM_STATION")
					+ "\n"
					+ "Numero plage: "
					+ rs.getString("ID_PLAGE")
					+ "\n"
					+ "Date debut : "
					+ rs.getString("DATE_DEBUT")
					+ "\n"
					+ "Date fin : "
					+ rs.getString("DATE_FIN")
					+ "\n"
					+ "Etat station : "
					+ rs.getString("ETAT_STATION")
					+ "\n"
					+ "\n*****************************************************************";
		}
		// Close the result set, statement and the connection
		rs.close();
		stmt.close();
		System.out.println(res);		
	}

	public static void creerPlagehoraire(Connection conn, int numStation, int numPlage, String dateDebut
			, String datePlage, String etatStation) throws SQLException, ParseException {
		// Get a statement from the connection
		String sql = "INSERT INTO PLAGEHORAIRE VALUES (?,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, numStation);
		preparedStatement.setInt(2, numPlage);
		preparedStatement.setDate(3, convertStringToDate(dateDebut));
		preparedStatement.setDate(4, convertStringToDate(datePlage));
		preparedStatement.setString(5, etatStation);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		// conn.close();
		
	}

	public static void editerPlagehoraire(Connection conn, int numPlage, int numStation, String dateDebut, String datePlage, String etatStation) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		// Get a statement from the connection
				String sql = "UPDATE PLAGEHORAIRE SET NUM_STATION=?,DATE_DEBUT=?,DATE_FIN=?,ETAT_STATION=?  WHERE ID_PLAGE=?";
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, numStation);
				preparedStatement.setDate(2, convertStringToDate(dateDebut));
				preparedStatement.setDate(3, convertStringToDate(datePlage));
				preparedStatement.setString(4, etatStation);
				preparedStatement.setInt(5, numPlage);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				// conn.close();
		;
		
	}
}
