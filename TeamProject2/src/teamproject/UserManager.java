package teamproject;

import java.util.ArrayList;

public class UserManager extends DBManager {

	ArrayList<User> u_list = new ArrayList<User>();
	
	public UserManager() {
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
