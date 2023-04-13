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

public class Modele {
    ArrayList<Course> course = new ArrayList<>();

    public ArrayList<Course> getCourse() {
        return course;
    }

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
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void getInscription(String prenom, String nom, String email, String matricule,Course chosenClass) {
        try {
            Socket BONJOUR = new Socket("127.0.0.1", 1337);
            ObjectOutputStream BYEBYE = new ObjectOutputStream(BONJOUR.getOutputStream());
            BYEBYE.writeObject("INSCRIRE");
            BYEBYE.flush();
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
                if (matricule.length() != 8) {
                    validMatricule = false;
                }
                if (validMatricule == false){
                    throw new Exception("Invalid matricule");
                }

            } catch (Exception e) {
                errorWindows("Invalid Matricule","Wrong matricule (8 numbers)!");
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
                Socket kek = new Socket("127.0.0.1", 1337);
                ObjectOutputStream kik = new ObjectOutputStream(kek.getOutputStream());
                System.out.println(ins);
                kik.writeObject(ins);
                kik.flush();

                Stage INSCRIT = new Stage();
                Pane nice = new Pane();
                Scene nice2 = new Scene(nice, 480, 200);
                Text txt = new Text("Félicitations, vous etes inscrit(e) avec succès au cours: " + chosenClass.getCode());
                txt.setFont(Font.font("serif", 18));
                txt.setLayoutX(10);
                txt.setLayoutY(100);
                nice.getChildren().add(txt);
                INSCRIT.setScene(nice2);
                INSCRIT.setTitle("GOODJOB");
                INSCRIT.show();
                System.out.println("BYE");
                kek.close();
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

