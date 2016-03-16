package org.testfx.playground.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Component;
import org.testfx.playground.model.Player;
import org.testfx.playground.model.Team;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;

@Component
public class Service {

	private final ObjectMapper mapper = new ObjectMapper();

	private final JavaType playerList = CollectionType.construct(List.class, SimpleType.construct(Player.class));

	private final JavaType teamList = CollectionType.construct(List.class, SimpleType.construct(Team.class));

	public List<Player> loadMorePlayers() {
		return loadFile("/data/players_more.json", playerList);
	}

	public List<Team> loadMoreTeams() {
		return loadFile("/data/teams_more.json", teamList);
	}

	public List<Player> loadPlayers() {
		return loadFile("/data/players.json", playerList);
	}

	public List<Team> loadTeams() {
		return loadFile("/data/teams.json", teamList);
	}

	private <T> T loadFile(String filePath, JavaType javaType) {
		try {
			final InputStream is = getClass().getResourceAsStream(filePath);
			return mapper.readValue(is, javaType);
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse file: " + filePath, e);
		}
	}

}
