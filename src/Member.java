public class Member extends Thread {

	// calls in itms from other classes inorder to run
	// makes a array for objects of the bell class
	// help on how to do this from
	// https://stackoverflow.com/questions/5199462/class-what-does-it-mean
	private final Bell[] song;
	// note from the note class
	private final Note Note;
	// calls the conductor class
	private final Conductor conduct;

	// create a new member for the choir
	public Member(Note n, Bell[] music, Conductor c) {
		song = music;
		Note = n;
		conduct = c;
		// prints the thread and note the thread is given
		System.out.println(this.getName() + " assigned note: " + n.toString());
	}

	// run method
	public void run() {

		while (conduct.currentnote < song.length) {
			// Get current note
			Bell bn = song[conduct.currentnote];

			// cheecks to see if its the current threads note
			if (bn.note == Note) {
				// if yes then play it
				conduct.playNote(bn);
				// goes to next note
				conduct.currentnote++;
				// Notify other threads
				synchronized (conduct.line) {
					conduct.line.notifyAll();
				}
			} else {
				// if its not that threads note go wait
				synchronized (conduct.line) {
					try {
						conduct.line.wait();
					} catch (InterruptedException ex) {
					}
				}
			}
		}
	}
}