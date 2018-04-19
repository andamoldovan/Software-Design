package model;

public class ShowModel {
	private int id;
	private String title;
	private String genre;
	private String distribution;
	private String date;
	private int nrTickets;
	
	public ShowModel() {
		
	}
	
	public ShowModel(int id, String title, String genre, String distribution, String date, int nrTickets) {
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.distribution = distribution;
		this.date = date;
		this.nrTickets = nrTickets;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(validateTitle(title) == true)
			this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		if(validateDistribution(distribution) == true)
			this.distribution = distribution;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNrTickets() {
		return nrTickets;
	}

	public void setNrTickets(int nrTickets) {
		if(validateNrTickets(nrTickets) == true)
			this.nrTickets = nrTickets;
	}
	
	
	public boolean soldOut() {
		if(nrTickets == 0) return true;
		else return false;
	}
	
	/**
	 * Checks the validity of the title of a show before it has the chance 
	 * of being added to the database
	 * @param title
	 * @return true if the title is valid(the first character must be a letter and rest must not contain non - alphanumeric characters)
	 * 			false if the title is invalid
	 */
	public boolean validateTitle(String title) {
		char[] t = title.toCharArray();
		if(!Character.isAlphabetic(t[0])) return false;
		for(char c : t) {
			if(!Character.isAlphabetic(c) && !Character.isDigit(c) && !(c == ' ')) return false;
		}
		return true;
	}
	
	/**
	 * Validates the distribution
	 * @param dist
	 * @return true if the distribution contains alphabetical characters and the symbols ',' and ' '
	 * 		   false if other symbols are contained or is empty
	 */
	public boolean validateDistribution(String dist) {
		if(dist.equals("")) return false;
		
		char[] ch = dist.toCharArray();
		for(char c : ch) {
			if(!Character.isAlphabetic(c) && !(c == ' ') && !(c == ',')) return false;
		}
		return true;
	}
	
	/**
	 * Checks if the number of tickets available were correctly introduced
	 * @param nr
	 * @return true if the number is greater than 0
	 * 		   false it it is smaller than 0
	 */
	public boolean validateNrTickets(int nr) {
		if(nr < 0) return false;
		else return true;
	}
	
	@Override
	public String toString() {
		return "Title: " + this.getTitle() + " Genre Id: " + this.getGenre() + " Distribution: " + this.getDistribution()
			 + " Date: " + this.getDate() + " Nr tickets: " + this.getNrTickets();
	}
	
}
