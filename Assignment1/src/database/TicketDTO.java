package database;

public class TicketDTO {
	private int id;
	private int showId;
	private int row;
	private int col;
	
	public TicketDTO() {
		
	}
	public TicketDTO(int id, int showId, int row, int col) {
		this.id = id;
		this.showId = showId;
		this.row = row;
		this.col = col;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getShowId() {
		return showId;
	}
	public void setShowId(int showId) {
		this.showId = showId;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
}
