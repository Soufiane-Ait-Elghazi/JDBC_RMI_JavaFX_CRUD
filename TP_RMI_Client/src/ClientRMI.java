import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import metier.Etudiant;
import rmi.IEtudiantRemote;


public class ClientRMI extends Application {
	IEtudiantRemote stub ;
	List<Etudiant> etudiants;
	Etudiant etudiant = new Etudiant();

	public static void main(String[] args) {
		Application.launch(ClientRMI.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Gestion Etudiants");
		BorderPane borderPane = new BorderPane();
	
		HBox hbox1 = new HBox();
		hbox1.setPadding(new Insets(10));
		hbox1.setSpacing(10);
		Label labelHost = new Label("Host :");
		TextField textFieldHost = new TextField("localhost");
		textFieldHost.setPromptText("Host");
		Label labelPort = new Label("Port :");
		TextField textFieldPort = new TextField("8888");
		textFieldPort.setPromptText("Port");
		Label labelCode = new Label("Code :");
		TextField textFieldCode = new TextField("SM");
		textFieldCode.setPromptText("Code");
		Button buttonConnecter = new Button("Connecter");
		hbox1.getChildren().addAll(labelHost,textFieldHost,labelPort,textFieldPort,labelCode,textFieldCode,buttonConnecter);
		
		
		VBox vbox1 = new VBox();
		vbox1.setPadding(new Insets(10));
		vbox1.setSpacing(10);
		ObservableList<Etudiant> observableTableEtudiants =  FXCollections.observableArrayList();
		final TableView<Etudiant> etudiantTableView =new TableView<>();
		etudiantTableView.setItems(observableTableEtudiants);
	    TableColumn<Etudiant, Integer> etudiantId =new TableColumn<>("Id");
	    etudiantId.setCellValueFactory( new PropertyValueFactory<Etudiant,Integer>("id"));
	    TableColumn<Etudiant, String> etudiantNom =new TableColumn<>("Nom");
	    etudiantNom.setCellValueFactory( new PropertyValueFactory<Etudiant, String>("nom"));
	    TableColumn<Etudiant, String> etudiantPrenom =new TableColumn<>("Prenom");
	    etudiantPrenom.setCellValueFactory( new PropertyValueFactory<Etudiant, String>("prenom"));
	    etudiantTableView.getColumns().addAll(etudiantId,etudiantNom,etudiantPrenom);
	    TableViewSelectionModel selectionModel =etudiantTableView.getSelectionModel();
		vbox1.getChildren().add(etudiantTableView);
		
		HBox hbox2 = new HBox();
		hbox2.setPadding(new Insets(10));
		hbox2.setSpacing(10);
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		Label labelNom = new Label("Nom :");
		TextField textFieldNom = new TextField();
		textFieldNom.setPromptText("AIT ELGHAZI");
		Label labelPrenom = new Label("Prenom :");
		TextField textFieldPrenom = new TextField();
		textFieldPrenom.setPromptText("Soufiane");
		gridPane.add(labelNom, 0, 1);
		gridPane.add(textFieldNom, 0, 2);
		gridPane.add(labelPrenom, 0, 3);
		gridPane.add(textFieldPrenom, 0, 4);
		Button buttonGetAllStudents = new Button("Etudiants");
		Button buttonDelete = new Button("delete");
		Button buttonEdit = new Button("Modifier");
		Button buttonAddEtudiant = new Button("Ajouter");
		gridPane.add(buttonGetAllStudents, 1, 1);
		gridPane.add( buttonDelete, 1, 2);
		gridPane.add(buttonEdit, 1, 3);
		gridPane.add(buttonAddEtudiant, 1, 4);
		TextField textFieldSearch = new TextField();
		Button buttonSearch = new Button("Chercher");
		gridPane.add( textFieldSearch, 2, 2);
		gridPane.add(buttonSearch, 2, 3);
		
		hbox2.getChildren().addAll(gridPane);
		
		
		borderPane.setTop(hbox1);
		borderPane.setCenter(vbox1);
		borderPane.setBottom(hbox2);
		Scene scene = new Scene(borderPane,1100,400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	
		
		
		
		buttonConnecter.setOnAction(ev->{
			try {
				String host = textFieldHost.getText();
				int port =Integer.parseInt(textFieldPort.getText());
				String service = textFieldCode.getText();
				stub = (IEtudiantRemote) Naming.lookup("rmi://"+host+":"+port+"/"+service);
				hbox1.setDisable(true);
				} catch (MalformedURLException | RemoteException | NotBoundException ex) {
					ex.printStackTrace();
				}
		});
		
		buttonGetAllStudents.setOnAction(ev->{
			try {
				etudiantTableView.getItems().clear();
				 etudiants = stub.listAllEtudiants();
				 etudiants.forEach(e->{
						 observableTableEtudiants.add(e);
				 });
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
		
		buttonAddEtudiant.setOnAction(ev->{
			try {
				String nom = textFieldNom.getText();
				String prenom = textFieldPrenom.getText();
				etudiant.setNom(nom);
				etudiant.setPrenom(prenom);
				stub.ajouterEtudiant(etudiant);
				buttonGetAllStudents.fire();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
		
		buttonDelete.setOnAction(ev->{
			ObservableList<Etudiant>  selectedItems = selectionModel.getSelectedItems();
			stub.supprimerEtudiant( selectedItems.get(0).getId());
			buttonGetAllStudents.fire();
			
		});
		
		buttonSearch.setOnAction(ev->{
			try {
				String s = textFieldSearch.getText();
				etudiants =stub.listEtudiantskeyWord(s);
				observableTableEtudiants.clear();
				observableTableEtudiants.addAll(etudiants);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
		
		buttonEdit.setOnAction(ev->{
			try {
				ObservableList<Etudiant>  selectedItems = selectionModel.getSelectedItems();
				Etudiant e = new Etudiant();
				e.setNom(textFieldNom.getText());
				e.setPrenom(textFieldPrenom.getText());
				e = stub.modifierEtudiant(selectedItems.get(0).getId(), e);
				buttonGetAllStudents.fire();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
		
	}
}
