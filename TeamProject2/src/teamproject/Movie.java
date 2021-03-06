package teamproject;

public class Movie {
	private int id;
	private String name;
	private String director;
	private String time;	//time은 우선 string으로 처리하여 구현 후, 시간 처리를 어떻게 할지를 생각한 후 수정
	private int age_limit;
	private int theater_num;
	private int date;
	
	public Movie(int id, String name, String director, String time, int age_limit, int theater_num, int date) {
		super();
		this.id = id;
		this.name = name;
		this.director = director;
		this.time = time;
		this.age_limit = age_limit;
		this.theater_num = theater_num;
		this.date=date;
	}

	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getAge_limit() {
		return age_limit;
	}

	public void setAge_limit(int age_limit) {
		this.age_limit = age_limit;
	}

	public int getTheater_num() {
		return theater_num;
	}
	
	public void setTheater_num(int theater_num) {
		this.theater_num = theater_num;
	}
	public int getDate() {
		return date;
	}
	
	public void setDate(int date) {
		this.date = date;
	}



	@Override
	public String toString() {
		return "Movie [id=" + id + ", name=" + name + ", director=" + director + ", time=" + time + ", age_limit="
				+ age_limit + ", theater_num=" + theater_num + ", date=" + date + "]";
	}


	
	
}
