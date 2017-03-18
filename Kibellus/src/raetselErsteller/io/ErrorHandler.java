package raetselErsteller.io;

/**
 * Dieses Interface definiert die Schnittstelle zum Ausgeben von
 * Fehlern. Wenn ein Fehler auftritt, leitet der Controller die
 * zugehoerige Fehlernachricht an diese Schnittstelle weiter. Eine
 * Klasse, welche dieses Interface implementiert muss die Nachricht
 * dann geeignet verarbeiten. (StandardErr, Datei, ...)
 * 
 * @author Felix Kibellus
 * */
public interface ErrorHandler {
	
	/**
	 * Diese Methode stellt die Schnittstelle zur Fehlerausgabe dar.
	 * 
	 * @param msg
	 *            Eine Nachricht, welche den aufgetretenden Fehler
	 *            beschreibt.
	 * */
	void zeigeFehler(String fehler);
}
