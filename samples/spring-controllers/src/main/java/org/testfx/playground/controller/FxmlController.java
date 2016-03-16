package org.testfx.playground.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

/**
 * Identifies a controller that will be created by an {@link FXMLLoader}. The
 * {@code FXMLLoader} will automatically inject {@code location} and
 * {@code resources} properties into the controller, and then it will call the
 * no-arg {@link #initialize()} method. This is the recommended approach: don't
 * use the {@link Initializable} interface.
 */
public interface FxmlController {

	/**
	 * Called by the {@link FXMLLoader} to initialize a controller after its
	 * root element has been completely processed. This means all of the
	 * controller's {@link FXML} elements will be injected, and they can be used
	 * to wire up the GUI in ways that couldn't be accomplished using pure FXML,
	 * e.g. attaching property listeners.
	 */
	void initialize();

}
