package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TicTacToeController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView img4;

	@FXML
	private ImageView img3;

	@FXML
	private ImageView img6;

	@FXML
	private ImageView img5;

	@FXML
	private ImageView img8;

	@FXML
	private ImageView img7;

	@FXML
	private ImageView img9;

	@FXML
	private ImageView img2;

	@FXML
	private ImageView img1;

	List<ImageView> cells = new ArrayList<ImageView>();
	String[] values = new String[9];
	Image imgX, imgO;
	int moves = 0;

	@FXML
	void fillUserCell(MouseEvent event) {

		ImageView context = (ImageView) event.getSource();
		int contextId = cells.indexOf(context);
		if (values[contextId].equals("N")) {

			cells.get(contextId).setImage(imgX);
			values[contextId] = "x";
			moves++;

			Random rChoice = new Random();
			boolean taken = false;
			if (moves < 8) {
				while (!taken) {

					int current = (rChoice.nextInt(8)) + 1;
					if (values[current].equals("N")) {

						values[current] = "o";
						cells.get(current).setImage(imgO);
						taken = true;
						moves++;
					} else
						continue;
				}
			}
			
			if (checkWinner()) {

				System.out.println("Fine");
				System.exit(0);
			}
		}
	}

	public boolean checkWinner() {

		if ((values[0].equals(values[1]) && values[1].equals(values[2]) && !values[0].equals("N"))
				|| (values[3].equals(values[4]) && values[4].equals(values[5]) && !values[3].equals("N"))
				|| (values[6].equals(values[7]) && values[7].equals(values[8]) && !values[7].equals("N"))
				|| (values[0].equals(values[3]) && values[3].equals(values[6]) && !values[0].equals("N"))
				|| (values[1].equals(values[4]) && values[4].equals(values[7]) && !values[1].equals("N"))
				|| (values[2].equals(values[5]) && values[5].equals(values[8]) && !values[2].equals("N"))
				|| (values[2].equals(values[4]) && values[4].equals(values[6]) && !values[2].equals("N"))
				|| (values[0].equals(values[4]) && values[4].equals(values[8]) && !values[0].equals("N"))) {
			return true;
		} else
			return false;
	}

	@FXML
	void initialize() {
		assert img4 != null : "fx:id=\"img4\" was not injected: check your FXML file 'TicTacToe.fxml'.";
		assert img3 != null : "fx:id=\"img3\" was not injected: check your FXML file 'TicTacToe.fxml'.";
		assert img6 != null : "fx:id=\"img6\" was not injected: check your FXML file 'TicTacToe.fxml'.";
		assert img5 != null : "fx:id=\"img5\" was not injected: check your FXML file 'TicTacToe.fxml'.";
		assert img8 != null : "fx:id=\"img8\" was not injected: check your FXML file 'TicTacToe.fxml'.";
		assert img7 != null : "fx:id=\"img7\" was not injected: check your FXML file 'TicTacToe.fxml'.";
		assert img9 != null : "fx:id=\"img9\" was not injected: check your FXML file 'TicTacToe.fxml'.";
		assert img2 != null : "fx:id=\"img2\" was not injected: check your FXML file 'TicTacToe.fxml'.";
		assert img1 != null : "fx:id=\"img1\" was not injected: check your FXML file 'TicTacToe.fxml'.";

		cells.add(img1);
		cells.add(img2);
		cells.add(img3);
		cells.add(img4);
		cells.add(img5);
		cells.add(img6);
		cells.add(img7);
		cells.add(img8);
		cells.add(img9);

		imgX = new Image(getClass().getResourceAsStream("x.png"));
		imgO = new Image(getClass().getResourceAsStream("o.png"));

		for (int k = 0; k < values.length; k++) {

			values[k] = "N";
		}
	}
}
