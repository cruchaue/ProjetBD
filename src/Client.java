import java.sql.SQLException;
import java.text.ParseException;

public class Client {
	public static void main(String[] args) {
		
		Connexion conn = new Connexion();		
		String saisie = "";
		System.out.println("******************Client******************");
		System.out.println("Bonjour ! Bienvenue dans l'application VEPICK :"
				+ "\n\nVeuillez choisir une option :" + "\n1 : S'abonner"
				+ "\n2 : Emprunter un vélo ou Réserver un vélo" + "\n3 : Rendre un vélo"
				+ "\n4 : Infos bornes"
				+ "\n5 : R�server un v�lo"
				+ "\n(0 : Quitter)\n");
		System.out.flush();
		int i = LectureClavier.lireEntier(saisie);
		switch (i) {
		case 0:
			System.exit(0);
			break;
		case 1:
			System.out.println("Voulez-vous vous abonner O/N :");
			System.out.flush();
			boolean b = LectureClavier.lireOuiNon(saisie);
			if (b == true) {
				System.out.println("Votre nom : ");
				System.out.flush();
				String nom = LectureClavier.lireChaine();
				System.out.println("Votre prenom : ");
				System.out.flush();
				String prenom = LectureClavier.lireChaine();
				System.out.println("Etes-vous une femme ou un homme : ");
				System.out.flush();
				String homme = LectureClavier.lireChaine();
				System.out.println("Vore date de naissance (dd/mm/yyyy) : ");
				System.out.flush();
				String dateNaissance = LectureClavier.lireChaine();
				System.out.println("Vore adresse : ");
				System.out.flush();
				String adresse = LectureClavier.lireChaine();
				System.out.println("Code postal : ");
				System.out.flush();
				int codePostal = LectureClavier.lireEntier(saisie);
				System.out.println("Ville : ");
				System.out.flush();
				String ville = LectureClavier.lireChaine();
				System.out.println("Code secret : ");
				System.out.flush();
				int codeSecret = LectureClavier.lireEntier(saisie);
				System.out.println("Numero CB : ");
				System.out.flush();
				int numCB = LectureClavier.lireEntier(saisie);
				
				try {
					RequetesBD.insertAbonne(conn.getConn(), nom, prenom, homme, dateNaissance, adresse, codePostal, ville, codeSecret, numCB);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Saisir votre numero de carte bancaire");
				System.out.flush();
				int numCB = LectureClavier.lireEntier(saisie);
				int codeSecret;
				try {
					codeSecret = RequetesBD.insertClient(conn.getConn(),numCB);
					System.out.println("Votre inscription a été prise en compte votre code secret est : "+ codeSecret );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			break;
		case 2:
			System.out.println("Afin de louer un vélo veuillez entrer l'identifant de la station");
			String res;
			try {
				int velodispo=-1;
				int idVelo =0;
				while(velodispo==-1){
					res = RequetesBD.afficherStations(conn.getConn());
					System.out.println(res);
					System.out.println("Saisir num station :");
					System.out.flush();
					int idStation = LectureClavier.lireEntier(saisie);
				    idVelo = RequetesBD.veloDisponible(conn.getConn(), idStation);
					velodispo=idVelo;
				}
				boolean valide = false;
				int identifiant=0;
				int codeSecret=0;
				while (valide==false) {
					System.out.println("Authentification");
					System.out.println("Saisir votre identifiant");
					System.out.flush();
					identifiant = LectureClavier.lireEntier(saisie);
					System.out.println("Saisir votre code secret");
					System.out.flush();
					codeSecret = LectureClavier.lireEntier(saisie);
					valide= RequetesBD.identification(conn.getConn(), identifiant, codeSecret);
				}
				RequetesBD.louerVelo(conn.getConn(), idVelo, identifiant);	
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 3:
			boolean valide = false;
			int identifiant=0;
			int codeSecret=0;
			while (valide==false) {
				System.out.println("Authentification");
				System.out.println("Saisir votre identifiant");
				System.out.flush();
				identifiant = LectureClavier.lireEntier(saisie);
				System.out.println("Saisir votre code secret");
				System.out.flush();
				codeSecret = LectureClavier.lireEntier(saisie);
				try {
					valide= RequetesBD.identification(conn.getConn(), identifiant, codeSecret);
					int bornettedispo=-1;
					while(bornettedispo==-1){
						res = RequetesBD.afficherStations(conn.getConn());
						System.out.println(res);
						System.out.println("Saisir num station :");
						System.out.flush();
						int idStation = LectureClavier.lireEntier(saisie);
						bornettedispo = RequetesBD.bornetteDisponible(conn.getConn(), idStation);    
					}
					//verifier velo
					System.out.println("Saisir l'id du vélo à rendre : ");
					System.out.flush();
					int idVelo = LectureClavier.lireEntier(saisie);
					boolean veloTrouve=RequetesBD.checkIdVelo(conn.getConn(), identifiant, idVelo);
					
					while(veloTrouve==false){
						System.out.println("Le vélo ne coresspond pas au vélo loué de nouveau : ");
						System.out.flush();
						idVelo = LectureClavier.lireEntier(saisie);
						veloTrouve=RequetesBD.checkIdVelo(conn.getConn(), identifiant, idVelo);
					}
					
					RequetesBD.rendreVelo(conn.getConn(),identifiant,idVelo,bornettedispo);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
			
		case 5:
			boolean authentification_valide = false;
			int id =0;
			int code_secret =0;
			while (authentification_valide==false) {
				System.out.println("Authentification");
				System.out.println("Saisir votre identifiant");
				System.out.flush();
				identifiant = LectureClavier.lireEntier(saisie);
				System.out.println("Saisir votre code secret");
				System.out.flush();
				code_secret = LectureClavier.lireEntier(saisie);
				try {
					valide= RequetesBD.identification(conn.getConn(), id, code_secret);
					System.out.println("R�server un v�lo � une station \n");
					res = RequetesBD.afficherStations(conn.getConn());
						System.out.println(res);
						System.out.println("Saisir num station :");
						System.out.flush();
						int idStation = LectureClavier.lireEntier(saisie);
						System.out.println("Choisir une date de r�servation (jj-mm-aaaa hh:mm) \n")  ;
						System.out.flush();
						
						String date_location = LectureClavier.lireChaine();
						// TODO : Test date bien form�e
						
			
					
					RequetesBD.reserverVelo(conn.getConn(),id,idStation,date_location);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
	}
}