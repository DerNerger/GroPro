package raetselErsteller.io;

/**
 * Diese Klasse kann zur Fehlerausgabe benutzt werden. Sie schreibt
 * auftretende Fehler auf StandardErr, also auf die Fehleraushabe.
 * 
 * @author Felix Kibellus
 * */
public class ConsoleErrorHandler implements ErrorHandler {

	/**
	 * Schreibt einen Fehler auf die Fehleraushabe.
	 * 
	 * @param msg
	 *            Die Fehlernachricht, welche ausgegben werden soll.
	 * */
	@Override
	public void zeigeFehler(String msg) {
		System.err.println(msg);
	}

}
