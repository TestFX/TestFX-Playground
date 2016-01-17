package org.testfx.playground;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * The main class in the application.
 */
@SpringBootApplication
public class ClientApplication extends Application {

	private Parent root;

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() throws Exception {
		ConfigurableApplicationContext context = startSpringApplication();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
		loader.setControllerFactory(clazz -> context.getBean(clazz));
		root = loader.load();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Close the Spring context when the client is closed.
		stage.setOnCloseRequest(e -> System.exit(0));
		stage.setScene(new Scene(root, 850, 550));
		stage.setTitle("Spring Client Application");
		stage.show();
	}

	private ConfigurableApplicationContext startSpringApplication() {
		SpringApplication application = new SpringApplication(ClientApplication.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		application.setHeadless(false);
		return application.run(args);
	}

	@Bean
	public ExecutorService executorService() {
		String threadNamePrefix = "ApplicationThread-";
		return Executors.newFixedThreadPool(5, new CustomizableThreadFactory(threadNamePrefix));
	}

}
