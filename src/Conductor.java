import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Conductor extends Thread {

	// cheecks the current note
	public volatile int currentnote = 0;

	// array for the notes in the txt file
	public List<Bell> sheetmusic = new ArrayList<>();

	// array for the choir members
	private final List<Member> choirmember = new ArrayList<>();

	// arrary for all the notes
	public static final Note[] NOTES = Note.values();

	// audio output
	public SourceDataLine line;

	// makes a new conductor, if something goes wrong throws a error
	// credit to skylarismy for this inital set up
	// https://github.com/skylarmt/Tones/tree/master/src/name/skylarismy
	public Conductor(List<Bell> music) throws LineUnavailableException {
		line = AudioSystem.getSourceDataLine(Tone.getAudioFormat());
		line.open();
		line.start();
		sheetmusic = music;

		// Setup all the everyone and give everyone a note
		// thank you to Patrick C
		Bell[] noteArray = sheetmusic.toArray(new Bell[sheetmusic.size()]);
		for (int i = 0; i < NOTES.length; i++) {
			choirmember.add(new Member(Note.values()[i], noteArray, this));
		}
	}

	// run method
	public void run() {
		// credit to skylarismy for .stream
		choirmember.stream().forEach((player) -> {
			player.start();
		});

	}

	// play a note here
	public void playNote(Bell bn) {
		final int ms = Math.min(bn.length.timeMs(), (int) (Note.MEASURE_LENGTH_SEC * 1000));
		final int length = Note.SAMPLE_RATE * ms / 1000;
		line.write(bn.note.sample(), 0, length);
		line.write(Note.REST.sample(), 0, 50);
	}
}