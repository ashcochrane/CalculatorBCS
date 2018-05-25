package application.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;

import application.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class MainController {
	
    private ArrayList<Job> jobs = new ArrayList<>();
    private Holidays ch = new Holidays();
    private boolean isPressed;

    @FXML
    private TableView<Job> jTableView;
    @FXML
    private TableColumn<Job, String> jColID;
    @FXML
    private TableColumn<Job, String> jColLMT;
    @FXML
    private TableColumn<Job, LocalDate> jColSD;
    @FXML
    private TableColumn<Job, LocalDate> jColED;
    @FXML
    private Button jButtonA;
    @FXML
    private Button jButtonCA;
    @FXML
    private Button jButtonR;
    @FXML
    private TextField jTextID;
    @FXML
    private DatePicker jTextSD;
    @FXML
    private TextField jTextLMT;
    @FXML
    private ListView<String> hListView;
    @FXML
    private DatePicker hTextSD;
    @FXML
    private DatePicker hTextED;
    @FXML
    private Button hButtonA;
    @FXML
    private Button hButtonR;
    @FXML
    private Button hButtonCA;
    @FXML
    private CheckBox hCheckBox;
    @FXML
    private Text hHideED;
    
    private void clearJobs() {
        this.jobs.clear();
    }
    
    @FXML
    void hCheckPressed(ActionEvent event) {
    	if (hCheckBox.isSelected()) {
    		hHideED.setVisible(false);
    		hTextED.setVisible(false);
    		isPressed = true;
    	} else {
    	 	hHideED.setVisible(true);
    		hTextED.setVisible(true);
    		isPressed = false;
    	}
    }
    
	@FXML
	void hPressedA(ActionEvent event) {
		 
    	
		if (isPressed) {
			MonthDay temp = ch.toMonthDay(hTextSD.getValue());
			ch.addHoliday(temp);
			
			String display = hTextSD.getValue().toString();
			hListView.getItems().add(display);
			
		} else {
			if (hTextSD.getValue() != null && hTextSD.getValue() != null) {
				LocalDate sd = hTextSD.getValue();
				LocalDate ed = hTextED.getValue();
				ch.addHolidayFromTo(sd, ed);

				String display = sd.toString() + " -> " + ed.toString();
				hListView.getItems().add(display);
			}
		}
	}

    @FXML
    void hPressedCA(ActionEvent event) {
    	for ( int i = 0; i < hListView.getItems().size(); i++) {
    	    hListView.getItems().clear();
    	}
    	ch.holidays.clear();
    }

    @FXML
    void hPressedR(ActionEvent event) {
    	String temp = hListView.getSelectionModel().getSelectedItem();
    	hListView.getItems().removeAll(hListView.getSelectionModel().getSelectedItems());

    }

    @FXML
    void jPressedA(ActionEvent event) {
    	
    	for (int i = 0; i < jobs.size(); i++) {
    		if (jobs.get(i).getId().equals(jTextID.getText())) {
    			return;
    		}
    	}
    	
    	if (jTextID.getText() != null && jTextID.getText() != null && jTextSD.getValue() != null) {
    		Job job = new Job(jTextID.getText(), jTextLMT.getText(), jTextSD.getValue());
    		
            if (jobs.size() == 0){
                jobs.add(job);
            }

            for (int i=0; i < jobs.size(); i++) {
                if (!(job.getId().equals(jobs.get(i).getId()))){
                    jobs.add(job);
                }
            }
            
            jColID.setCellValueFactory(new PropertyValueFactory<Job, String>("id"));
            jColLMT.setCellValueFactory(new PropertyValueFactory<Job, String>("lmt"));
            jColSD.setCellValueFactory(new PropertyValueFactory<Job, LocalDate>("startDate"));
            jColED.setCellValueFactory(new PropertyValueFactory<Job, LocalDate>("endDate"));
            
            job.setEndDate(ch.findEndDate(job));
            
            jTableView.getItems().add(job);
    	}
    

    }

    @FXML
    void jPressedCA(ActionEvent event) {
    	for ( int i = 0; i < jTableView.getItems().size(); i++) {
    	    jTableView.getItems().clear();
    	}
    	clearJobs();
    }

    @FXML
    void jPressedR(ActionEvent event) {
    	Job temp = jTableView.getSelectionModel().getSelectedItem();
    	jTableView.getItems().removeAll(jTableView.getSelectionModel().getSelectedItem());
    	jobs.remove(temp);
    }
    
    @FXML
    void saveButtonPressed(ActionEvent event) {
    	SaveState save = new SaveState();
    	try {
			save.save(jobs, ch);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void loadButtonPressed(ActionEvent event) {
		SaveState save = new SaveState();
		try {
			jobs = save.loadJobs();
			ch = save.loadHolidays();
			
            jColID.setCellValueFactory(new PropertyValueFactory<Job, String>("id"));
            jColLMT.setCellValueFactory(new PropertyValueFactory<Job, String>("lmt"));
            jColSD.setCellValueFactory(new PropertyValueFactory<Job, LocalDate>("startDate"));
            jColED.setCellValueFactory(new PropertyValueFactory<Job, LocalDate>("endDate"));
			
			jTableView.getItems().addAll(jobs);
			System.out.println(jTableView.getItems().get(0).getEndDate());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

}
