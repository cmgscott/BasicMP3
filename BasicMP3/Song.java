
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.io.FilenameUtils;
import org.tritonus.share.sampled.file.TAudioFileFormat;

public class Song {
	
	String name;
	File file;
	boolean isFavorite;
	int mins, secs, totalSecs;
	String length;
	
	
	public Song() {
		
	}
	public Song(File file) {
		this.file = file;
		try {
			this.mins = getMins(file);
			this.secs = getSecs(file);
		} catch (UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
		}
		this.name = FilenameUtils.getBaseName((file.getAbsolutePath()));
		this.isFavorite = false;
		if (this.secs < 10) {
		this.length = String.format("%d:0%d", this.mins, this.secs);
		} else {
			this.length = String.format("%d:%d", this.mins, this.secs);
		}
		this.totalSecs = (this.mins * 60) + this.secs;
	}
	
	/**
	 * This method get's the mins of the track.  This is code modified from
	 * the user Tom Brito on StackOverflow.
	 * 
	 * @param file
	 * @return
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	private int getMins(File file) throws UnsupportedAudioFileException, IOException {

	    AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
	    if (fileFormat instanceof TAudioFileFormat) {
	        Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
	        String key = "duration";
	        Long microseconds = (Long) properties.get(key);
	        int mili = (int) (microseconds / 1000);
	        int min = (mili / 1000) / 60;
	        return min;
	    } else {
	        throw new UnsupportedAudioFileException();
	    }

	}
	/**
	 * This method get's the mins of the track.  This is code modified from
	 * the user Tom Brito on StackOverflow.
	 * 
	 * @param file
	 * @return
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	private int getSecs(File file) throws UnsupportedAudioFileException, IOException {

	    AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
	    if (fileFormat instanceof TAudioFileFormat) {
	        Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
	        String key = "duration";
	        Long microseconds = (Long) properties.get(key);
	        int mili = (int) (microseconds / 1000);
	        int sec = (mili / 1000) % 60;
	        return sec;
	    } else {
	        throw new UnsupportedAudioFileException();
	    }

	}
	public String toString() {
		return name;
	}

}
