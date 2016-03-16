package org.testfx.playground.model;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testfx.playground.service.Service;

@Component
public class Model {

	private final ObservableList<Team> teams = FXCollections.observableArrayList();

	private final ObservableList<Player> players = FXCollections.observableArrayList();

	private final ExecutorService executorService;

	private final Service service;

	@Autowired
	public Model(Service service, ExecutorService executorService) {
		this.service = service;
		this.executorService = executorService;
	}

	@PostConstruct
	public void postConstruct() throws Exception {
		final Task<Void> loadTeamsTask = new LoadTeamsTask();
		final Task<Void> loadPlayersTask = new LoadPlayersTask();
		loadTeamsTask.setOnFailed(event -> loadTeamsTask.getException().printStackTrace());
		loadPlayersTask.setOnFailed(event -> loadPlayersTask.getException().printStackTrace());
		executorService.execute(loadTeamsTask);
		executorService.execute(loadPlayersTask);
	}

	public void fetchRemainingPlayers() {
		executorService.execute(() -> {
			List<Player> morePlayers = service.loadMorePlayers();
			Platform.runLater(() -> players.addAll(morePlayers));
		});
	}

	public void fetchRemainingTeams() {
		executorService.execute(() -> {
			List<Team> moreTeams = service.loadMoreTeams();
			Platform.runLater(() -> teams.addAll(moreTeams));
		});
	}

	public ObservableList<Team> getTeams() {
		return teams;
	}

	public ObservableList<Player> getPlayers() {
		return players;
	}

	public ObservableList<Player> getPlayersByTeam(Team team) {
		return players.filtered(player -> {
			return Objects.equals(player.getTeamId(), team.getId());
		});
	}

	private class LoadTeamsTask extends Task<Void> {
		@Override
		protected Void call() throws Exception {
			final List<Team> loadedTeams = service.loadTeams();
			Platform.runLater(() -> teams.addAll(loadedTeams));
			return null;
		}
	}

	private class LoadPlayersTask extends Task<Void> {
		@Override
		protected Void call() throws Exception {
			final List<Player> loadedPlayers = service.loadPlayers();
			Platform.runLater(() -> players.addAll(loadedPlayers));
			return null;
		}
	}

}
