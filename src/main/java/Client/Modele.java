package Client;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.models.Course;
import server.models.RegistrationForm;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


/**
 * Cette classe Modele est la logique derrière le programme. Il donne la fonctionnalité à l'interface utilisateur graphique
 * pour voir les cours disponibles et pour s'inscrire aux cours.
 */

public class Modele {
    ArrayList<Course> course = new ArrayList<>();

    /**
     * Cette méthode obtient les cours
     * <p>
     * @return ArrayList Course
     */
    public ArrayList<Course> getCourse() {
        return course;
    }

    /**
     * This method will get the available courses depending on the session chosen from the server.
     *
     *<p>
     * @param session String session to get available courses
     * @throws IOException error while sending and receiving information to the server
     */
    public void getCourses(String session) throws IOException {

        //ArrayList<Course> course = new ArrayList<>();
        try {
            Socket cS = new Socket("127.0.0.1", 1337);
            ObjectOutputStream os = new ObjectOutputStream(cS.getOutputStream());
            if (session.equals("Automne")) {
                session = "CHARGER Automne";
            }
            if (session.equals("Hiver")) {
                session = "CHARGER Hiver";
            }
            if (session.equals("Ete")) {
                session = "CHARGER Ete";
            }
            os.writeObject(session);
            os.flush();

            try {
                ObjectInputStream in = new ObjectInputStream(cS.getInputStream());
                this.course = (ArrayList<Course>) in.readObject(); // read the object sent by the server
                in.close();
                System.out.println("Les cours offerts pendant la session d'automne sont:");
                for (int i = 0; i < this.course.size(); i++) {
                    System.out.println(i + 1 + "." + this.course.get(i).getCode() + "\t" + this.course.get(i).getName());
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            cS.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This will register a user into a chosen course, but it will also verify the information written by the user
     * to make sure it is compatible before sending it to the server
     * <p>
     * @param prenom prenom de l'utilisateur
     * @param nom nom de l'utilisateur
     * @param email email de l'utilisateur
     * @param matricule matricule de l'utilisateur
     * @param chosenClass class chosis de l'utilisateur
     */
    public void getInscription(String prenom, String nom, String email, String matricule,Course chosenClass) {
        try {
            Socket socket = new Socket("127.0.0.1", 1337);
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.writeObject("INSCRIRE");
            outStream.flush();
            boolean validEmail = true;
            boolean validMatricule = true;
            boolean validCode = true;

            try {
                String mailRegex = "[^@\\s]+@[^@\\s]+\\.\\w+";  //PIAZA NICHOLASCOOPER
                validEmail = email.matches(mailRegex);
                if (validEmail == false){
                    throw new Exception("Invalid email");
                }
            } catch (Exception e) {
                errorWindows("Invalid Email","Proper email format please!");
            }
            try {
                validMatricule = true;
                if (matricule.length() != 6) {
                    validMatricule = false;
                }
                if (validMatricule == false){
                    throw new Exception("Invalid matricule");
                }

            } catch (Exception e) {
                errorWindows("Invalid Matricule","Wrong matricule (6 numbers)!");
            }
            try {
                validCode = true;
                if (chosenClass == null) {
                    validCode = false;
                }
                if (validCode == false){
                    throw new Exception("Invalid code");
                }
            } catch (Exception e) {
                errorWindows("Invalid Code","Please choose a class");
            }

            if(!((validCode == false) || (validMatricule == false) || (validEmail == false))) {
                RegistrationForm ins = new RegistrationForm(prenom, nom, email, matricule, chosenClass);
                Socket socket2 = new Socket("127.0.0.1", 1337);
                ObjectOutputStream outStream2 = new ObjectOutputStream(socket2.getOutputStream());
                System.out.println(ins);
                outStream2.writeObject(ins);
                outStream2.flush();

                Stage INSCRIT = new Stage();
                Pane pane = new Pane();
                Scene pane2 = new Scene(pane, 480, 200);
                Text txt = new Text("Félicitations, vous etes inscrit(e) avec succès au cours: " + chosenClass.getCode());
                txt.setFont(Font.font("serif", 18));
                txt.setLayoutX(10);
                txt.setLayoutY(100);
                pane.getChildren().add(txt);
                INSCRIT.setScene(pane2);
                INSCRIT.setTitle("GOODJOB");
                INSCRIT.show();
                System.out.println("BYE");
                socket2.close();
            }
            else {
                return;
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This will create an error window when the user sends the wrong information with a title and a message
     * indicating the error
     * <p>
     * @param title title of the error window
     * @param message message of the error window
     */
    public void errorWindows(String title,String message){
        Stage errorWindow = new Stage();
        Pane r2 = new Pane();
        Scene s2 = new Scene(r2, 500, 400);
        r2.setStyle("-fx-background-color: #FF0000;");
        Text txt = new Text(message);
        r2.getChildren().add(txt);
        txt.setLayoutX(130);
        txt.setLayoutY(200);
        txt.setFont(Font.font("verdana", FontWeight.BOLD,20));
        txt.setFill(Color.GHOSTWHITE);
        errorWindow.setScene(s2);
        errorWindow.setTitle(title);
        errorWindow.show();
    }
}

