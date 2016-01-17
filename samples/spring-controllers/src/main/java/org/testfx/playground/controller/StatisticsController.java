package org.testfx.playground.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testfx.playground.model.Model;
import org.testfx.playground.model.Player;
import org.testfx.playground.model.Team;
import org.testfx.playground.view.FilteredTable;

@Component
public class StatisticsController implements FxmlController {

	private final MainController mainController;
	private final Model model;

	@FXML private TableView<Team> teamTable;
	@FXML private TableColumn<Team, String> teamNameColumn;

	@FXML private FilteredTable<Player> playerTableFilterable;
	@FXML private TableView<Player> playerTable;
	@FXML private TableColumn<Player, Number> playerNumberColumn;
	@FXML private TableColumn<Player, String> playerNameColumn;
	@FXML private TableColumn<Player, String> playerPositionsColumn;
	@FXML private TableColumn<Player, Number> playerFieldGoalPercentageColumn;

	@Autowired
	public StatisticsController(Model model, MainController mainController) {
		this.model = model;
		this.mainController = mainController;
	}

	@Override
	public void initialize() {
		setupTeamTable();
		setupPlayerTable();
	}

	private void setupTeamTable() {
		teamNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		teamTable.setItems(model.getTeams());
		teamTable.getSelectionModel().selectedItemProperty().addListener((obs, oldTeam, newTeam) -> {
			if (newTeam == null) {
				playerTableFilterable.setItems(FXCollections.emptyObservableList());
			} else {
				playerTableFilterable.setItems(model.getPlayersByTeam(newTeam));
			}
		});
		if (!teamTable.getItems().isEmpty()) {
			teamTable.getSelectionModel().selectFirst();
		}
	}

	private void setupPlayerTable() {
		playerNameColumn.prefWidthProperty()
				.bind(playerTable.widthProperty().subtract(playerNumberColumn.widthProperty())
						.subtract(playerPositionsColumn.widthProperty())
						.subtract(playerFieldGoalPercentageColumn.widthProperty()).subtract(2));

		playerNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
		playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		playerPositionsColumn.setCellValueFactory(cellData -> cellData.getValue().positionsBinding());
		playerFieldGoalPercentageColumn.setCellValueFactory(cellData -> {
			return cellData.getValue().fieldGoalPercentageProperty();
		});

		playerTableFilterable.setFilterFunction((player, filterText) -> {
			return player.getName().toLowerCase().contains(filterText.toLowerCase());
		});

		// Show/hide the FG % column
		mainController.showFieldGoalPercentageProperty().addListener((obs, hide, show) -> {
			playerFieldGoalPercentageColumn.setVisible(show);
		});
	}

}
