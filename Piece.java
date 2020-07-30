// Vincent Z
// Section A
// Final Project - Tetris
// Creates a piece for the tetris game
// Piece.java
// 7/30/20

import java.awt.*;

public class Piece {

	// final class constant for a custom color : pastel red
	private static final Color pastelRed = new Color(255, 154, 162);
	// final class constant for a custom color : pastel orange
	private static final Color pastelOrange = new Color(255, 183, 178);
	// final class constant for a custom color : pastel yellow
	private static final Color pastelYellow = new Color(255, 218, 193);
	// final class constant for a custom color : pastel green
	private static final Color pastelGreen = new Color(226, 240, 203);
	// final class constant for a custom color : pastel blue
	private static final Color pastelBlue = new Color(181, 234, 215);
	// final class constant for a custom color : pastel purple
	private static final Color pastelPurple = new Color(199, 206, 234);

	/*
	enum for the different types of shapes (piece shapes),
	each shape being represented by a letter, and the letter E
	representing an empty slot in the panel (used later).
	 */
	public static enum PieceShape {I, J, L, O, S, T, Z, E}

	// piece shape
	PieceShape shape;
	// the color of the shape
	public Color color;
	// the 3d array holding each type of shape, and each rotation of that shape.
	int[][][] coord;
	// the current rotation model of the shape.
	int status = 0;
	int x;
	int y;

	/**
	 * gets the color of the piece shape.
	 * @param s PieceShape parameter for the piece shape of the color.
	 * @return the color of the passed in piece shape.
	 */
	public static Color getColor(PieceShape s) {
		switch (s) {
			case I:
				return Color.GRAY;
			case J:
				return pastelRed;
			case L:
				return pastelOrange;
			case O:
				return pastelYellow;
			case S:
				return pastelGreen;
			case T:
				return pastelBlue;
			case Z:
				return pastelPurple;
		}
		return Color.WHITE;
	}

	/**
	 * constructs a piece by setting the x and y coordinates of each rectangle within each piece,
	 * and each rotation form within each piece
	 * @param s PieceShape parameter for the type of shape each 3d array
	 *        	of coordinates corresponds to
	 */
	public Piece(PieceShape s) {
		switch (s) {
			case I:
				// the 3d array is structured as:
				// int[the total number of rotation types]
				// 	[how many coordinates in each rotation type (number of rectangles)]
				// 	[the x and y coordinates]
				// picture a 4 by 2 grid, with (0, 0) being in the center of the grid.
				// the coordinates inside of the 3d array are in relation to that center spot
				// for example, -2, 0 would mean the x coordinate of the rectangle is 2 to the left of the center,
				// the y coordinate of the rectangle is still at the current level.
				// [][].[][]
				// [X][][][] - x is where (-2, 0) would essentially define, this applies for all piece shapes.
				coord = new int[][][]{{{-2, 0}, {-1, 0}, {0, 0}, {1, 0}},
						  					{{-1, -2}, {-1, -1}, {-1, 0}, {-1, 1}},
						  					{{1, -1}, {0, -1}, {-1, -1}, {-2, -1}},
						  					{{0, 1}, {0, 0}, {0, -1}, {0, -2}}};
				// sets the color of the shape
				color = Color.GRAY;
				// sets the piece shape type of the shape
				shape = PieceShape.I;
				// goes to the next case
				break;
			case J:
				// explanation of code above.
				coord = new int[][][]{{{-1, -1}, {-1, 0}, {0, 0}, {1, 0}},
						  					{{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}},
						  					{{0, 0}, {0, -1}, {-1, -1}, {-2, -1}},
						  					{{-1, 0}, {0, 0}, {0, -1}, {0, -2}}};
				color = pastelRed;
				shape = PieceShape.J;
				break;
			case L:
				// explanation of code above.
				coord = new int[][][]{{{-1, 0}, {0, 0}, {1, 0}, {1, -1}},
						  					{{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}},
						  					{{0, -1}, {-1, -1}, {-2, -1}, {-2, 0}},
						  					{{0, 0}, {0, -1}, {0, -2}, {-1, -2}}};
				color = pastelOrange;
				shape = PieceShape.L;
				break;
			case O:
				// explanation of code above.
				coord = new int[][][]{{{-1, -1}, {0, -1}, {-1, 0}, {0, 0}},
						  					{{-1, -1}, {0, -1}, {-1, 0}, {0, 0}},
						  					{{-1, -1}, {0, -1}, {-1, 0}, {0, 0}},
						  					{{-1, -1}, {0, -1}, {-1, 0}, {0, 0}}};
				color = pastelYellow;
				shape = PieceShape.O;
				break;
			case S:
				// explanation of code above.
				coord = new int[][][]{{{-1, 0}, {0, 0}, {0, -1}, {1, -1}},
						  					{{-1, -1}, {-1, 0}, {0, 0}, {0, 1}},
						  					{{0, -1}, {-1, -1}, {-1, 0}, {-2, 0}},
						  					{{0, 0}, {0, -1}, {-1, -1}, {-1, -2}}};
				color = pastelGreen;
				shape = PieceShape.S;
				break;
			case T:
				// explanation of code above.
				coord = new int[][][]{{{-1, 0}, {0, -1}, {0, 0}, {1, 0}},
						  					{{-1, -1}, {0, 0}, {-1, 0}, {-1, 1}},
						  					{{0, -1}, {-1, 0}, {-1, -1}, {-2, -1}},
						  					{{0, 0}, {-1, -1}, {0, -1}, {0, -2}}};
				color = pastelBlue;
				shape = PieceShape.T;
				break;
			case Z:
				// explanation of code above.
				coord = new int[][][]{{{-1, -1}, {0, -1}, {0, 0}, {1, 0}},
						  					{{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
						  					{{0, 0}, {-1, 0}, {-1, -1}, {-2, -1}},
						  					{{-1, 0}, {-1, -1}, {0, -1}, {0, -2}}};
				color = pastelPurple;
				shape = PieceShape.Z;
				break;
		}
	}

	/**
	 * constructs a clone of the piece, by the passed in piece.
	 * @param p Piece parameter for the piece to be cloned (share the same properties).
	 */
	public Piece(Piece p) {
		this.status = p.status;
		this.coord = p.coord;
		this.shape = p.shape;
		this.color = p.color;
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * defines the minimum y position a block can spawn in,
	 * used for game over logic later.
	 * @return
	 */
	public int minY() {
		// the minimum is initialized in the position of it's current status
		// in the rotation, at the 0th index of 2d arrays, and at the y position.
		int min = coord[status][0][1];
		for (int i = 0; i < 4; i++) {
			// finds the minimum value.
			min = Math.min(min, coord[status][i][1]);
		}
		return min;
	}
}