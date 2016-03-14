/**
 * Hw 12
 * Authors: Kexin Huang & Weixi Ma
 */

package songs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Song{

	private Note[] notesArray;
	private String title;
	private String artist;
	private double totalDuration;
	private int length;
	
	/**
	 * Create a song from a file, at the same time, save the information in totalDuration, length, title, and artist
	 * @param filename
	 */
	public Song(String filename){
		File file = new File(filename);
		try {
			//if the file exist, use the information of it to create Notes
			Scanner sc = new Scanner(file);
			title = sc.nextLine();  //title
			artist = sc.nextLine();  //artist
			String num = sc.nextLine();//length
			totalDuration = 0;         // total duration
			length = Integer.valueOf(num);
			notesArray = new Note[length];
			int count = 0;
			int start = -1;//mark the start point of a repeat section
			int end = -1;  //mark the end of a repeat section
			int repeatnum = 0; //help to distinguish it is a start or an end of a repeat
			while (sc.hasNextLine()) {
				double duration;
				Pitch pitch;
				int octave;
				boolean repeat;
				Accidental accidental;
				Note note;
				String i = sc.nextLine();
				String[] strList = i.split(" ");
				duration = Double.valueOf(strList[0]);
				pitch = Pitch.valueOf(strList[1]);
				if (pitch.equals(Pitch.R)){
					repeat = Boolean.valueOf(strList[2]);
					note = new Note(duration, repeat);
				}
				else{
					octave = Integer.valueOf(strList[2]);
					accidental = Accidental.valueOf(strList[3]);
					repeat = Boolean.valueOf(strList[4]);
					note = new Note(duration, pitch, octave, accidental, repeat);
				}
				notesArray[count] = note;
				totalDuration += duration;
				if ((repeat) && (repeatnum % 2 ==0)){
					start = count;
					repeatnum += 1;
				}
				else if ((repeat) && (repeatnum % 2 != 0)){
					end = count;
					repeatnum += 1;
					for (int c = start; c <= end; c++){
						//add the repeat section's duration to the total duration
						totalDuration += notesArray[c].getDuration();
					}
				}
				count++;
			}
			sc.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the title of the song
	 * @return
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * get the artist of the song
	 * @return
	 */
	public String getArtist(){
		return artist;
	}

	/**
	 * get the total duration of the song
	 * @return
	 */
	public double getTotalDuration(){
		return totalDuration;
	}

	/**
	 * get the array collection of the song
	 * @return
	 */
	public Note[] getNotesArray(){
		return notesArray;
	}
	
	/**
	 * play the music
	 */
	public void play(){

		int start =-1;
		int end =-1;
		int count =0;
		for (int i=0; i<notesArray.length; i++){
			notesArray[i].play();
			if (notesArray[i].isRepeat() && count % 2 ==0){
				start = i; // mark the first repeating point
				count ++;
			}
			else if (notesArray[i].isRepeat() && count % 2 ==1){
				end = i; // mark the second repeating point
				for (int c=start; c<=end; c++){ // repeat the repeating period
					notesArray[c].play();
				}
				count ++;
			}
		}
	}

	/**
	 * Check whether the Octave hit a special case, which is that the octave is 1 when use octaveDown
	 * or the octave is 10 when use octaveUp
	 * @param max
	 * @return
	 */
	public boolean checkOctave(boolean max){
		if (max){
			for (Note i : notesArray){
				if (!i.isRest()){
					if (i.getOctave() >= 10){
						return false;
					}
				}
			}
			return true;
		}
		else {
			for (Note i : notesArray){
				if (!i.isRest()){
					if (i.getOctave() <= 1){
						return false;
					}
				}
			}
			return true;
		}
	}

	/**
	 * lower the octave and will do nothing when a special case is hit
	 * @return
	 */
	public boolean octaveDown(){
		if (checkOctave(false)){ //check whether it hits the special case 
			for (Note i : notesArray){
				if (!i.isRest()){
					i.setOctave(i.getOctave()-1);
				}
			}
			return true;
		}
		else {return false;}
	}

	/**
	 * raise the octave and will do nothing when a special case is hit
	 * @return
	 */
	public boolean octaveUp(){
		if (checkOctave(true)){//check whether it hits the special case
			for (Note i : notesArray){
				if (!i.isRest()){
					i.setOctave(i.getOctave()+1);
				}
			}
			return true;
		}
		else {return false;}
	}

	/**
	 * change the tempo of the song
	 * @param ratio
	 */
	public void changeTempo(double ratio){
		for (Note i : notesArray){
			i.setDuration(i.getDuration() * ratio);
		}
		totalDuration = totalDuration*ratio;
	}


	/**
	 * reverse the order of the song without creating new data structure
	 */
	public void reverse(){
		int len = notesArray.length;
		for (int i=0; i<len/2; i++){
			Note temp;
			temp = notesArray[i];
			notesArray[i] = notesArray[len-i-1];
			notesArray[len-i-1] = temp;
		}
	}

	/**
	 * toString method of the song, including all the information
	 */
	public String toString(){
		String s = "";
		int count = 0;
		s += this.title + ","
				+ " by " 
				+ this.artist 
				+ ". Total duration is " 
				+ this.totalDuration + "\n"
				+ "Total # of Notes: "
				+ this.length + "\n";
		for (Note i : notesArray){
			s += count + ", "
					+ String.valueOf(i.getDuration()) + " " 
					+ String.valueOf(i.getPitch()) + " "
					+ String.valueOf(i.getOctave()) + " "
					+ String.valueOf(i.getAccidental()) + " "
					+ String.valueOf(i.isRepeat()) + "\n";
			count ++;
		}
		return s;
	}
}
