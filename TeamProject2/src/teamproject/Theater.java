package teamproject;

public class Theater {
	int index;
	Movie mv;
	Boolean seat[][];
	public Theater(int index, Movie mv) {
		super();
		this.mv = mv;
		this.index = index;
		seat = new Boolean[5][5];
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Movie getMv() {
		return mv;
	}
	public void setMv(Movie mv) {
		this.mv = mv;
	}
	public Boolean[][] getSeat() {
		return seat;
	}
	public void showSeat() {
		for(int i=0;i<seat.length;i++) {
			for(int j=0;j<seat[i].length;j++) {
				System.out.print(seat[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	
	
	
}
