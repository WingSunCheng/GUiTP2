package Client;

import server.models.Course;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

}
