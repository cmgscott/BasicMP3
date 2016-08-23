import java.util.ArrayList;

public class PlayList {
	
	String playlistName;
	ArrayList<Song> playlistSongs = new ArrayList<Song>();
	boolean isFavorites;
	
	public PlayList(String name) {
		playlistName = name;
	}
	
	public PlayList() {
	}
	public String toString() {
		return this.playlistName;
	}

}
