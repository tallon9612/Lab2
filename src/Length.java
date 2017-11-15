//emun to get at the data easier. handels additional info with the notes
enum NoteLength {
	WHOLE(1.0f), HALF(0.5f), QUARTER(0.25f);

	private final int timeMs;

	private NoteLength(float length) {
		timeMs = (int) (length * Note.MEASURE_LENGTH_SEC * 1000);
	}

	public int timeMs() {
		return timeMs;
	}
}