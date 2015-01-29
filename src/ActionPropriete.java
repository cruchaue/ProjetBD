
public class ActionPropriete {

	
	private int numAction;
	private int numRoutine;
	private String lieu;
	private String commentaire;
	private String etat;
	private String notification;
	private int priorite;
	private int nbDeplacement;
	private String lieu_arrive;
	
	
	public ActionPropriete(int numAction, int numRoutine, String lieu,
			String commentaire, String etat, String notification, int priorite,
			int nbDeplacement, String lieu_arrive) {
		
		this.numAction = numAction;
		this.numRoutine = numRoutine;
		this.lieu = lieu;
		this.commentaire = commentaire;
		this.etat = etat;
		this.notification = notification;
		this.priorite = priorite;
		this.nbDeplacement = nbDeplacement;
		this.lieu_arrive = lieu_arrive;
	}
	
	public int getNumAction() {
		return numAction;
	}
	public void setNumAction(int numAction) {
		this.numAction = numAction;
	}
	public int getNumRoutine() {
		return numRoutine;
	}
	public void setNumRoutine(int numRoutine) {
		this.numRoutine = numRoutine;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public int getPriorite() {
		return priorite;
	}
	public void setPriorite(int priorite) {
		this.priorite = priorite;
	}
	public int getNbDeplacement() {
		return nbDeplacement;
	}
	public void setNbDeplacement(int nbDeplacement) {
		this.nbDeplacement = nbDeplacement;
	}
	public String getLieu_arrive() {
		return lieu_arrive;
	}
	public void setLieu_arrive(String lieu_arrive) {
		this.lieu_arrive = lieu_arrive;
	}
	
	
	
}
