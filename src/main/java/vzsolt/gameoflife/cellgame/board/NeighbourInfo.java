package vzsolt.gameoflife.cellgame.board;

public class NeighbourInfo {

	private CellColor color;
	private int livingNeighbour;
	
	public NeighbourInfo(){
		
	}
	
	public NeighbourInfo(NeighbourInfo info){
		this.color = info.color;
		this.livingNeighbour = info.livingNeighbour;
	}
	
	public CellColor getColor() {
		return color;
	}
	public void setColor(CellColor color) {
		this.color = color;
	}
	public int getLivingNeighbour() {
		return livingNeighbour;
	}
	public void setLivingNeighbour(int livingNeighbour) {
		this.livingNeighbour = livingNeighbour;
	}
	
	
	
}
