package raetselErsteller.io;

import java.io.IOException;

/**
 * Diese Klasse wird verwendet um Aussnahmen beim Lesen und Schreiben
 * von Dateien zu behandeln. Sie ist in der Lage, die beiden Faelle
 * Eingabedatei existiert nicht und Ausgabepfad ist ungueltig zu
 * repraesentieren. Ueber die Methode getType() kann abgefragt werden,
 * welcher von beiden Faellen eingetreten ist.
 * 
 * @author Felix Kibellus
 * */
public class FileIOException extends IOException {

	private static final long serialVersionUID = 1L;

	// Speichert welcher Fehlertyp genau eingetreten ist
	private ErrorType type;

	/**
	 * Erzeugt ein Objekt vom Typ FileIOException
	 * 
	 * @param type
	 *            Hier muss angegeben werden, ob die Eingabedatei nicht
	 *            existiert oder ob der Pfad zur Ausgabedatei ungueltig
	 *            ist
	 * */
	public FileIOException(ErrorType type) {
		this.type = type;
	}

	/**
	 * Liefert zurueck, welcher Fehlerfall bei der Ein- oder Ausgabe
	 * aufgetreten ist.
	 * */
	public ErrorType getType() {
		return type;
	}

	//Enum, welches die verschiedenen Ein- und Ausgabefaelle definiert
	public enum ErrorType {
		InputFileNotFound, OutputPathInvalid
	}
}
