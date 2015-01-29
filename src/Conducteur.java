import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;


public class Conducteur {

	private static int immat = 0;

	public static void main(String[] args) throws SQLException, ParseException,
			java.text.ParseException {
		// TODO Auto-generated method stub

		Connexion tutu = new Connexion();

		ArrayList TabMission = afficherAction(tutu.getConn());
		int countMission = TabMission.size();

		for (int k = 0; k < countMission; k++) {

			ActionPropriete commentaireAValider = (ActionPropriete) TabMission
					.get(k);
			System.out.println("La mission a valider est la suivante : \n");
			System.out.println(commentaireAValider.getCommentaire());
			String valid = "";
			String raisonRefus = "";
			System.out.println("Peut-elle commencer ? (yes/no)\n");
			System.out.flush();
			valid = LectureClavier.lireChaine();

			if (valid.equalsIgnoreCase("yes")) {
				parseAction(commentaireAValider, tutu.getConn());
			} else if (valid.equalsIgnoreCase("no")) {

				System.out.println("veuillez remplir une explication :");
				System.out.flush();
				raisonRefus = LectureClavier.lireChaine();
				System.out.println("Saisissez l'erreur : ");
				System.out.flush();
				String raisonErreur = LectureClavier.lireChaine();
				RequetesVehicule.modifierEtatNotificationAction(tutu.getConn(),
						raisonErreur, commentaireAValider.getNumAction());

			} else {

				System.out.println("erreur saisie !");

			}

		}

		System.out.println("Fin de la routine");

	}

	public static ArrayList afficherAction(Connection connection)
			throws SQLException {

		String saisie = "";
		System.out
				.println("bonjour, veuillez saisir l'immatriculation du véhicule : ");
		System.out.flush();
		immat = LectureClavier.lireEntier(saisie);

		ArrayList TabMission = new ArrayList();

		String format = "dd-MM-yyyy";
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
		java.util.Date date = new java.util.Date();
		// System.out.println(formater.format(date));

		TabMission = RequetesVehicule.afficherAction(connection, immat,formater.format(date));
		System.out.println("\n listes des missions du jour : ");

		for (int i = 0; i < TabMission.size(); i++) {
			ActionPropriete listeCommentaire = (ActionPropriete) TabMission.get(i);
			System.out.println(listeCommentaire.getCommentaire());
		}
		return TabMission;
	}

