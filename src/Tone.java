
//Credit to Jacqueline Grub for helping me arrange the starwars song
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

//Main class this is where all the magic happens
public class Tone {

	// array list inorder to keep track of the song
	// creddit to skylarismy for this conductor
	// https://github.com/skylarmt/Tones/tree/master/src/name/skylarismy
	public static Conductor conductor;

	// setup for the audio format
	public static final AudioFormat AF = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, false);

	public static void main(String args[]) throws LineUnavailableException {

		// file name made into a string name for ease of use
		// Pokemon

		String filename = loadfile(args[0]);

		// Read in the file to a List array
		// credit goes to Jakob Jenkov at
		// http://tutorials.jenkov.com/java-collections/list.html for the idea
		// of this list
		System.out.println("Standby");
		List<String> filelines = null;
		try {
			// attempts to get all the lines out of the file given
			filelines = Files.readAllLines(Paths.get(filename));
			// if there is no file or some other error catch it
		} catch (IOException ex) {
			System.out.println("I cant find my music! I quit!");
			System.exit(0);
		}
		// create new list to have a string check for errors and divide
		// up the notes and their length
		List<Bell> sheetmusic = new ArrayList<>();
		for (int t = 0; t < filelines.size(); t++) {
			// run lines through a string inheritor to cheek for errors
			String linereader = filelines.get(t);
			// idea for this from
			// https://docs.oracle.com/javase/7/docs/api/java/lang/String.html
			// (about halfway down the page)
			// if current line does not match these letters/numbers quit out of
			// program
			if (linereader.matches("(([A-G][0-9]S?|REST)\\s+([0-9]+))") == false) {
				System.out.println("I cant read this music!");
				// exit program
				System.exit(0);
			}
			// String split help from
			// https://stackoverflow.com/questions/3481828/how-to-split-a-string-in-java
			// make a new string that fetches the note part of the line
			String note = linereader.split(" ")[0];
			// make a new string that fetches the length part of the line
			String leng = linereader.split(" ")[1];

			// try to add the note and length to a final sheet that will be
			// played
			try {
				sheetmusic.add(new Bell(Note.valueOf(note), Tone.stringToLength(leng)));
				// if the note fails to match accepted length then error out
				// Credit to
				// https://beginnersbook.com/2013/04/exception-handling-examples/
			} catch (NumberFormatException ex) {
				System.out.println("This is to long to be a note!");
				System.exit(0);
				// if the note fails to match accepted imput then error out
				// credit to
				// https://stackoverflow.com/questions/15208544/when-should-an-illegalargumentexception-be-thrown
				// on how to use this
			} catch (IllegalArgumentException ex) {
				System.out.println("This isnt a note at all!");
				System.exit(0);
			}

		}
		// Create a new conductor for the track
		// creddit to skylarismy
		try {
			conductor = new Conductor(sheetmusic);
		} catch (Exception ex) {
			System.out.println("I cant work under these conditions, I quit!");
		}
		// }

		// Tell the conductors to start their players
		System.out.println("Show Time");

		conductor.start();
		// System.out.println(conductors.get(i));
	}

	// Thank you to dawn for her help in setting up this method
	private static String loadfile(String filename) {

		final File file = new File(filename);
		if (file.exists()) {

		}
		return filename;

	}

	// get the audio format
	public static AudioFormat getAudioFormat() {
		return AF;
	}

	// grabs the note legnth itself and returns the correct length
	public static NoteLength stringToLength(String s) throws NumberFormatException {
		switch (s) {
		case "1":
			return NoteLength.WHOLE;
		case "2":
			return NoteLength.HALF;
		case "4":
			return NoteLength.QUARTER;

		default:
			throw new NumberFormatException("Invalid note length.");
		}
	}

}