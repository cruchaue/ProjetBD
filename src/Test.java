import java.sql.SQLException;

public class Test {

	public static void main(String[] args) throws SQLException {
	
		/*String saisie="";
		System.out
				.println("Bonjour ! Bienvenue dans l'application VEPICK :"
						+ "\n\nVeuillez choisir une option :"
						+ "\n1 : Application CLIENT"
						+ "\n2 : Application SUPERVISEUR"
						+ "\n3 : Application CONDUCTEUR"
						+ "\n(0 : Quitter)\n");
		System.out.flush();
		int i = LectureClavier.lireEntier(saisie);
		
		switch (i)
		{
		case 0:
		    System.exit(0);
		    break;
		  case 1:
		    Client c1 = new Client();
		    break;
		  case 2:
			  Superviseur s1 = new Superviseur();
		    break;
		  case 3:
			  Conducteur c2 = new Conducteur();
		    break;
		}
		*/
		
		RequetesBD r = new RequetesBD();
		Connexion connexion1 = new Connexion();
		RequetesBD.afficherClients(connexion1.getConn());

	}
}
