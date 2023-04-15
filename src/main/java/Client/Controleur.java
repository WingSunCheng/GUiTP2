package Client;

import server.models.Course;

import java.io.IOException;
import java.util.ArrayList;

/** Cette classe est le contrôleur pour contrôler la logique derrière le modèle du programme.
 */
public class Controleur {
	private Modele modele;
	ArrayList<Course> courses = new ArrayList<>();

	/**
	 * Cette méthode obtient la ArrayList of Course
	 * <p>
	 * Cours @return ArrayList courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

	/**
	 * cela construit un nouveau Controleur avec le modèle
	 * <p>
	 * @param modele modèle pour le Controleur
	 */
	public Controleur(Modele modele) {
		this.modele = modele;
	}

	/**
	 * Cette méthode permet de filtrer les cours en fonction de la session choisie par l'utilisateur
	 * <p>
	 * @param session session pour filtrer les cours
	 * @throws IOException erreur d'obtention des cours du modèle
	 */
	public void charger(String session) throws IOException {
		this.modele.getCourses(session);
		this.courses = this.modele.getCourse();
	}

	/**
	 * Cette méthode appelle la méthode getInscription du modèle pour s'inscrire à un cours
	 *
	 * @param prenom Nom de l'utilisateur
	 * @param nom Prénom de l'utilisateur
	 * @param email Email de l'utilisateur
	 * @param matricule Matricule utilisateur
	 * @param chosenClass class selectioné par l'utilisateur
	 */
	public void inscripting(String prenom, String nom, String email, String matricule,Course chosenClass){
		this.modele.getInscription(prenom,nom,email,matricule,chosenClass);
	}
}
