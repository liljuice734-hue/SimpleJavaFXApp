package com.example.hellofx;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class HelloJavaFX extends Application {


    private final ObservableList<Customer> customers =
            FXCollections.observableArrayList();


    @Override
    public void start(Stage stage) {


        // -------------------------
        // INPUT CONTROLS
        // -------------------------


        Label nameLabel = new Label("Customer Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter customer name");
        nameLabel.setLabelFor(nameField);


        Label provinceLabel = new Label("Province:");
        ComboBox<String> provinceBox = new ComboBox<>();


        provinceBox.getItems().addAll(
                "Central",
                "Copperbelt",
                "Eastern",
                "Luapula",
                "Lusaka",
                "Muchinga",
                "Northern",
                "North-Western",
                "Southern",
                "Western"
        );


        provinceBox.setPromptText("Choose a province");
        provinceLabel.setLabelFor(provinceBox);


        // -------------------------
        // BUTTONS
        // -------------------------


        Button saveButton = new Button("Save Customer");
        Button deleteButton = new Button("Delete Selected");


        // -------------------------
        // STATUS MESSAGE
        // -------------------------


        Label status = new Label("Enter customer details.");


        // -------------------------
        // TABLE
        // -------------------------


        TableView<Customer> table = new TableView<>();


        TableColumn<Customer, String> nameColumn =
                new TableColumn<>("Customer Name");


        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );


        TableColumn<Customer, String> provinceColumn =
                new TableColumn<>("Province");


        provinceColumn.setCellValueFactory(
                new PropertyValueFactory<>("province")
        );


        table.getColumns().addAll(nameColumn, provinceColumn);
        table.setItems(customers);


        nameColumn.setPrefWidth(250);
        provinceColumn.setPrefWidth(200);


        // -------------------------
        // SAVE CUSTOMER
        // -------------------------


        saveButton.setOnAction(event -> {


            String name = nameField.getText().trim();


            // Validate name
            if (name.isEmpty()) {
                status.setText("Enter the customer name.");
                nameField.requestFocus();
                return;
            }


            // Validate province
            String province = provinceBox.getValue();


            if (province == null) {
                status.setText("Choose a province.");
                provinceBox.requestFocus();
                return;
            }


            // Create and add customer
            customers.add(new Customer(name, province));


            status.setText("Customer saved.");


            // Clear only after successful save
            nameField.clear();
            provinceBox.setValue(null);
            nameField.requestFocus();
        });


        // -------------------------
        // DELETE CUSTOMER
        // -------------------------


        deleteButton.setOnAction(event -> {


            Customer selected =
                    table.getSelectionModel().getSelectedItem();


            if (selected == null) {
                status.setText("Select a customer first.");
                return;
            }


            ButtonType delete =
                    new ButtonType("Delete");


            Alert confirmation = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Delete the selected customer?",
                    delete,
                    ButtonType.CANCEL
            );


            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Delete Customer");


            if (confirmation.showAndWait()
                    .orElse(ButtonType.CANCEL) == delete) {


                customers.remove(selected);
                status.setText("Customer deleted.");
            }
        });


        // -------------------------
        // FORM LAYOUT
        // -------------------------


        GridPane form = new GridPane();


        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(15));


        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);


        form.add(provinceLabel, 0, 1);
        form.add(provinceBox, 1, 1);


        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(saveButton, deleteButton);


        VBox topSection = new VBox(10);
        topSection.setPadding(new Insets(10));


        Label title = new Label("Customer Manager");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");


        topSection.getChildren().addAll(title, form, buttons, status);


        // Pressing Enter can activate Save
        saveButton.setDefaultButton(true);


        // -------------------------
        // MAIN LAYOUT
        // -------------------------


        BorderPane root = new BorderPane();


        root.setTop(topSection);
        root.setCenter(table);


        BorderPane.setMargin(
                table,
                new Insets(10)
        );


        Scene scene = new Scene(root, 650, 500);


        stage.setTitle(
                "ICT261 Customer Manager - Student No: 202500305"
        );


        stage.setScene(scene);
        stage.show();


        // Put cursor in name field when application opens
        nameField.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}