	public static void parseAction(ActionPropriete action, Connection connection)
			throws SQLException, ParseException, java.text.ParseException {

		String cas1 = "Embarquer velo";
		String cas2 = "Reparer bornette";
		String cas3 = "Verifier dispo";
		String cas4 = "Reparer velo";
		String cas5 = "Debarquer velo";

		String saisie = "";
		String saisie2 = "";
		if (action.getCommentaire().toLowerCase().contains(cas1.toLowerCase())) {
			// recuper id
			System.out
					.println("voici la liste des vélos que vous pouvez embarquer : ");
			String listeVelo = RequetesVehicule.afficherVeloDispo(connection,
					action.getLieu());
			System.out.println(listeVelo);
			System.out.println("Vous devez en prendre : "
					+ action.getNbDeplacement());
			ArrayList veloEmbarque = new ArrayList();
			for (int l = 0; l < action.getNbDeplacement(); l++) {

				System.out
						.println("veuillez saisir le numéro du vélo embarqué parmis la liste : ");
				System.out.flush();
				int numBornette = LectureClavier.lireEntier(saisie);
				veloEmbarque.add(numBornette);
			}
			// veloEmbarque
			RequetesVehicule.embarquerVelo(connection, veloEmbarque, action, immat);
			System.out.println("les velos sont bien dans le véhicule");
			RequetesVehicule.modifierEtatAction(connection, action.getNumAction());
			saisie = "";
		} else if (action.getCommentaire().toLowerCase()
				.contains(cas2.toLowerCase())) {
			// reparer velo
			int nbVeloReparation = 0;
			nbVeloReparation = action.getNbDeplacement();
			for (int i = 0; i < nbVeloReparation; i++) {
				System.out
						.println("veuillez saisir le numéro de la bornette réparée : ");
				System.out.flush();
				int numBornette = LectureClavier.lireEntier(saisie);
				RequetesVehicule.modifierEtatBornette(connection, action.getLieu(),
						numBornette);
			}
			System.out.println("action finie ! ");
			RequetesVehicule.modifierEtatAction(connection, action.getNumAction());
			saisie = "";

		} else if (action.getCommentaire().toLowerCase()
				.contains(cas3.toLowerCase())) {
			// envoyer notification superviseur
			System.out.println("Verifier la présence de "
					+ action.getNbDeplacement() + "vélos dans la station "
					+ action.getLieu() + ". \n");
			System.out.println("Sont-ils tous présents ? (yes/no) ");
			System.out.flush();
			String validPresence = LectureClavier.lireChaine();
			if (validPresence.equals("yes")) {
				RequetesVehicule
						.modifierEtatAction(connection, action.getNumAction());
			} else {
				System.out.println("Saisissez l'erreur : ");
				System.out.flush();
				String raisonErreur = LectureClavier.lireChaine();
				RequetesVehicule.modifierEtatNotificationAction(connection,
						raisonErreur, action.getNumAction());
			}
		} else if (action.getCommentaire().toLowerCase()
				.contains(cas4.toLowerCase())) {
			// velo reparation

			int nbVeloReparation = 0;
			nbVeloReparation = action.getNbDeplacement();
			for (int i = 0; i < nbVeloReparation; i++) {
				System.out
						.println("veuillez saisir le numéro du vélo réparé : ");
				System.out.flush();
				int numVelo = LectureClavier.lireEntier(saisie2);
				RequetesVehicule.modifierEtatVelo(connection, action.getLieu(),
						numVelo);
			}
			System.out.println("action finie ! ");
			RequetesVehicule.modifierEtatAction(connection, action.getNumAction());
			saisie = "";
			// identifiant de la bornette a reparer

		} else if (action.getCommentaire().toLowerCase()
				.contains(cas5.toLowerCase())) {
			System.out.println("Vous devez déposer "
					+ action.getNbDeplacement() + " vélos à la station "
					+ action.getLieu() + ".\n");

			String listeBornette = RequetesVehicule.listeBornetteVide(connection,
					Integer.parseInt(action.getLieu()));
			System.out.println("liste des bornettes vides :");
			System.out.println(listeBornette);

			System.out.println("combien de vélo pouvez-vous déposer ?");
			System.out.flush();
			int nbVelo = LectureClavier.lireEntier(saisie);

			System.out.println("liste des vélos dans le véhicule : ");
			String listeVeloVehicule = RequetesVehicule.listerVeloVehicule(
					connection, immat);
			System.out.println(listeVeloVehicule);
			System.out
					.println("Saisisser les identifiants des vélos a déposer");
			ArrayList veloVehiculeDepose = new ArrayList();
			ArrayList veloVehiculeDeposeBornette = new ArrayList();
			for (int i = 0; i < nbVelo; i++) {
				System.out.println("vélo : ");
				System.out.flush();
				veloVehiculeDepose.add(LectureClavier.lireEntier(saisie));
				System.out.println("à la bornette : ");
				System.out.flush();
				veloVehiculeDeposeBornette.add(LectureClavier.lireEntier(saisie));
			}

			System.out.println(veloVehiculeDepose);
			System.out.println(veloVehiculeDeposeBornette);
			for (int j = 0; j < veloVehiculeDepose.size(); j++) {
				RequetesVehicule.debarquerVelo(connection, veloVehiculeDepose.get(j),
						veloVehiculeDeposeBornette.get(j), action);
			}

			System.out.println("debarquement fini !");
			RequetesVehicule.modifierEtatAction(connection, action.getNumAction());
		}
	}

}
