import Client.Vue;

public class Main {
	/**
	 * Cette méthode appelle simplement la classe qui étend l'application pour pouvoir créer un jar fonctionnel
	 * sans avoir à ajouter la bibliothèque javaFx lors de l'exécution en ligne de commande
	 *
	 * @param args argument de ligne de commande mais est ignoré,uniquement utilisé
	 *                pour pouvoir appeler la méthode main depuis Vue, pour lancer l'application
	 */
	public static void main(String[] args) {
		Vue.main(args);
	}
}
