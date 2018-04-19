package database;

public class ShowDTO {
	
		private int id;
		private String title;
		private String genre;
		private String distribution;
		private String date;
		private int nrTickets;
		
		public ShowDTO() {
			
		}
		
		public ShowDTO(int id, String title, String genre, String distribution, String date, int nrTickets) {
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
			this.nrTickets = nrTickets;
		}
		
	
}