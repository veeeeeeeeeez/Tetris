// Vincent Z
// Section A
// Final Project - Tetris
// Controller for the Tetris Game
// TetrisGameController.java
// 7/30/20

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class TetrisGameController extends GameController implements KeyListener {

	// final instance variable fo the current Tetris game.
	private final TetrisGame game;
	// instance variable for the user's score.
	private int score = 0;

	/**
	 * constructs a controller for the game based on the game view and the game.
	 * @param view TetrisGameView parameter for the game view of the game.
	 * @param game TetrisGame parameter for the current tetris game.
	 */
	public TetrisGameController(TetrisGameView view, TetrisGame game) {
		super(view);
		this.game = game;
		// initializes the game (starts it).
		init();
	}

	/**
	 * initializes the game (starts it).
	 */
	private void init() {
		// sets the initial score to 0 at the beginning of the game.
		score = 0;
		// sets the game over message (comprised of a string and the score) to an empty string.
		game.gameOverMessage = "";
		// clears the board (sets the board, rather, the information of the board to an empty board).
		clearBoard();
		// spawns a new piece.
		spawnNewPiece();
	}

	/**
	 * clears the board (sets the board, rather, the information of the board to an empty board).
	 */
	private void clearBoard() {
		// makes the grid a new PieceShape 2d array based on the width and height of the panel.
		game.grid = new Piece.PieceShape[TetrisGame.PANEL_WIDTH][TetrisGame.PANEL_HEIGHT];
		// loops through every element in the grid, and sets it all to empty (the piece shape E).
		for (int i = 0; i < TetrisGame.PANEL_WIDTH; i++) {
			for (int j = 0; j < TetrisGame.PANEL_HEIGHT; j++) {
				game.grid[i][j] = Piece.PieceShape.E;
			}
		}
	}

	/**
	 * spawns a new piece at the top of the panel.
	 */
	private void spawnNewPiece() {
		// sets the piece shape of the falling piece to a random piece shape.
		game.fallingPiece = new Piece(Piece.PieceShape.values()[new Random().nextInt(7)]);
		// initial x position of the falling piece.
		game.fallingPiece.x = TetrisGame.PANEL_WIDTH / 2;
		// initial y position of the falling piece.
		game.fallingPiece.y = -(game.fallingPiece.minY());
		// if a new piece can't be placed, the game is over.
		if (!attemptToPlace(game.fallingPiece, game.fallingPiece.x, game.fallingPiece.y)) {
			gameOver();
		}
	}

	/**
	 * logic for when the game is over (when the user loses).
	 */
	private void gameOver() {
		// adds the score to the game over message,
		// multiplied by 100 to match the original tetris game.
		game.gameOverMessage = "SCORE: " + score * 100;
		// clears the board.
		clearBoard();
		// draws the piece one last time.
		view.draw();
		// stops the timer (ends the game).
		timer.stop();
	}

	@Override
	protected void update() {
		// updates the piece.
		super.update();
		// constantly moves the piece down.
		moveDown();
	}

	/**
	 * attempts to place a block (if a block can be moved, or rotated).
	 * @param p Piece parameter for the piece that is attempted to be placed.
	 * @param newX int parameter for the x coordinate that it would be moved to (attempted).
	 * @param newY int parameter for the y coordinate that it would be moved to (attempted).
	 * @return whether or not the block can be placed.
	 */
	private boolean attemptToPlace(Piece p, int newX, int newY) {
		// loops through the rectangles of the piece (squares).
		for (int i = 0; i < 4; i++) {
			// adds the x to be moved to to the current x position of the piece.
			int x = newX + p.coord[p.status][i][0];
			// adds the y to be moved to to the current y position of the piece.
			int y = newY + p.coord[p.status][i][1];
			// checks to see if the piece is at the left or right border of the panel,
			// or if it's at the top or bottom of the panel.
			if (y < 0 || y >= TetrisGame.PANEL_HEIGHT || x < 0 || x >= TetrisGame.PANEL_WIDTH) {
				return false;
			}
			// checks to see whether or not the position that the piece is going to move to
			// is empty.
			if (game.grid[x][y] != Piece.PieceShape.E) {
				return false;
			}
		}
		// sets the falling piece to the piece that is passed in and checked.
		game.fallingPiece = p;
		// sets the x position of the falling piece to the new x defined above.
		game.fallingPiece.x = newX;
		// sets the y position of the falling piece to the new y defined above.
		game.fallingPiece.y = newY;
		return true;
	}

	/**
	 * logic for what to do if a piece has finished falling
	 * (hit the bottom of the panel, or hit another piece).
	 */
	private void finishFalling() {
		// loops through the rectangles of the piece (squares).
		for (int i = 0; i < 4; i++) {
			// sets the x to the current x position of the falling piece + it's x coordinate (the occupied area).
			int x = game.fallingPiece.x + game.fallingPiece.coord[game.fallingPiece.status][i][0];
			// sets the y to the current y position of the falling piece + it's y coordinate (the occupied area).
			int y = game.fallingPiece.y + game.fallingPiece.coord[game.fallingPiece.status][i][1];
			// saves the x and y position of the piece that finished falling to the grid, and the piece shape of it.
			game.grid[x][y] = game.fallingPiece.shape;
		}
		// clears the necessary lines.
		clearLines();
		// spawns a new piece.
		spawnNewPiece();
	}

	/**
	 * clears the necessary lines (lines that match the criteria).
	 */
	private void clearLines() {
		// sets the total number of lines cleared to 0.
		int lineCleared = 0;
		// loops through the grid, and checks if a line can be cleared (if it's full).
		for (int y = 0; y < TetrisGame.PANEL_HEIGHT; y++) {
			boolean canClear = true;
			for (int x = 0; x < TetrisGame.PANEL_WIDTH; x++) {
				// if a line contains the piece shape E (empty spot), it can't be cleared.
				if (game.grid[x][y] == Piece.PieceShape.E) {
					canClear = false;
					break;
				}
			}
			// if it can be cleared, increment the number of lines cleared.
			if (canClear) {
				lineCleared++;
				// when a line is cleared, all the lines above are shifted down the number of lines cleared.
				for (int j = y; j >= 1; j--) {
					for (int x = 0; x < TetrisGame.PANEL_WIDTH; x++) {
						game.grid[x][j] = game.grid[x][j-1];
						// sets the newly emptied spots in the grid to the piece shape E (empty).
						game.grid[x][j-1] = Piece.PieceShape.E;
					}
				}
			}
			// scoring logic (doesn't entirely match the original scoring of the game as a fun twist).
			if (lineCleared > 0) {
				if (lineCleared < 4) {
					// each line cleared, if the total lines cleared is less than 4 returns 100 points.
					score = score + lineCleared;
				} else if (lineCleared == 4) {
					// if the lines cleared is 4, you get 800 points.
					score = 8;
				} else {
					// multiplier that rewards more lines cleared after the 4.
					score = score + 8 + lineCleared;
				}
			}
		}
	}

	/**
	 * moves the entire falling piece down one block.
	 */
	private void moveDown() {
		// keeps shifting the y of the piece down 1, until it can't be placed anymore,
		// in which the piece finishes falling.
		if (!attemptToPlace(game.fallingPiece, game.fallingPiece.x, game.fallingPiece.y + 1)) {
			finishFalling();
		}
	}

	/**
	 * drops the falling piece all the way down until it hits the bottom of the panel
	 * or it hits another piece (free drops instead of only incrementing the y by 1).
	 */
	private void freeDrop() {
		// sets the y to the current y position of the falling piece.
		int y = game.fallingPiece.y;
		// checks while the piece hasn't hit the bottom yet.
		while (y < TetrisGame.PANEL_HEIGHT) {
			// drops the piece until it hits something.
			if (!attemptToPlace(game.fallingPiece, game.fallingPiece.x, game.fallingPiece.y + 1)) {
				break;
			}
			y++;
		}
		// one finished, the falling is also finished.
		finishFalling();
	}

	/**
	 * moves the piece to the left.
	 */
	private void moveLeft() {
		// shifts the piece to the left by decreasing the x position of the falling piece, moving it towards 0.
		attemptToPlace(game.fallingPiece, game.fallingPiece.x - 1, game.fallingPiece.y);
	}

	/**
	 * moves the piece to the right.
	 */
	private void moveRight() {
		// shifts the piece to the right by increasing the x position of the falling piece,
		// moving it towards the border (width) of the panel.
		attemptToPlace(game.fallingPiece, game.fallingPiece.x + 1, game.fallingPiece.y);
	}

	/**
	 * rotates the piece clockwise.
	 */
	private void rotateRight() {
		Piece p = new Piece(game.fallingPiece);
		// increases the status by 1, cycling through the coordinates of the coord[][][]
		// in a clockwise fashion.
		p.status = p.status + 1;
		// resets the status of the rotation back to 0 after the last rotation type (clockwise).
		if (p.status > 3) {
			p.status = 0;
		}
		// check to see if it can be rotated, and placing it if it can.
		attemptToPlace(p, p.x, p.y);
	}

	public void rotateLeft() {
		Piece p = new Piece(game.fallingPiece);
		// decreases the status by 1, cycling through the coordinates of the coord[][][]
		// in a counter-clockwise fashion.
		p.status = p.status - 1;
		// resets the status of the rotation back to 0 after the last rotation type (counter-clockwise).
		if (p.status < 0) {
			p.status = 3;
		}
		// check to see if it can be rotated, and placing it if it can.
		attemptToPlace(p, p.x, p.y);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		// HOW TO PLAY THE GAME:
		// moves left if the user presses the left arrow key
		// moves right if the user presses the right arrow key
		// drops the piece to the bottom or until it hits something
		// if the user presses the space key
		// rotates clockwise if the user presses the x key
		// rotates counterclockwise if the user presses the z key
		// resets the game (restart) if the user presses the r key.
		switch (keyCode) {
			case KeyEvent.VK_LEFT:
				moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				moveRight();
				break;
			case KeyEvent.VK_SPACE:
				freeDrop();
				break;
			case KeyEvent.VK_X:
				rotateRight();
				break;
			case KeyEvent.VK_Z:
				rotateLeft();
				break;
			case KeyEvent.VK_R:
				init();
				timer.restart();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}