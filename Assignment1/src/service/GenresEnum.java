package service;

public enum GenresEnum {
	Ballet, Comedy, Comedy_Drama, Drama, Tragedy, Folk, Tragy_Comedy,
	Melodrama, Political, Satirical, Science_Fiction, Thriller,
	Ballad, Chamber_Opera, Children_Opera, Christmas_Opera, Farse, 
	Intermezzi, Opera_Ballet, Opera;
	
	@Override
	public String toString() {
		switch(this){
			case Ballet : return "Ballet";
			case Comedy : return "Comedy";
			case Comedy_Drama : return "Comedy Drama";
			case Drama : return "Drama";
			case Tragedy : return "Tragedy";
			case Folk : return "Folk";
			case Tragy_Comedy : return "Tragedy Comedy";
			case Melodrama : return "Melodrama";
			case Political : return "Political";
			case Satirical : return "Satirical";
			case Science_Fiction : return "Science Fiction";
			case Thriller : return "Thriller";
			case Ballad : return "Ballad";
			case Chamber_Opera : return "Chamber Opera";
			case Children_Opera : return "Children Opera";
			case Christmas_Opera : return "Christmas Opera";
			case Farse : return "Farse";
			case Intermezzi : return "Intermezzi";
			case Opera_Ballet : return "Opera Ballet";
			case Opera : return "Opera";
		}
		return "";
	}
}




