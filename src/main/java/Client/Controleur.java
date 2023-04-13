package Client;

import server.models.Course;

import java.io.IOException;
import java.util.ArrayList;

public class Controleur {
	private Modele modele;
	ArrayList<Course> courses = new ArrayList<>();

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public Controleur(Modele modele) {
		this.modele = modele;
	}
	public void charger(String session) throws IOException {
		this.modele.getCourses(session);
		this.courses = this.modele.getCourse();
	}

	public void inscripting(String prenom, String nom, String email, String matricule,Course chosenClass){
		this.modele.getInscription(prenom,nom,email,matricule,chosenClass);
	}
}
