package Client;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.models.Course;

import java.io.IOException;

public class Vue extends Application {
    Course chosenClass;
    @Override
    public void start(Stage stage) throws Exception {

        Controleur controleur = new Controleur(new Modele());
        try {

            //MAIN WINDOW
            Pane root = new Pane();
            Scene scene = new Scene(root, 600, 500);
            root.setStyle("-fx-background-color: #f5f5dc;");
            Separator mainSeperator = new Separator();
            mainSeperator.setOrientation(Orientation.VERTICAL);
            root.getChildren().add(mainSeperator);
            mainSeperator.setLayoutX(root.getWidth() / 2);
            mainSeperator.setPrefHeight(root.getHeight());

            //BOTTOM BOX FOR BUTTONS
            HBox bottomBox = new HBox();
            bottomBox.setPrefHeight(50);
            bottomBox.setPrefWidth(280);
            bottomBox.setSpacing(50);
            bottomBox.setAlignment(Pos.CENTER);
            root.getChildren().add(bottomBox);
            bottomBox.setLayoutX(10);
            bottomBox.setLayoutY(440);
            bottomBox.setStyle("-fx-background-color: #FFFFFF;");

            ObservableList<String> session = FXCollections.observableArrayList("Automne", "Hiver", "Ete");
            ChoiceBox<String> lesSessions = new ChoiceBox<>(session);
            lesSessions.setValue(session.get(0));
            Button charge = new Button("Charger");
            bottomBox.getChildren().addAll(lesSessions, charge);

            //TITLE FOR TABLE VIEW
            Text tableViewTitle = new Text("Liste de cours");
            root.getChildren().add(tableViewTitle);
            tableViewTitle.setFont(Font.font("serif", 18));
            tableViewTitle.setLayoutX(95);
            tableViewTitle.setLayoutY(25);

            //GRIDPANE FOR INSCRIPTION
            GridPane inscriptionForm = new GridPane();
            Text text1 = new Text("Prénom");
            Text text2 = new Text("Nom");
            Text text3 = new Text("Email");
            Text text4 = new Text("Matricule");


            javafx.scene.control.TextField prenom1 = new javafx.scene.control.TextField();
            javafx.scene.control.TextField nom1 = new javafx.scene.control.TextField();
            javafx.scene.control.TextField email1 = new javafx.scene.control.TextField();
            javafx.scene.control.TextField matricule1 = new javafx.scene.control.TextField();

            Button envoyer = new Button("Envoyer");

            //GRIDPANE INSCRIPTION LAYOUT
            inscriptionForm.setMinSize(250, 300);
            inscriptionForm.setPadding(new Insets(10, 10, 10, 10));
            inscriptionForm.setHgap(10);
            inscriptionForm.setVgap(15);
            inscriptionForm.setAlignment(Pos.CENTER);
            inscriptionForm.add(text1, 0, 0);
            inscriptionForm.add(prenom1, 1, 0);
            inscriptionForm.add(text2, 0, 1);
            inscriptionForm.add(nom1, 1, 1);
            inscriptionForm.add(text3, 0, 2);
            inscriptionForm.add(email1, 1, 2);
            inscriptionForm.add(text4, 0, 3);
            inscriptionForm.add(matricule1, 1, 3);
            inscriptionForm.add(envoyer, 1, 4);
            root.getChildren().add(inscriptionForm);
            inscriptionForm.setLayoutX(310);
            inscriptionForm.setLayoutY(15);


			//INSCRIPTION TITLE
			Text inscriptionTitre = new Text("Formulaire d'inscription");
			root.getChildren().add(inscriptionTitre);
			inscriptionTitre.setFont(Font.font("serif",18));
			inscriptionTitre.setLayoutX(360);
			inscriptionTitre.setLayoutY(25);

			//THIS IS COURS TABLE TO SHOW USER USING TABLEVIEW
			TableView<Course> choixDeCours = new TableView<>();
			TableColumn<Course, String> codeCol = new TableColumn<>("Code");
			TableColumn<Course, String> coursCol = new TableColumn<>("Cours");
			choixDeCours.getColumns().addAll(codeCol,coursCol);
			choixDeCours.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			choixDeCours.setLayoutX(25);
			choixDeCours.setLayoutY(30);
			codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
			coursCol.setCellValueFactory(new PropertyValueFactory<>("name"));
			root.getChildren().add(choixDeCours);


			//CHARGER BUTTON ACTION
			charge.setOnAction((action) -> {
				try {
					controleur.charger(lesSessions.getValue());
					ObservableList<Course> courses = FXCollections.observableArrayList(controleur.getCourses());
					choixDeCours.setItems(courses);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});

			//TABLE VIEW ACTION WHEN USER SELECT A CLASS
			choixDeCours.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					String selectedName = newSelection.getName();
					String selectedCode = newSelection.getCode();
					System.out.println("Selected class: " + selectedName + ", " + selectedCode);
					for (int i = 0; i < controleur.getCourses().size(); i++) {
						if (controleur.getCourses().get(i).getCode().equals(selectedCode)){
							this.chosenClass = controleur.getCourses().get(i);
						}
					}
				}
			});

			//ENVOYER BUTTON ACTION
			envoyer.setOnAction((action) -> {
				try{
					String prenom = prenom1.getText();
					String nom = nom1.getText();
					String email = email1.getText();
					String matricule = matricule1.getText();
					System.out.println(controleur.getCourses());
					System.out.println(chosenClass);
					System.out.println(prenom);
					System.out.println(nom);

					controleur.inscripting(prenom,nom,email,matricule,this.chosenClass);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
			stage.setTitle("Inscription UdeM");
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	public static void main(String[] args){
		launch(args);
	}
}
