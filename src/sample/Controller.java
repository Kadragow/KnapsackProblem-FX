package sample;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.util.StringConverter;
import pro.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    List<Item> itemList;
    Instance instance;

    @FXML
    private TableView<Object> itemsTable;

    @FXML
    private TableView<Object> itemsTable1;

    @FXML
    private Label currentItemsLabel;

    @FXML
    private TableColumn<Object, String> weighColumn;

    @FXML
    private TableColumn<Object, String> weighColumn1;

    @FXML
    private MenuItem closeMenu;

    @FXML
    private ImageView picture;

    @FXML
    private MenuItem aboutMenu;

    @FXML
    private Label dateLabel;

    @FXML
    private TextField backpackSizeField;

    @FXML
    private TextField itemWeightField;

    @FXML
    private TextField itemValueField;

    @FXML
    private TableColumn<Object, String> valueColumn;

    @FXML
    private TableColumn<Object, String> valueColumn1;

    @FXML
    private MenuItem languageMenu;

    @FXML
    private ComboBox<Algorithm> algorithmComboBox;

    @FXML
    private Button runButton;

    @FXML
    private Button addButton;

    @FXML
    private Label label4;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label5;

    @FXML
    private Label label6;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem showInfo;

    private ObservableList<Object> observableList;
    private ListProperty<Object> listProperty;
    private ObservableList<Object> observableList1;
    private ListProperty<Object> listProperty1;
    private ResourceBundle rb;
    private Locale locale;
    private String currentPattern = "yyyy-MM-dd HH:mm:ss";
    private String hiddenString = "Zawartosc plecaka: ";
    private String hiddenString2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        locale = new Locale("pl", "PL");
        this.rb = ResourceBundle.getBundle("sample.lang", locale);
        itemList = new ArrayList<>();
        runButton.setDisable(true);

        algorithmComboBox.setItems(FXCollections.observableArrayList(
                new BruteForce(),
                new Greedy(),
                new RandomAlg()));

        algorithmComboBox.setPromptText("");

        algorithmComboBox.setConverter(new StringConverter<Algorithm>() {
            @Override
            public String toString(Algorithm object) {
                return object.getName();
            }

            @Override
            public Algorithm fromString(String string) {
                return null;
            }
        });

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(currentPattern);
            dateLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        observableList = FXCollections.observableArrayList();
        listProperty = new SimpleListProperty<>();
        listProperty.set(observableList);
        itemsTable.setItems(observableList);
        itemsTable.itemsProperty().bindBidirectional(listProperty);

        observableList1 = FXCollections.observableArrayList();
        listProperty1 = new SimpleListProperty<>();
        listProperty1.set(observableList1);
        itemsTable1.setItems(observableList1);
        itemsTable1.itemsProperty().bindBidirectional(listProperty1);

        valueColumn.setCellValueFactory((new PropertyValueFactory<>("itemValue")));
        weighColumn.setCellValueFactory((new PropertyValueFactory<>("itemWeight")));
        valueColumn1.setCellValueFactory((new PropertyValueFactory<>("itemValue")));
        weighColumn1.setCellValueFactory((new PropertyValueFactory<>("itemWeight")));


    }

    @FXML
    void onSelectCB(ActionEvent event) {
        if (algorithmComboBox.getValue() != null && itemList.size() != 0 && !backpackSizeField.getText().equals(""))
            runButton.setDisable(false);
    }

    @FXML
    void onAddItemAction(ActionEvent event) {
        Item item = new Item(Float.parseFloat(itemValueField.getText()), Integer.parseInt(itemWeightField.getText()));
        itemList.add(item);
        refreshTable();
    }

    @FXML
    void onRunAlgorithmAction(ActionEvent event) {
        List<Item> tmp = new ArrayList<>(itemList);
        instance = new Instance(Integer.parseInt(backpackSizeField.getText()));
        Algorithm alg = algorithmComboBox.getValue();
        alg.solveAlgorithm(tmp, instance);
        observableList1.setAll(instance.getItemList());
        //System.out.print(instance.getCurrentValue() + " " + instance.getCurrentWeight());
    }

    @FXML
    void onMenuLanguagePress(ActionEvent event) {

    }

    @FXML
    void onMenuAboutPress(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE, "Autor: Kamil Błanik, 235629 \nOpis: Program przeznaczony do rozwiązywania problemu plecakowego", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    void onMenuClosePress(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void onPlChoose(ActionEvent event) {
        locale = new Locale("pl", "PL");
        refreshLanguage();
    }

    @FXML
    void onGbChoose(ActionEvent event) {
        locale = new Locale("en", "GB");
        refreshLanguage();
    }

    @FXML
    void onUsaChoose(ActionEvent event) {
        locale = new Locale("en", "US");
        refreshLanguage();
    }

    private void refreshLanguage() {
        //locale = new Locale(lang);
        rb = ResourceBundle.getBundle("sample.lang", locale);
        Locale.setDefault(locale);
        languageMenu.setText(rb.getString("languageMenu"));
        label1.setText(rb.getString("label1"));
        //currentItemsLabel.setText(rb.getString("label3"));
        hiddenString = rb.getString("label3");
        label2.setText(rb.getString("label2"));
        label4.setText(rb.getString("label4"));
        label5.setText(rb.getString("label5"));
        label6.setText(rb.getString("label6"));
        showInfo.setText(rb.getString("showInfo"));
        exit.setText(rb.getString("exit"));
        closeMenu.setText(rb.getString("exit"));
        aboutMenu.setText(rb.getString("about"));
        addButton.setText(rb.getString("button1"));
        runButton.setText(rb.getString("button2"));
        weighColumn.setText(rb.getString("colWeight"));
        valueColumn.setText(rb.getString("colValue"));
        weighColumn1.setText(rb.getString("colWeight"));
        valueColumn1.setText(rb.getString("colValue"));
        currentPattern = rb.getString("pattern");
        refreshTable();
        itemDisplay();
        /*Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL); //ofPattern("yyyy-MM-dd HH:mm:ss");
            dateLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();*/

    }

    private void itemDisplay() {

        int tmp = itemList.size();
        if (tmp == 1) {
            hiddenString2 = rb.getString("one");
        } else if (tmp % 10 > 1 && tmp % 10 < 5 && (tmp > 20 || tmp < 10)) {
            hiddenString2 = rb.getString("2_4");
        } else {
            hiddenString2 = rb.getString("more");
        }
    }

    private void refreshTable() {
        if (algorithmComboBox.getValue() != null && itemList.size() != 0 && !backpackSizeField.getText().equals(""))
            runButton.setDisable(false);
        /*for(Item item : itemList)
            System.out.print(item.getItemValue() + " " + item.getItemWeight() + "\n");*/
        observableList.setAll(itemList);
        itemDisplay();
        currentItemsLabel.setText(hiddenString + itemList.size() + " " + hiddenString2);
    }

}

