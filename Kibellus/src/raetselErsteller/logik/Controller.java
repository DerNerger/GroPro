package raetselErsteller.logik;

import java.util.logging.Level;
import java.util.logging.Logger;

import raetselErsteller.Konstanten;
import raetselErsteller.daten.AusgabeDaten;
import raetselErsteller.daten.EingabeDaten;
import raetselErsteller.io.AusgabeDatenSchreiber;
import raetselErsteller.io.ConsoleErrorHandler;
import raetselErsteller.io.EingabeDatenLeser;
import raetselErsteller.io.ErrorHandler;
import raetselErsteller.io.FileIO;
import raetselErsteller.io.FileIOException;
import raetselErsteller.io.format.FormatException;
import raetselErsteller.io.format.Formatierer;
import raetselErsteller.io.format.StdFormat;
import raetselErsteller.logik.algorithmus.BacktrackingAlgorithmus;
import raetselErsteller.logik.algorithmus.KeineLoesungException;
import raetselErsteller.logik.algorithmus.LoesungsAlgorithmus;

/**
 * Diese Klasse stellt den zentralen Punkt der Programmarchitektur dar.
 * Sie verwaltet die einzelnen Aufgaben und leitet Funktionsaufrufe an
 * die entsprechenden Schnittstellen von Daten-, Ein/Ausgabeschicht und
 * Algorithmus weiter.
 * 
 * @author Felix Kibellus
 * */
public class Controller {

	public static Logger logger = Logger.getLogger(Controller.class
			.getName());

	private EingabeDatenLeser eingabeLeser;
	private AusgabeDatenSchreiber ausgabeSchreiber;
	private LoesungsAlgorithmus algorithmus;
	private ErrorHandler errorHandler;

	protected static boolean timestamp;
	protected static boolean tiefensuche = true;

	/**
	 * Initialisiert die Controller-Klasse. In diesem Konstruktor wird
	 * festgelegt, wie die Ein- und Ausgabe erfolgen soll. Sollte keine
	 * Interaktion ueber Files mehr erwuenscht sein, so muss hier die
	 * Klasse FileIO durch eine eigene Klasse, welche die noetigen
	 * Interfaces implementiert, ersetzt werden. Ausserdem wird in
	 * diesem Konstruktor der ErrorHandler gesetzt. Sollte keine
	 * Fehlerausgabe auf StandardError mehr erwuenscht sein, so muss
	 * das betreffende Objekt durch einen alternativen ErrorHandler
	 * angepasst werden.
	 * 
	 * @param inPath
	 *            Der Pfad zur Eingabedatei
	 * @param outPath
	 *            Der Pfad zur Ausgabedatei
	 * */
	public Controller(String inPath, String outPath) {
		logger.log(Level.FINER, "starte Methode");

		Formatierer dataFormat = new StdFormat();
		FileIO io = new FileIO(inPath, outPath, dataFormat);
		eingabeLeser = io;
		ausgabeSchreiber = io;
		algorithmus = new BacktrackingAlgorithmus(tiefensuche);
		errorHandler = new ConsoleErrorHandler();
	}

	/**
	 * Wenn dieser Konstruktor aufgerufen wird, wird der Ausgabepfad
	 * ueber die Funktion getDefaultOutputPath bestimmt.
	 * */
	public Controller(String inPath) {
		this(inPath, getDefaultOutputPath(inPath));
	}

	/**
	 * Diese Funktion liefert den Default-Dateinamen der Ausgabedatei.
	 * Endet der Dateiname der Eingabedatei nicht auf .in, so wird nur
	 * .out angehaengt. Endet der Dateiname der Eingabedatei auf .in,
	 * so wird das .in durch .out ersetzt.
	 * 
	 * @param in
	 *            der Pfad zur Eingabedatei
	 * */
	private static String getDefaultOutputPath(String in) {
		logger.log(Level.FINER, "starte Methode");

		String inEnd = Konstanten.dateiendungIn;
		String outEnd = Konstanten.dateiendungOut;
		if (!in.endsWith(inEnd))
			return in + outEnd;
		String result = in.substring(0, in.length() - 3)
				+ outEnd;
		return result;
	}

	/**
	 * In dieser Methode werden zunaechst die Eingabedaten aus der
	 * Eingabedatei gelesen. Danach wird der Loesungsalgorithmus mit
	 * den Eingabedaten ausgefuehrt. Zum Schluss werden die
	 * Ausgabedaten in eine Ausgabedatei geschrieben. Wenn es bei einem
	 * der beschriebenen Schritten zu einem Fehler kommt, wird die
	 * passende Fehlermeldung ueber den errorHandler ausgegeben.
	 * */
	public void loese() {
		logger.log(Level.FINER, "starte Methode");

		try {
			// Lesen der Eingabedaten
			EingabeDaten input = eingabeLeser.leseEingabe();
			// Aufruf der Loesungsroutine
			AusgabeDaten output = null;
			if (timestamp) {
				// mit Zeitmessung
				long t1 = System.currentTimeMillis();
				output = algorithmus.loese(input);
				long t2 = System.currentTimeMillis();
				long runtime = t2 - t1;
				int seconds = (int) runtime / 1000;
				int millis = (int) runtime % 1000;
				System.out.println(Konstanten.laufzeit + seconds + "s "
						+ millis + "ms");
			} else {
				// Ohne Zeitmessung
				output = algorithmus.loese(input);
			}
			// Schreiben der Ausgabedaten
			ausgabeSchreiber.schreibeAusgabe(output);
			// Fehlerbehandlung
		} catch (FileIOException e) {
			switch (e.getType()) {
				case InputFileNotFound :
					errorHandler
							.zeigeFehler(Konstanten.inputFileNotFound);
					break;
				case OutputPathInvalid :
					errorHandler
							.zeigeFehler(Konstanten.outputPathInvalid);
					break;
				default :
					errorHandler.zeigeFehler(Konstanten.unknownIoErr);
			}
		} catch (FormatException e) {
			switch (e.getType()) {
				case KeinBuchstabe :
					int line = e.getLine();
					errorHandler.zeigeFehler(Konstanten
							.buchstabeInZeile(line));
					break;
				case NichtGenugWorte :
					errorHandler
							.zeigeFehler(Konstanten.nichtGenugWorte);
					break;
				case Leerzeile:
					errorHandler.zeigeFehler(Konstanten.leerzeile);
					break;
				default :
					errorHandler
							.zeigeFehler(Konstanten.unknownFormatErr);
					break;
			}
		} catch (KeineLoesungException e) {
			errorHandler.zeigeFehler(Konstanten.keineLoesung);
		} catch(OutOfMemoryError e){
			errorHandler.zeigeFehler(Konstanten.keinSpeicher);
		}
	}

}
