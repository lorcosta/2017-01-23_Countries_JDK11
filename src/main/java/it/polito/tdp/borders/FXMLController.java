package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtAnno;

    @FXML
    private ComboBox<Country> boxNazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	this.txtResult.clear();
    	String toBeParsed=this.txtAnno.getText();
    	Integer anno=null;
    	try {
    		anno=Integer.parseInt(toBeParsed);
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! Il valore inserito non è un numero corretto.\n");
    	}
    	List<Country> stati=model.creaGrafo(anno);
    	for(Country c:stati) {
    		this.txtResult.appendText(c+", confinati:"+c.getConfinanti()+"\n");
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
