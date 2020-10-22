package teamproject;

public class Ticket {
	private int idx;
	private Movie m;
	private User u;
	private int s_id;
	
	public Ticket(Movie m, User u,int s_id, int idx) {
		super();
		this.idx = idx;
		this.m = m;
		this.u = u;
		this.s_id = s_id;
	}
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public Movie getMovie() {
		return m;
	}
	public void setMovie(Movie m) {
		this.m = m;
	}
	public User getUser() {
		return u;
	}
	public void setUser(User u) {
		this.u = u;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}

	@Override
	public String toString() {
		return "Ticket\n"+idx+"관"+"\n" + m + "\n" + u; //@@@@@@@@@@@@@@@ id+관? 하면 상영관 넘버와 다름
	}
	
	
	
	
	
	
	
}
