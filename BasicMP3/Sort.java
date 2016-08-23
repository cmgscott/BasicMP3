import java.io.File;
import java.util.ArrayList;

public class Sort{
	public static void main(String[] args) {
		FileImport media = new FileImport();
		ArrayList<File> arrayList= media.importMedia("E:\\samples");

		//All Songs...
		System.out.println("ALL SONGS...");
		for(File result : arrayList) {
			System.out.println(result);
		}
    	
		//Shuffled List...
		System.out.println("\nSHUFFLED LIST...");
		ArrayList<File> al = media.shuffle(arrayList);
		for(File result : al) {
			System.out.println(result);
		}
		
		//Custom PlayList...
		System.out.println("\nCUSTOM PLAYLIST...");
		//File song1 = media.addToPL();
		//File song2 = media.addToPL();
		//File song3 = media.addToPL();
		//media.removeFromPL(song2);
		ArrayList<File> playList = media.getPlayList();
		for(File result : playList) {
			System.out.println(result);
		}
		media.clearPL();
		System.out.println("\nAFTER CLEARING...");
		ArrayList<File> cleared = media.getPlayList();
		for(File result : cleared) {
			System.out.println(result);
		}
	}
}
