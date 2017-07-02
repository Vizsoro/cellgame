package vzsolt.gameoflife.cellgame.board;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Cell {
	
	private CellState state;
	private CellColor color;
	private int posX;
	private int posY;
	@JsonIgnore
	private NeighbourInfo neighbourInfo;
	
	public Cell(){		
	}
	
	public Cell(Cell cell){
		this.posX = cell.posX;
		this.posY = cell.posY;
		this.state = cell.state;
		this.color = cell.color;
		
		if(cell.neighbourInfo != null){
			this.neighbourInfo = new NeighbourInfo(cell.neighbourInfo);			
		}
	}
	
	public CellState getState() {
		return state;
	}
	public Cell setState(CellState state) {
		this.state = state;
		return this;
	}
	public CellColor getColor() {
		return color;
	}
	public Cell setColor(CellColor color) {
		this.color = color;
		return this;
	}
	@JsonIgnore
	public NeighbourInfo getNeighbourInfo() {
		return neighbourInfo;
	}
	
	public Cell setNeighbourInfo(NeighbourInfo neighbourInfo) {
		this.neighbourInfo = neighbourInfo;
		return this;
	}
	@JsonIgnore
	public int getLivingNeighbours(){
		return neighbourInfo.getLivingNeighbour();
	}
	@JsonIgnore
	public CellColor getSurroundingColor(){
		return neighbourInfo.getColor();
	}

	public int getPosX() {
		return posX;
	}

	public Cell setPosX(int posX) {
		this.posX = posX;
		return this;
	}

	public int getPosY() {
		return posY;
	}

	public Cell setPosY(int posY) {
		this.posY = posY;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + posX;
		result = prime * result + posY;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (color != other.color)
			return false;
		if (posX != other.posX)
			return false;
		if (posY != other.posY)
			return false;
		if (state != other.state)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cell [state=" + state + ", color=" + color + ", posX=" + posX + ", posY=" + posY + "]";
	}
	
	

	
	
}
