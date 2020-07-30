// Vincent Z
// Section A
// Final Project - Tetris
// The game view of the Tetris Game (What you see)
// TetrisGameView.java
// 7/30/20

import java.awt.*;
import java.awt.event.KeyListener;

public class TetrisGameView extends GameView {

	// final instance variable for the current game
	private final TetrisGame game;

	/**
	 * constructs a new game view (visual) for the game.
	 * @param game TetrisGame parameter for the game to make visuals for.
	 */
	public TetrisGameView(TetrisGame game) {
		super();
		this.game = game;
		// creates a new panel based on the width (num of blocks wide) * the actual length of each block,
		// and the height (num of blocks tall) * the actual length of each block.
		panel = new DrawingPanel(TetrisGame.PANEL_WIDTH * TetrisGame.SQUARE_LENGTH,
				  TetrisGame.PANEL_HEIGHT * TetrisGame.SQUARE_LENGTH);
		// gets the graphics on the panel.
		g = panel.getGraphics();
	}

	/**
	 * adds a key listener to the current drawing panel.
	 * @param listener Keylistener parameter for the key listener of the panel.
	 */
	public void addKeyListener(KeyListener listener) {
		panel.addKeyListener(listener);
	}

	@Override
	protected void draw() {
		super.draw();
		// clears the background
		clearBackground();
		// draws the pieces that are currently on the panel
		drawGrid();
		// draws the game over screen if the game is over
		if (!game.gameOverMessage.isBlank()) {
			drawGameOver();
		} else {
			// draws a falling piece
			drawFallingPiece();
		}
	}

	/**
	 * clears the background by drawing a white rectangle that is the size of the panel.
	 */
	private void clearBackground() {
		g.setColor(Color.WHITE);
		g.fillRect(0,0, TetrisGame.PANEL_WIDTH * TetrisGame.SQUARE_LENGTH,
				  TetrisGame.PANEL_HEIGHT * TetrisGame.SQUARE_LENGTH);
	}

	/**
	 * draws the game over screen.
	 */
	private void drawGameOver() {
		g.setColor(Color.GRAY);
		// creates a new font
		Font myFont = new Font(Font.SANS_SERIF, Font.BOLD, TetrisGame.PANEL_WIDTH * 3 / 2);
		g.setFont(myFont);
		// draws the words "Game Over"
		g.drawString("GAME OVER!",
				  TetrisGame.PANEL_WIDTH * TetrisGame.SQUARE_LENGTH / 3,
				  TetrisGame.PANEL_HEIGHT * TetrisGame.SQUARE_LENGTH / 2);
		// draws the score
		g.drawString(game.gameOverMessage,
				  TetrisGame.PANEL_WIDTH * TetrisGame.SQUARE_LENGTH / 3,
				  TetrisGame.PANEL_HEIGHT * TetrisGame.SQUARE_LENGTH / 2 + 20);
		// draws the restart message
		g.drawString("Press R to Retry",
				  TetrisGame.PANEL_WIDTH * TetrisGame.SQUARE_LENGTH / 3,
				  TetrisGame.PANEL_HEIGHT * TetrisGame.SQUARE_LENGTH / 2 + 40);
	}

	/**
	 * draws the current pieces that have already landed.
	 */
	private void drawGrid() {
		// loops through the 2d array of the grid.
		for (int i = 0; i < TetrisGame.PANEL_WIDTH; i++) {
			for (int j = 0; j < TetrisGame.PANEL_HEIGHT; j++) {
				// checks to see if the piece isn't empty, and draws the piece at it's position.
				if (game.grid[i][j] != Piece.PieceShape.E) {
					drawSquare(i, j, Piece.getColor(game.grid[i][j]));
				}
			}
		}
	}

	/**
	 * draws the current falling piece.
	 */
	private void drawFallingPiece() {
		// loops through each rectangle of the shape type (always 4), and draws it
		// based on it's status and position.
		for (int i = 0; i < 4; i++) {
			drawSquare(game.fallingPiece.x + game.fallingPiece.coord[game.fallingPiece.status][i][0],
					  game.fallingPiece.y + game.fallingPiece.coord[game.fallingPiece.status][i][1],
					  game.fallingPiece.color);
		}
	}

	/**
	 * helper method to help draw a square (rectangle in each piece).
	 * @param x the top left x coordinate of the rectangle to be drawn.
	 * @param y the top left y coordinate of the rectangle to be drawn.
	 * @param color the color of the piece.
	 */
	private void drawSquare(int x, int y, Color color) {
		// sets the color to the passed in color.
		g.setColor(color);
		// draws a rectangle based on the given properties.
		g.fillRect(x * TetrisGame.SQUARE_LENGTH, y * TetrisGame.SQUARE_LENGTH,
				  TetrisGame.SQUARE_LENGTH - 1,
				  TetrisGame.SQUARE_LENGTH - 1);
	}
}