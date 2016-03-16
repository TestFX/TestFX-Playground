package org.testfx.playground.model;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Player {

	private final StringProperty name = new SimpleStringProperty();

	private final IntegerProperty number = new SimpleIntegerProperty();

	private final ObservableList<String> positions = FXCollections.observableArrayList();

	private final StringBinding positionsBinding = Bindings.createStringBinding(() -> {
		return String.join(",", positions);
	} , positions);

	private final IntegerProperty teamId = new SimpleIntegerProperty();

	private final DoubleProperty fieldGoalPercentage = new SimpleDoubleProperty();

	public Player() {
		// Used by serialization library
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getName() {
		return nameProperty().get();
	}

	public void setName(String value) {
		nameProperty().set(value);
	}

	public IntegerProperty numberProperty() {
		return number;
	}

	public int getNumber() {
		return numberProperty().get();
	}

	public void setNumber(int value) {
		numberProperty().set(value);
	}

	public StringBinding positionsBinding() {
		return positionsBinding;
	}

	public ObservableList<String> getPositions() {
		return positions;
	}

	public void setPositions(List<String> positions) {
		this.positions.setAll(positions);
	}

	public IntegerProperty teamIdProperty() {
		return teamId;
	}

	public int getTeamId() {
		return teamIdProperty().get();
	}

	public void setTeamId(int value) {
		teamIdProperty().set(value);
	}

	public DoubleProperty fieldGoalPercentageProperty() {
		return fieldGoalPercentage;
	}

	public double getFieldGoalPercentage() {
		return fieldGoalPercentageProperty().get();
	}

	public void setFieldGoalPercentage(double value) {
		fieldGoalPercentageProperty().set(value);
	}

	@Override
	public String toString() {
		return getName();
	}

}
