import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;
import org.tritonus.share.sampled.file.TAudioFileFormat;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.Component;


public class MP3PlayerGUI {
	FileImport media = new FileImport();
	ArrayList<File> playList;
	ArrayList<File> al;
	ArrayList<File> current;
	MainClass MC = new MainClass();
	Sort SL = new Sort();
	FileImport FI = new FileImport();
	public static int count = 0;

	private JFrame frmBasicMp;
	static JTextField Display;
	String path;
	static int index = 0;
	Path loc;
	String name;
	JButton btnOpen;
	JButton btnLoop;
	JLabel label;
	JSlider Vslid;
	JLabel lblNewLabel;
	JSlider Tslid;
	JButton btnAdd;
	JButton btnCustom;
	JLabel lblNewLabel_1;
	JTextField txtPath;
	JButton btnPath;
	JButton btnAddPlay;
	JButton btnShufflePlay;
	JButton btnCustomPlay;
	private JScrollPane scrollPane;
	private JList lstPLDisplay;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmAddFile;
	private JMenuItem mntmAddDirectory;
	private JMenuItem mntmExit;
	File theFile;
	static ArrayList<Song> currentPL = new ArrayList<Song>();
	File folder;
	File[] filesInFolder;
	Song selectedSong;
	private JLabel lblSongName;
	private JLabel lblSongLength;
	int totalSecs;
	static JSlider sldrTrackSlider;
	static boolean isPlaying;
	JComboBox cmbbxSortBy;
	private JComboBox cmbbxPlaylistList;
	ArrayList<PlayList> allPlaylists = new ArrayList<PlayList>();
	private JLabel lblTotalPlaylistLength;
	private JLabel lblPlaylistLength;
	TrackTimer tt;
	static JLabel lblMin;
	static JLabel lblSec;
	private JLabel label_1;
	JLabel lblAlbumArt;
	private JLabel lblBackground;
	public static int mins;
	public static int secs;
	int loopCounter;
	Image loop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MP3PlayerGUI window = new MP3PlayerGUI();
					window.frmBasicMp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MP3PlayerGUI() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmBasicMp = new JFrame();
		frmBasicMp.setTitle("Basic MP3");
		frmBasicMp.setBounds(100, 100, 978, 563);
		frmBasicMp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBasicMp.getContentPane().setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 285, 383);
		frmBasicMp.getContentPane().add(scrollPane);

		lstPLDisplay = new JList();
		scrollPane.setViewportView(lstPLDisplay);
		Color lstBackground = new Color(179, 209, 255);
		lstPLDisplay.setBackground(lstBackground);
		lstPLDisplay.setSelectionBackground(Color.LIGHT_GRAY);
		lstPLDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				e.setSource(lstPLDisplay);
				if (e.getClickCount() == 2) {
					MC.Stop();
					secs = 0;
					lblSongName.setText(selectedSong.name);
					lblSongLength.setText(selectedSong.length);
					MC.Play((selectedSong.file).getAbsolutePath());
					index = currentPL.indexOf(selectedSong);
					String albumArtPath = String.format("\\albumArt\\%s.jpg", MP3PlayerGUI.currentPL.get(MP3PlayerGUI.index).name);
					Image albumArt = new ImageIcon(this.getClass().getResource(albumArtPath)).getImage();
					lblAlbumArt.setIcon(new ImageIcon(albumArt));
				
					setTrackMax();
					sldrTrackSlider.setValue(0);
				}
			}
		});
		lstPLDisplay.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (lstPLDisplay.getSelectedValue() != null) {
					selectedSong = (Song) lstPLDisplay.getSelectedValue();
				}
			}

		});

		lblSongName = new JLabel("");
		lblSongName.setForeground(Color.WHITE);
		lblSongName.setBounds(433, 343, 260, 14);
		frmBasicMp.getContentPane().add(lblSongName);

		lblSongLength = new JLabel("");
		lblSongLength.setForeground(Color.WHITE);
		lblSongLength.setBounds(781, 343, 76, 14);
		frmBasicMp.getContentPane().add(lblSongLength);

		lblAlbumArt = new JLabel("");
		lblAlbumArt.setBounds(577, 11, 250, 250);
		frmBasicMp.getContentPane().add(lblAlbumArt);

		cmbbxPlaylistList = new JComboBox();
		cmbbxPlaylistList.setBackground(lstBackground);
		cmbbxPlaylistList.setModel(new DefaultComboBoxModel(new String[] {"Playlists appear here"}));
		cmbbxPlaylistList.setMaximumRowCount(80);
		cmbbxPlaylistList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlayList temp = (PlayList) cmbbxPlaylistList.getSelectedItem();
				currentPL = temp.playlistSongs;
				updateDisplay(currentPL);
			}
		});
		cmbbxPlaylistList.setBounds(10, 11, 285, 20);
		frmBasicMp.getContentPane().add(cmbbxPlaylistList);

		lblTotalPlaylistLength = new JLabel("Total Playlist Length:");
		lblTotalPlaylistLength.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTotalPlaylistLength.setForeground(Color.WHITE);
		lblTotalPlaylistLength.setBounds(3, 451, 190, 35);
		frmBasicMp.getContentPane().add(lblTotalPlaylistLength);

		lblPlaylistLength = new JLabel("0:00");
		lblPlaylistLength.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPlaylistLength.setForeground(Color.WHITE);
		lblPlaylistLength.setBounds(242, 451, 118, 35);
		frmBasicMp.getContentPane().add(lblPlaylistLength);



		sldrTrackSlider = new JSlider();
		sldrTrackSlider.setOpaque(false);
		sldrTrackSlider.setBackground(lstBackground);
		sldrTrackSlider.setValue(0);
		sldrTrackSlider.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MC.Pause();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				MC.Resume(currentPL.get(index));

			}
		});
		sldrTrackSlider.setBounds(552, 309, 286, 23);
		frmBasicMp.getContentPane().add(sldrTrackSlider);

		lblMin = new JLabel("0");
		lblMin.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMin.setForeground(Color.WHITE);
		lblMin.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMin.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblMin.setBounds(718, 284, 32, 14);
		frmBasicMp.getContentPane().add(lblMin);

		lblSec = new JLabel("00");
		lblSec.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSec.setForeground(Color.WHITE);
		lblSec.setBounds(781, 284, 46, 14);
		frmBasicMp.getContentPane().add(lblSec);

		label_1 = new JLabel(":");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(760, 284, 32, 14);
		frmBasicMp.getContentPane().add(label_1);

		final JLabel PLAY = new JLabel("");
		PLAY.addMouseListener(new BtnPlayMouseListener());
		//Image play = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Play.png")).getImage();
		ImageIcon play = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Play.png"));
		PLAY.setIcon(play);
		PLAY.setBounds(607, 375, 164, 50);
		frmBasicMp.getContentPane().add(PLAY);

		final JLabel STOP = new JLabel("");
		STOP.addMouseListener(new BtnStopMouseListener());
		ImageIcon stop = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Stop.png"));
		STOP.setIcon(stop);
		frmBasicMp.getContentPane().add(STOP);
		STOP.setBounds(684, 436, 164, 50);
		frmBasicMp.getContentPane().add(STOP);

		final JLabel FORWARD = new JLabel("");
		FORWARD.addMouseListener(new BtnForwardMouseListener());
		Image forward = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Forward.png")).getImage();
		FORWARD.setIcon(new ImageIcon(forward));
		FORWARD.setBounds(781, 375, 164, 50);
		frmBasicMp.getContentPane().add(FORWARD);

		final JLabel BACK = new JLabel("");
		BACK.addMouseListener(new BtnReverseMouseListener());
		Image back = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Reverse.png")).getImage();
		BACK.setIcon(new ImageIcon(back));
		BACK.setBounds(433, 375, 164, 50);
		frmBasicMp.getContentPane().add(BACK);

		final JLabel LOOP = new JLabel("");
		loop = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Loop (Off).png")).getImage();
		LOOP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loopCounter++;
				if (loopCounter > 2) loopCounter = 0;

				if (loopCounter == 0) {
					loop = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Loop (Off).png")).getImage();
				} else if (loopCounter == 1) {
					count = 1;
					loop = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Loop(One Song).png")).getImage();
				} else if (loopCounter == 2) {
					count = 2;
					loop = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Loop.png")).getImage();
				}

				LOOP.setIcon(new ImageIcon(loop));

			}
		});
		LOOP.setIcon(new ImageIcon(loop));
		LOOP.setBounds(310, 225, 164, 50);
		frmBasicMp.getContentPane().add(LOOP);

		final JLabel CREATE_PLAYLIST = new JLabel("");
		Image createPlaylist = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Create Playlist.png")).getImage();
		CREATE_PLAYLIST.setIcon(new ImageIcon(createPlaylist));
		CREATE_PLAYLIST.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentPL = new ArrayList<Song>();
				updateDisplay(currentPL);
			}
		});
		CREATE_PLAYLIST.setBounds(310, 42, 164, 50);
		frmBasicMp.getContentPane().add(CREATE_PLAYLIST);

		final JLabel SHUFFLE_PLAYLIST = new JLabel("");
		Image shufflePlaylist = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Shuffle.png")).getImage();
		SHUFFLE_PLAYLIST.setIcon(new ImageIcon(shufflePlaylist));
		SHUFFLE_PLAYLIST.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Collections.shuffle(currentPL);
				index = currentPL.indexOf(selectedSong);
			}
		});
		SHUFFLE_PLAYLIST.setBounds(310, 164, 164, 50);
		frmBasicMp.getContentPane().add(SHUFFLE_PLAYLIST);

		final JLabel SAVE_PLAYLIST = new JLabel("");
		Image savePlaylist = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Save.png")).getImage();
		SAVE_PLAYLIST.setIcon(new ImageIcon(savePlaylist));
		SAVE_PLAYLIST.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String playlistName = JOptionPane.showInputDialog("Name of new playlist?");
				PlayList newList = new PlayList(playlistName);
				newList.playlistSongs.addAll(currentPL);
				allPlaylists.add(0, newList);
				updatePLCmbbx();
				JOptionPane.showMessageDialog(null, "Playlist Saved!");
			}
		});
		SAVE_PLAYLIST.setBounds(310, 103, 164, 50);
		frmBasicMp.getContentPane().add(SAVE_PLAYLIST);

		final JLabel PAUSE = new JLabel("");
		PAUSE.addMouseListener(new BtnPauseMouseListener());
		Image pause = new ImageIcon(this.getClass().getResource("\\Buttons\\Button - Pause.png")).getImage();
		PAUSE.setIcon(new ImageIcon(pause));
		PAUSE.setBounds(510, 436, 164, 50);
		frmBasicMp.getContentPane().add(PAUSE);

		lblBackground = new JLabel("");
		Image background = new ImageIcon(this.getClass().getResource("\\Buttons\\background.png")).getImage();
		lblBackground.setIcon(new ImageIcon(background));
		lblBackground.setBounds(-103, 1, 1065, 511);
		frmBasicMp.getContentPane().add(lblBackground);

		menuBar = new JMenuBar();
		frmBasicMp.setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmAddFile = new JMenuItem("Add File");
		mntmAddFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(theFile);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 File", "MP3");
				int userChoice = chooser.showOpenDialog(null);
				chooser.setFileFilter(filter);
				if (userChoice == JFileChooser.APPROVE_OPTION) {
					theFile = chooser.getSelectedFile();
					if (FilenameUtils.getExtension(theFile.getAbsolutePath()).equals("mp3")); {
						currentPL.add(new Song(theFile));
					}
				}
				updateDisplay(currentPL);
			}
		});
		mnFile.add(mntmAddFile);

		mntmAddDirectory = new JMenuItem("Add Directory");
		mntmAddDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(theFile);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 File", "MP3");
				chooser.addChoosableFileFilter(filter);
				int userChoice = chooser.showOpenDialog(null);
				if (userChoice == JFileChooser.APPROVE_OPTION) {
					folder = chooser.getSelectedFile();
					filesInFolder = folder.listFiles();
					theFile = chooser.getSelectedFile();
					int i = 0;
					for(File file : filesInFolder) {
						if (FilenameUtils.getExtension(file.getAbsolutePath()).contains("mp3")) {
							currentPL.add(new Song(file));
							i++;
						}
					}
				}
				updateDisplay(currentPL);
			}
		});
		mnFile.add(mntmAddDirectory);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Thanks, goodbye!");
				System.exit(1);
			}
		});
		mnFile.add(mntmExit);
	}

	private class BtnStopMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			MC.Stop();
			////timer.cancel();
		}
	}

	private class BtnPlayMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			String albumArtPath = String.format("\\albumArt\\%s.jpg", MP3PlayerGUI.currentPL.get(MP3PlayerGUI.index).name);
				Image albumArt = new ImageIcon(this.getClass().getResource(albumArtPath)).getImage();
				lblAlbumArt.setIcon(new ImageIcon(albumArt));
			
			if (isPlaying == true) {
				MC.Resume();
				isPlaying = false;
			}
			else if (!isPlaying) {
				mins = 0;
				secs = 0;
				if (selectedSong != null) {
					MC.Stop();
					MC.Play(selectedSong.file.getAbsolutePath());
				} else if (currentPL != null) {
					MC.Stop();
					MC.Play(currentPL.get(index).file.getAbsolutePath());
					changeSongDisplay();
				}

				selectedSong = currentPL.get(index);
				setTrackMax();
				sldrTrackSlider.setValue(0);
			}
			changeSongDisplay();
		}
	}

	private class BtnPauseMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			MC.Pause();
			isPlaying = true;

		}
	}

	private class BtnForwardMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			MC.Stop();
			mins = 0;
			secs = 0;
			//timer.cancel();
			index++;
			if (index > currentPL.size() - 1) index = 0;
			sldrTrackSlider.setValue(0);
			MC.Play(currentPL.get(index).file.getAbsolutePath());
			changeSongDisplay();
			String albumArtPath = String.format("\\albumArt\\%s.jpg", MP3PlayerGUI.currentPL.get(MP3PlayerGUI.index).name);
				Image albumArt = new ImageIcon(this.getClass().getResource(albumArtPath)).getImage();
				lblAlbumArt.setIcon(new ImageIcon(albumArt));
			setTrackMax();
			selectedSong = currentPL.get(index);

		}
	}






	private class BtnReverseMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			MC.Stop();
			mins = 0;
			secs = 0;
			//timer.cancel();
			index--;
			if (index < 0) index = currentPL.size()-1;
			MC.Play(currentPL.get(index).file.getAbsolutePath());
			changeSongDisplay();String albumArtPath = String.format("\\albumArt\\%s.jpg", MP3PlayerGUI.currentPL.get(MP3PlayerGUI.index).name);
				Image albumArt = new ImageIcon(this.getClass().getResource(albumArtPath)).getImage();
				lblAlbumArt.setIcon(new ImageIcon(albumArt));
			
			setTrackMax();
			selectedSong = currentPL.get(index);
			sldrTrackSlider.setValue(0);

		}
	}
	
	private class BtnShufflePlayActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			index = 0;
			MC.Stop();
			loc = Paths.get("" + al.get(index));
			name  = loc.getFileName().toString();
			Display.setText(name);
			MC.Play("" + al.get(index));
			current = al;
		}
	}
	private class BtnCustomPlayActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			index = 0;
			MC.Stop();
			loc = Paths.get("" + playList.get(index));
			name  = loc.getFileName().toString();
			Display.setText(name);
			MC.Play("" + playList.get(index));
			current = playList;
		}

	}
	private static void getLength(File file) throws UnsupportedAudioFileException, IOException {

		AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
		if (fileFormat instanceof TAudioFileFormat) {
			Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
			String key = "duration";
			Long microseconds = (Long) properties.get(key);
			int mili = (int) (microseconds / 1000);
			int sec = (mili / 1000) % 60;
			int min = (mili / 1000) / 60;
		} else {
			throw new UnsupportedAudioFileException();
		}

	}
	public void updateDisplay(ArrayList<Song> songPL) {
		currentPL = songPL;
		lstPLDisplay.setModel(new AbstractListModel() {

			public int getSize() {
				return currentPL.size();
			}
			public Object getElementAt(int index) {
				return currentPL.get(index);
			}
		});
		int totalPlTime = 0;
		for (int i = 0; i < currentPL.size(); i++) {
			totalPlTime += currentPL.get(i).totalSecs;
		}
		int mins = totalPlTime / 60;
		int secs = totalPlTime % 60;
		lblPlaylistLength.setText(String.format("%d:%d", mins, secs));
	}
	public void changeSongDisplay() {
		lblSongName.setText(currentPL.get(index).name);
		lblSongLength.setText(currentPL.get(index).length);
	}
	public void updatePLCmbbx() {
		cmbbxPlaylistList.setModel(new DefaultComboBoxModel(allPlaylists.toArray()));
	}
	public void setTrackMax() {

		sldrTrackSlider.setMaximum(currentPL.get(index).totalSecs);
		sldrTrackSlider.setMinimum(0);
	}
}

