package teamproject;

public class Ticket {
	private int id;
	private Movie m;
	private User u;
	private int s_id;
	
	public Ticket(Movie m, User u,int s_id, int id) {
		super();
		this.id = id;
		this.m = m;
		this.u = u;
		this.s_id = s_id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Ticket\n"+id+"°ü"+"\n" + m + "\n" + u;
	}
	
	
	
	
	
	
	
}
