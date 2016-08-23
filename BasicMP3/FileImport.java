import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileImport {
	ArrayList<File> playList = new ArrayList<File>();
	
	public ArrayList<File> importMedia(String directory) {
		File[] files = new File(directory).listFiles();
    	ArrayList<File> arrayList = new ArrayList<File>();
    	for(int i = 0; i < files.length; i++) {
    		arrayList.add(files[i]);
    	}
    	Collections.sort(arrayList);
        return arrayList;
    }
    /*public void showFiles(File[] files) {
        String name;
        for (File file : files) {
            if (file.isDirectory()) {
                name = file.getName();
                if (!(name.contains("!")) && name.contains("(") && name.contains(")")) {
                    System.out.println("Directory: " + name);
                }
                showFiles(file.listFiles());
            } else {
                System.out.println("File: " + file.getName());

            }
        }
    }*/
    public ArrayList<File> shuffle(ArrayList<File> files) {
    	Collections.shuffle(files);
		return files;
    }
    public File addToPL() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3 & wav Images", "wav", "mp3");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal != JFileChooser.APPROVE_OPTION) {
			System.exit(4); //or return;
		}
		File theFile = chooser.getSelectedFile();
		playList.add(theFile);
		return theFile;
    }
    public void removeFromPL(File name) {
    	playList.remove(name);
    }
    public void clearPL() {
    	playList.clear();
    }
	/**
	 * @return the playlist
	 */
	public ArrayList<File> getPlayList() {
		return playList;
	} 
}