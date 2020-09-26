package teamproject;

import java.util.ArrayList;



public class MovieManager extends DBManager {

	ArrayList<Movie> m_list = new ArrayList<Movie>();
	
	public MovieManager() {
		// TODO Auto-generated constructor stub
		connectDB();
		readDB();
	}
	
	@Override
	protected void readDB() {	
		// TODO Auto-generated method stub

	}
	
	@Override
	public void showAll() {
		// TODO Auto-generated method stub

	}

}
