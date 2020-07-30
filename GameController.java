// Vincent Z
// Section A
// Final Project - Tetris
// Blueprint of the Game Controller, for code usability (if I made Snake)
// GameController.java
// 7/30/20

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GameController implements ActionListener {

	// instance variable for the timer used.
	Timer timer;
	// instance variable for the game view of the current game.
	GameView view;
	// final instance variable for the frame interval of the game
	// (how fast the piece moves down).
	private final int FRAME_INTERVAL = 200;

	/**
	 * constructs a new game controller based on the game view.
	 * @param view Game view parameter for the game view of the current game.
	 */
	public GameController(GameView view) {
		this.view = view;
		// creates a new timer on the game.
		timer = new Timer(FRAME_INTERVAL, this);
		// starts the timer.
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// runs the game.
		runGame();
	}

	/**
	 * runs the game by updating the game and displaying the game view.
	 */
	private void runGame() {
		update();
		view.draw();
	}

	/**
	 * updates the game.
	 */
	protected void update() {
	}
}