import java.sql.SQLException;
import java.text.ParseException;

public class Superviseur {

	public static void main(String[] args) throws SQLException, ParseException {

		RequetesBDSuperviseur r = new RequetesBDSuperviseur();
		Connexion connexion1 = new Connexion();

		String saisie = "";
		while (!saisie.equals("0")) {
			System.out
			.println("******************Superviseur******************");
			System.out
			.println("Bonjour ! Bienvenue dans l'application VEPICK:"
					+ "\n\nVeuillez choisir une option:"
					+ "\n1: Consulter routines"
					+ "\n2: Consulter vélos"
					+ "\n3: Consulter/Editer plages horaires"
					+ "\n4: Consulter informations stations "
					+ "\n5: Editer/Modifier routine routine" + "\n(0: Quitter)\n");

			System.out.flush();
			int i = LectureClavier.lireEntier(saisie);

			switch (i) {
			case 0:
				System.exit(0);
				break;
			case 1:
				System.out.println("Voici les routines de la base:");
				System.out.flush();
				RequetesBDSuperviseur.afficherRoutine(connexion1.getConn());
				System.out.println("\n\nChoisir option:"
						+ "\n1: Détailler routine"
						+ "\n2: Consulter avancement routine vehicule");
				System.out.flush();
				if (LectureClavier.lireChaine().equals("1")) {
					System.out
					.println("Détailler routine (saisir numero routine ou '0' pour terminer): ");
					System.out.flush();
					int numRoutine = LectureClavier.lireEntier(saisie);
					if (numRoutine != 0) {
						RequetesBDSuperviseur.afficherActions(connexion1.getConn(),
								numRoutine);
					}
				} else {
					System.out
					.println("Avancement routine vehicule (saisir numero vehicule ou '0' pour terminer): ");
					System.out.flush();
					int immatriculation = LectureClavier.lireEntier(saisie);
					if (immatriculation != 0) {
						RequetesBDSuperviseur.afficherActionCourante(connexion1.getConn(),
								immatriculation);
					}
				}
				break;
			case 2:
				System.out.println("Voici les stations de la base:");
				System.out.flush();
				RequetesBDSuperviseur.afficherStation(connexion1.getConn());
				System.out.println("\n\nChoisir option:"
						+ "\n1: Consulter le nombre de velos d'une station"
						+ "\n2: Consulter nombre velos endommages d'une station"
						+ "\n3: Consulter nombre de places libres d'une station");
				System.out.flush();
				String choix = LectureClavier.lireChaine();
				if (choix.equals("1")) {
					System.out
					.println("Nombre velos station (saisir numero station ou '0' pour terminer): ");
					System.out.flush();
					int numStation = LectureClavier.lireEntier(saisie);
					if (numStation != 0) {
						RequetesBDSuperviseur.afficherNbVeloStation(connexion1.getConn(),numStation);
					}
				}
				if(choix.equals("2"))
				{
					System.out
					.println("Nombre velos endommages (saisir numero station ou '0' pour terminer): ");
					System.out.flush();
					int numStation = LectureClavier.lireEntier(saisie);
					if (numStation != 0) {
						RequetesBDSuperviseur.afficherNbVeloStationEndommage(connexion1.getConn(),numStation);
					}	
				}
				if(choix.equals("3"))
				{
					System.out
					.println("Nombre de places libres(saisir numero station ou '0' pour terminer): ");
					System.out.flush();
					int numStation = LectureClavier.lireEntier(saisie);
					if (numStation != 0) {
						RequetesBDSuperviseur.afficherNbVeloStationLibres(connexion1.getConn(),numStation);
					}	
				}
				break;
			case 3:
				System.out.println("Voici les plages horaires de la base:");
				System.out.flush();
				RequetesBDSuperviseur.afficherPlagesHoraires(connexion1.getConn());	
				System.out.println("\n\n Choisir option:"
						+ "\n1: Créer plage horaire"
						+ "\n2: Modifier plage horaire");
				System.out.flush();
				String choix3 = LectureClavier.lireChaine();
				if (choix3.equals("1")) {
					System.out
					.println("Numero de station ");
					System.out.flush();
					int numStation = LectureClavier.lireEntier(saisie);
					System.out.println("Date/heure debut (JJ/MM/YYYY HH:mm): ");
					System.out.flush();
					String dateDebut = LectureClavier.lireChaine();
					System.out.println("Date/heure fin (JJ/MM/YYYY HH:mm): ");
					System.out.flush();
					String dateFin = LectureClavier.lireChaine();
					System.out.println("Etat station: ");
					System.out.flush();
					String etatStation = LectureClavier.lireChaine();
					RequetesBDSuperviseur.creerPlagehoraire(connexion1.getConn(), numStation, dateDebut+"00"
							, dateFin+"00", etatStation);
				}
				if (choix3.equals("2")) {
					System.out
					.println("Plage horaire à modifier (saisir numero plage horaire ou '0' pour terminer): ");
					System.out.flush();
					int numPlage = LectureClavier.lireEntier(saisie);
					if (numPlage != 0) {
						System.out
						.println("Numero de station ");
						System.out.flush();
						int numStation = LectureClavier.lireEntier(saisie);
						System.out.println("Date debut (JJ/MM/YYYY): ");
						System.out.flush();
						String dateDebut = LectureClavier.lireChaine();
						System.out.println("Date fin (JJ/MM/YYYY): ");
						System.out.flush();
						String dateFin = LectureClavier.lireChaine();
						System.out.println("Etat station: ");
						System.out.flush();
						String etatStation = LectureClavier.lireChaine();
						RequetesBDSuperviseur.editerPlagehoraire(connexion1.getConn(),numPlage, numPlage, dateDebut+"00", dateFin+"00", etatStation);
					}
				}

				break;
			case 4:
				System.out.println("Voici les stations de la base:");
				System.out.flush();
				RequetesBDSuperviseur.afficherStation(connexion1.getConn());
				System.out.println("\n\n Choisir option:"
						+ "\n1: Consulter rapport station/routine"
						+ "\n2: Consulter nombre velo dans vehicule");

				System.out.flush();
				String choix2 = LectureClavier.lireChaine();
				if (choix2.equals("1")) {
					System.out
					.println("Action pour station (saisir numero station ou '0' pour terminer): ");
					System.out.flush();
					int numStation = LectureClavier.lireEntier(saisie);
					if (numStation != 0) {
						RequetesBDSuperviseur.afficherActionRoutine(connexion1.getConn(), Integer.toString(numStation));
					}
				}
				if(choix2.equals("2"))
				{
					System.out
					.println("Nombre velos dans vehicule (saisir numero vehicule ou '0' pour terminer): ");
					System.out.flush();
					int numVehicule = LectureClavier.lireEntier(saisie);
					if (numVehicule != 0) {
						RequetesBDSuperviseur.afficherNbVeloVehicule(connexion1.getConn(),numVehicule);
					}	
				}
				break;
			case 5:
				System.out.flush();
				System.out.println("Veuillez sélectionner une option:\n"
						+ "\n1: Créer une routine"
						+ "\n2: Modifier une routine");
				System.out.flush();
				int saisie5 = LectureClavier.lireEntier(saisie);
				if (saisie5 == 1) {
					System.out.println("Numéro Superviseur: ");
					System.out.flush();
					int numSuperviseur = LectureClavier.lireEntier(saisie);
					System.out.println("Immatriculation véhicule: ");
					System.out.flush();
					int immatriculationVehicule = LectureClavier
							.lireEntier(saisie);
					System.out.println("Date routine (dd/mm/yyyy): ");
					System.out.flush();
					String dateRoutine = LectureClavier.lireChaine();
					RequetesBDSuperviseur.insertRoutine(connexion1.getConn(),
							numSuperviseur, immatriculationVehicule,
							dateRoutine);

				} else if (saisie5 == 2) {
					System.out
					.println("Veuillez saisir numéro de routine à éditer: ");
					System.out.flush();
					int numRoutine = LectureClavier.lireEntier(saisie);
					System.out.println("Numéro action: ");
					System.out.flush();
					int numAction = LectureClavier.lireEntier(saisie);
					System.out.println("Lieu Action: ");
					System.out.flush();
					String lieuAction = LectureClavier.lireChaine();
					System.out.println("Commentaire Action: ");
					System.out.flush();
					String commentaireAction = LectureClavier.lireChaine();
					System.out.println("Etat Action: ");
					System.out.flush();
					String etatAction = LectureClavier.lireChaine();
					System.out.println("Notification Action: ");
					System.out.flush();
					String notificationAction = LectureClavier.lireChaine();
					System.out.println("Priorite Action: ");
					System.out.flush();
					int prioriteAction = LectureClavier.lireEntier(saisie);
					System.out.println("Nombre deplacements: ");
					System.out.flush();
					int nbDeplacement = LectureClavier.lireEntier(saisie);
					System.out.println("Lieu Action: ");
					System.out.flush();
					String lieuActionArrive = LectureClavier.lireChaine();
					RequetesBDSuperviseur.insertAction(connexion1.getConn(),
							numRoutine, lieuAction, commentaireAction,
							etatAction, notificationAction, prioriteAction,
							nbDeplacement, lieuActionArrive);

				}
				break;
			}
		}
	}
}

