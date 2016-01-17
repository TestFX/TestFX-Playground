package org.testfx.playground.controller;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testfx.playground.model.Model;

@Component
public class MainController implements FxmlController {

	private final Model model;

	@FXML private MenuItem morePlayersMenuItem;
	@FXML private MenuItem moreTeamsMenuItem;
	@FXML private CheckMenuItem showFieldGoalPercentage;

	@Autowired
	public MainController(Model model) {
		this.model = model;
	}

	@Override
	public void initialize() {
		// No extra initialization required
	}

	@FXML
	public void loadMorePlayers() {
		morePlayersMenuItem.setDisable(true);
		model.fetchRemainingPlayers();
	}

	@FXML
	public void loadMoreTeams() {
		moreTeamsMenuItem.setDisable(true);
		model.fetchRemainingTeams();
	}

	public BooleanProperty showFieldGoalPercentageProperty() {
		return showFieldGoalPercentage.selectedProperty();
	}

}
