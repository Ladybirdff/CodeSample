/**
 * Hw 12
 * Authors: Kexin Huang & Weixi Ma
 */
package songs;

/*
 * Music Player
 *
 * This instructor-provided file implements the graphical user interface (GUI)
 * for the Music Player program and allows you to test the behavior
 * of your Song class.
 */

//import guiprograms.GraphicFractions;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MusicPlayer implements ActionListener, StdAudio.AudioEventListener {

	// instance variables
	private Song song;
	private boolean playing; // whether a song is currently playing
	private JFrame frame;
	private JLabel labelTempo, showInfo, buttonInfo, imageLabel;
	private JPanel panel1, panel2, panel3, sliderPanel, buttonPanel1, buttonPanel2, textPanel, imagePanel;
	private JButton play, stop, load, reverse, up, down, change, pause;
	private JFileChooser fileChooser;
	private JTextField tempoText;
	private JSlider currentTimeSlider;
	private ActionListener listener = this;

	//these are the two labels that indicate time
	// to the right of the slider
	private JLabel currentTimeLabel;
	private JLabel totalTimeLabel;

	//this the label that says 'welcome to the music player'
	private JLabel statusLabel; 

	/*
	 * Creates the music player GUI window and graphical components.
	 */
	public MusicPlayer() {
		song = null;
		createComponents();
		doLayout();
		StdAudio.addAudioEventListener(this);
		frame.setVisible(true);
		frame.pack();
	}

	/*
	 * Called when the user interacts with graphical components, such as
	 * clicking on a button.
	 */
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		if (cmd.equals("Play")) {
			playSong();
			changeButtonInfo(cmd);
		} else if (cmd.equals("Pause")) {
			StdAudio.setPaused(!StdAudio.isPaused());
			changeButtonInfo(cmd);
		} else if (cmd == "Stop") {
			StdAudio.setMute(true);
			StdAudio.setPaused(false);
			changeButtonInfo(cmd);
		} else if (cmd == "Load") {
			try {
				loadFile();
				changeButtonInfo(cmd);
			} catch (IOException ioe) {
				System.out.println("not able to load from the file");
			}
		} else if (cmd == "Reverse") {
			song.reverse();
			changeButtonInfo(cmd);
		} else if (cmd == "Up") {
			song.octaveUp();
			changeButtonInfo(cmd);
		} else if (cmd == "Down") {
			song.octaveDown();
			changeButtonInfo(cmd);
		} else if (cmd == "Change") {
			System.out.println(tempoText.getText());
			song.changeTempo(Double.valueOf(tempoText.getText()));
			updateTotalTime();
			changeButtonInfo(cmd);
		}
	}

	/*
	 * Called when audio events occur in the StdAudio library. We use this to
	 * set the displayed current time in the slider.
	 */
	public void onAudioEvent(StdAudio.AudioEvent event) {
		// update current time
		if (event.getType() == StdAudio.AudioEvent.Type.PLAY
				|| event.getType() == StdAudio.AudioEvent.Type.STOP) {
			setCurrentTime(getCurrentTime() + event.getDuration());
		}
	}

	/*
	 * Sets up the graphical components in the window and event listeners.
	 */

	private void createComponents() {
		// create all your components here.
		// note that you should have already defined your components as instance variables.
		frame = new JFrame("Music Player");
		
		// Panel 1 components
		panel1 = new JPanel();
		statusLabel = new JLabel("Welcome to Music Player");
		currentTimeSlider = new JSlider();
		totalTimeLabel = new JLabel();
		currentTimeLabel = new JLabel();
		sliderPanel = new JPanel();

		// Panel 2 components
		panel2 = new JPanel();
		play = new JButton("Play");
		play.addActionListener(listener);
		stop = new JButton("Stop");
		stop.addActionListener(listener);
		load = new JButton("Load");
		load.addActionListener(listener);
		reverse = new JButton("Reverse");
		reverse.addActionListener(listener);
		up = new JButton("Up");
		up.addActionListener(listener);
		down = new JButton("Down");
		down.addActionListener(listener);
		pause = new JButton("Pause");
		pause.addActionListener(listener);
		buttonPanel1 = new JPanel();
		buttonPanel2 = new JPanel();

		//Panel 3 components
		panel3 = new JPanel();
		labelTempo = new JLabel("Tempo: ");
		tempoText = new JTextField(15);
		change = new JButton("Change");
		change.addActionListener(listener);
		fileChooser = new JFileChooser();

		//textPanel components
		textPanel = new JPanel();
		showInfo = new JLabel("Status:");
		buttonInfo = new JLabel("Please load a song file first!");
		
		//imagePanel components
		imagePanel = new JPanel();
		imageLabel = new JLabel();
    	ImageIcon icon = new ImageIcon("melody.png");//create image from existing png file 
    	Image image= icon.getImage();
    	Image newimg= image.getScaledInstance(200, 100, java.awt.Image.SCALE_SMOOTH);
    	ImageIcon newiconLogo = new ImageIcon(newimg);
    	imageLabel.setIcon(newiconLogo);
		
		doEnabling();
	}

	/*
	 * Sets whether every button, slider, spinner, etc. should be currently
	 * enabled, based on the current state of whether a song has been loaded and
	 * whether or not it is currently playing. This is done to prevent the user
	 * from doing actions at inappropriate times such as clicking play while the
	 * song is already playing, etc.
	 */
	private void doEnabling() {
		//figure out which buttons need to enabled
		if (song == null){ //initial state without song
			play.setEnabled(false);
			reverse.setEnabled(false);
			stop.setEnabled(false);
			up.setEnabled(false);
			down.setEnabled(false);
			change.setEnabled(false);
			load.setEnabled(true);
			pause.setEnabled(false);
		}
		else if(this.playing){ // during playing
			play.setEnabled(false);
			reverse.setEnabled(false);
			stop.setEnabled(true);
			up.setEnabled(false);
			down.setEnabled(false);
			change.setEnabled(false);
			load.setEnabled(false);
			pause.setEnabled(true);
		}
		else{ //song is not playing
			play.setEnabled(true);
			reverse.setEnabled(true);
			stop.setEnabled(false);
			up.setEnabled(true);
			down.setEnabled(true);
			change.setEnabled(true); 
			load.setEnabled(true);
			pause.setEnabled(true);
		}
	}

	/*
	 * Performs layout of the components within the graphical window. 
	 * Also make the window a certain size and put it in the center of the screen.
	 */
	private void doLayout() {
		//figure out how to layout the components
		final int FRAME_WIDTH = 800;
		final int FRAME_HEIGHT = 300;

		//Set the size of frame
		frame.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.setLayout(new BorderLayout());
		
		//Set exit when closed.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		// Set window in the middle of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		// panel1 (upper panel) layouts
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		frame.add(panel1, BorderLayout.NORTH);
		final int PANEL1_WIDTH = 800;
		final int PANEL1_HEIGHT = 100;
		panel1.setSize(PANEL1_WIDTH, PANEL1_HEIGHT);
		currentTimeSlider.setPreferredSize(new Dimension(600,100));
		final int SLIDERPANEL_WIDTH = 600;
		final int SLIDERPANEL_HEIGHT = 50;
		sliderPanel.setSize(SLIDERPANEL_WIDTH, SLIDERPANEL_HEIGHT);
		currentTimeSlider.setPreferredSize(new Dimension(580,100));
		currentTimeSlider.setPaintTicks(true);
		currentTimeSlider.setPaintLabels(false);
		currentTimeSlider.setMajorTickSpacing(10);
		currentTimeSlider.setMinorTickSpacing(5);
		currentTimeSlider.setValue(0);
		sliderPanel.add(currentTimeSlider);
		panel1.add(statusLabel);
		statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel1.add(sliderPanel);
		sliderPanel.add(currentTimeLabel);
		currentTimeLabel.setText("0.000 /");
		totalTimeLabel.setText("0.000 sec");
		sliderPanel.add(totalTimeLabel);
		currentTimeSlider.setOrientation(SwingConstants.HORIZONTAL);

		// panel2 (middle panel) layouts
		final int PANEL2_WIDTH = 800;
		final int PANEL2_HEIGHT = 100;
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel2.setSize(PANEL2_WIDTH, PANEL2_HEIGHT);
		frame.add(panel2, BorderLayout.CENTER);
		buttonPanel1.add(play);
		buttonPanel1.add(pause);
		buttonPanel1.add(stop);
		buttonPanel1.add(load);
		buttonPanel1.add(showInfo);
		buttonPanel2.add(reverse);
		buttonPanel2.add(up);
		buttonPanel2.add(down);
		panel2.add(buttonPanel1);
		panel2.add(buttonPanel2);
		
		// panel3 (bottom panel) layouts
		final int PANEL3_WIDTH = 200;
		final int PANEL3_HEIGHT = 100;
		panel3.setSize(PANEL3_WIDTH,PANEL3_HEIGHT);
		frame.add(panel3, BorderLayout.SOUTH);
		panel3.add(labelTempo);
		panel3.add(tempoText);
		panel3.add(change);
		
		//textPanel (east panel) layouts
		frame.add(textPanel, BorderLayout.EAST);
		textPanel.setLayout(new BorderLayout());
		textPanel.setPreferredSize(new Dimension(200, 100));
		textPanel.add(showInfo, BorderLayout.NORTH);
		textPanel.add(buttonInfo, BorderLayout.CENTER);	
		
		//imagePanel(west panel) layouts
		frame.add(imagePanel, BorderLayout.WEST);
		imagePanel.setPreferredSize(new Dimension(200, 100));
		imagePanel.add(imageLabel);
	}

	/*
	 * Returns the estimated current time within the overall song, in seconds.
	 */
	private double getCurrentTime() {
		String timeStr = currentTimeLabel.getText();
		timeStr = timeStr.replace(" /", "");
		try {
			return Double.parseDouble(timeStr);
		} catch (NumberFormatException nfe) {
			return 0.0;
		}
	}

	/*
	 * Pops up a file-choosing window for the user to select a song file to be
	 * loaded. If the user chooses a file, a Song object is created and used
	 * to represent that song.
	 */
	private void loadFile() throws IOException {
		if (fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selected = fileChooser.getSelectedFile();
		if (selected == null) {
			return;
		}
		statusLabel.setText("Current song: " + selected.getName());
		String filename = selected.getAbsolutePath();
		System.out.println("Loading song from " + selected.getName() + " ...");

		//create a song from the file
		song = new Song(filename);

		tempoText.setText("1.0");
		setCurrentTime(0.0);
		updateTotalTime();
		System.out.println("Loading complete.");
		System.out.println("Song: " + song);
		doEnabling();
	}

	/*
	 * Initiates the playing of the current song in a separate thread (so
	 * that it does not lock up the GUI). 
	 * You do not need to change this method.
	 * It will not compile until you make your Song class.
	 */
	private void playSong() {
		if (song != null) {
			setCurrentTime(0.0);
			Thread playThread = new Thread(new Runnable() {
				public void run() {
					StdAudio.setMute(false);
					playing = true;
					doEnabling();
					String title = song.getTitle();
					String artist = song.getArtist();
					double duration = song.getTotalDuration();
					System.out.println("Playing \"" + title + "\", by "
							+ artist + " (" + duration + " sec)");
					song.play();
					System.out.println("Playing complete.");
					playing = false;
					doEnabling();
				}
			});
			playThread.start();
		}
	}

	/*
	 * Sets the current time display slider/label to show the given time in
	 * seconds. Bounded to the song's total duration as reported by the song.
	 */
	private void setCurrentTime(double time) {
		double total = song.getTotalDuration();
		time = Math.max(0, Math.min(total, time));
		currentTimeLabel.setText(String.format("%08.2f /", time));
		currentTimeSlider.setValue((int) (100 * time / total));
	}

	/*
	 * Updates the total time label on the screen to the current total duration.
	 */
	private void updateTotalTime() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		this.totalTimeLabel.setText(String.valueOf(twoDForm.format(song.getTotalDuration()))+" sec");
	}

	/**
	 * change the current information shown on the player indicating status
	 * @param str
	 */
	private void changeButtonInfo(String str){
		if (str.equals("Load")){
			buttonInfo.setText("You have loaded a song.");
		}
		else if (str.equals("Play")){
			buttonInfo.setText("Enjoy the music!");
		}
		else if (str.equals("Pause")){
			if (StdAudio.isPaused()){
				buttonInfo.setText("Click 'Pause' again to continue.");
			}
			else {
				buttonInfo.setText("Enjoy the music!");
			}
		}
		else if (str.equals("Stop")){
			buttonInfo.setText("Stop.");
		}
		else if (str.equals("Change")){
			buttonInfo.setText("The tempo has been changed.");
		}
		else if (str.equals("Up")){
			buttonInfo.setText("1 octave higher.");
		}
		else if (str.equals("Down")){
			buttonInfo.setText("1 octave lower.");
		}
		else if (str.equals("Reverse")){
			buttonInfo.setText("Reverse the song.");
		}
	}
	
}
