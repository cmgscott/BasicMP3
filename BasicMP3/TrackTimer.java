import java.util.TimerTask;

public class TrackTimer extends TimerTask {
	
	public int secs = MP3PlayerGUI.secs;// = 0;
	public int mins = MP3PlayerGUI.mins;// = 0;
	int trackSliderValue = 0;
	

	@Override
	public void run() {
		if (MP3PlayerGUI.isPlaying) {
			secs = 0;
			mins = 0;
		
		} 
		secs++;
		MP3PlayerGUI.secs = this.secs;
		MP3PlayerGUI.mins = this.mins;
		if (secs >= 60) {
			mins++;
			secs = 0;
		}
		MP3PlayerGUI.lblMin.setText(String.format("%d", mins));

		if (secs < 10) MP3PlayerGUI.lblSec.setText(String.format("0%d", secs));
		else MP3PlayerGUI.lblSec.setText(String.format("%d", secs));
		trackSliderValue = MP3PlayerGUI.sldrTrackSlider.getValue();
		trackSliderValue++;
		MP3PlayerGUI.sldrTrackSlider.setValue(trackSliderValue);
		
		
		
	}

}
