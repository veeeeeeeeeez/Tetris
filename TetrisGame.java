// Vincent Z
// Section A
// Final Project - Tetris
// Main class to run the Tetris Game
// TetrisGame.java
// 7/30/20

public class TetrisGame {

	// final class constant for the panel width, in blocks.
	public static final int PANEL_WIDTH = 15;
	// final class constant for the panel height, in blocks.
	public static final int PANEL_HEIGHT = 30;
	// final class constant for the side length of each block within each piece.
	public static final int SQUARE_LENGTH = 25;

	// instance variable for the game view of the game.
	private TetrisGameView gameView;
	// instance variable for the controller of the game.
	private TetrisGameController gameController;
	// instance variable for the current falling piece.
	public Piece fallingPiece;
	// instance variable for the grid, that stores information
	// about where each shape is in relation to the panel (if a space is occupied or not).
	public Piece.PieceShape[][] grid;
	// instance variable for game over message.
	public String gameOverMessage = "";

	/**
	 * constructs a new game, using the game view, controller, and a key listener.
	 */
	public TetrisGame() {
		gameView = new TetrisGameView(this);
		gameController = new TetrisGameController(gameView, this);
		gameView.addKeyListener(gameController);
	}

	/**
	 * main method that runs the game.
	 * @param args console input
	 */
	public static void main (String[] args) {
		TetrisGame game = new TetrisGame();
	}
}