package vzsolt.gameoflife.cellgame.board;

public interface BoardHandler {

	void fillNeighbourInfo(Board board);

	Board generateBoard(int size, double probability);

}