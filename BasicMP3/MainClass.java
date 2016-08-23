import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MainClass {
	FileInputStream FIS;
	BufferedInputStream BIS;

	public Player player;

	public long pauseLocation;
	public long songTotalLength;

	public String fileLocation;

	Timer timer;

	public void Stop() {
		if (player != null) {
			timer.cancel();
			player.close();

			pauseLocation = 0;
			songTotalLength = 0;

		}
	}

	public void Pause() {
		if (timer != null) timer.cancel();
		if (player != null) {
			try {
				pauseLocation = FIS.available();
				player.close();
			} catch (IOException ex) {

			}
		}
	}

	public void Resume() {
		try {
			FIS = new FileInputStream(fileLocation);
			BIS = new BufferedInputStream(FIS);

			player = new Player(BIS);

			FIS.skip(songTotalLength - pauseLocation);
			if (timer != null) timer.cancel();

			timer = new Timer();
			timer.schedule(new TrackTimer(), 0, 1000);
		} catch (FileNotFoundException | JavaLayerException ex) {

		} catch (IOException ex) {

		}

		new Thread() {
			@Override
			public void run() {
				try {
					player.play();
				} catch (JavaLayerException ex) {

				}
			}
		}.start();

	}

	public void Resume(Song song) {
		try {
			
			FIS = new FileInputStream(fileLocation);
			BIS = new BufferedInputStream(FIS);

			player = new Player(BIS);
			if (timer != null) timer.cancel();

			timer = new Timer();
			timer.schedule(new TrackTimer(), 0, 1000);

			long framesPerSec = songTotalLength/song.totalSecs;
			long framesToSkip = framesPerSec * MP3PlayerGUI.sldrTrackSlider.getValue();
			FIS.skip(framesToSkip);
			MP3PlayerGUI.sldrTrackSlider.setValue(MP3PlayerGUI.sldrTrackSlider.getValue());
		} catch (FileNotFoundException | JavaLayerException ex) {

		} catch (IOException ex) {

		}

		new Thread() {
			@Override
			public void run() {
				try {
					player.play();
				} catch (JavaLayerException ex) {

				}
			}
		}.start();

	}
	public void Play(String path) {
		try {
			FIS = new FileInputStream(path);
			BIS = new BufferedInputStream(FIS);

			player = new Player(BIS);

			songTotalLength = FIS.available();

			fileLocation = path + "";
			if (timer != null) timer.cancel();

			timer = new Timer();
			timer.schedule(new TrackTimer(), 0, 1000);
		} catch (FileNotFoundException | JavaLayerException ex) {

		} catch (IOException ex) {

		}

		new Thread() {
			@Override
			public void run() {
				try {
					player.play();

					if (MP3PlayerGUI.sldrTrackSlider.getValue() >= MP3PlayerGUI.currentPL.get(MP3PlayerGUI.index).totalSecs) {
						if (MP3PlayerGUI.count == 1) {
							Play(fileLocation);
						} else if (MP3PlayerGUI.count == 0) {
							if (MP3PlayerGUI.index <= MP3PlayerGUI.currentPL.size()) {
								MP3PlayerGUI.index++;
								Play(MP3PlayerGUI.currentPL.get(MP3PlayerGUI.index).file.getAbsolutePath());
							}else if (MP3PlayerGUI.count == 2) {
							}
						}
					}
				} catch (JavaLayerException ex) {

				}
			}
		}.start();
	}

	public void FastForward() {
		try {

			FIS.skip((long) ((songTotalLength)));
		} catch (IOException ex) {

		}
	}

}