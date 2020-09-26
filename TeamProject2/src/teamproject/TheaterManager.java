package teamproject;

import java.util.ArrayList;

public class TheaterManager extends DBManager {

	ArrayList<Theater> t_list = new ArrayList<Theater>();
	public TheaterManager() {
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
