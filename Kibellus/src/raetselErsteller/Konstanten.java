package raetselErsteller;

/**
 * In dieser Klasse sind Konstanten definiert. Diese lassen sich in 4
 * Kategorien unterteilen: -Programmeinstellungen -Hiletext fuer den
 * Argparser -Fehlermeldungen -Ausgabe Die Programmeinstellungen
 * koennen geaendert werden, um das Verhalten des Programms zu
 * beeinflussen. Sollte das Programm in einer anderen Sprache benuetigt
 * werden, genuegt es die Texte in dieser Datei auszutauschen.
 * 
 * @author Felix Kibellus
 * */
public class Konstanten {

	// ##############################################################
	// ################# Programmeinstellungen ######################
	// ##############################################################

	public static final boolean useMap = false;

	/*
	 * Hier koennen die gewuenschten Dateiendungen fuer ein und
	 * Ausgabedatei angegeben werden.
	 */
	public static final String dateiendungIn = ".in";
	public static final String dateiendungOut = ".out";

	// ##############################################################
	// ################# Hilfe fuer Argparser #######################
	// ##############################################################

	// So wird der Platzhalter fuer die Eingabedatei in der
	// Hilfeansicht
	// des Argparsers dargestellt
	public static final String inputData = "EINGABEDATEI";

	// So wird in der Hilfeansicht des Argparsers der
	// Programmaufruf erklaert
	public static final String projectname = "java -jar"
			+ " raetselErsteller.jar";

	// Kurze Beschreibung der Programmfunktionalitaet.
	public static final String beschreibung = "Dieses Programm kann "
			+ "zum Erstellen von Raetseln genutzt werden.";

	// Erklaerung der Kommandozeilen-Option -l bzw --log
	public static final String log = "Über diesen Parameter kann das "
			+ "Logging aktiviert werden. Moegliche Parameter sind ALL, "
			+ "CONFIG, FINE, FINER, FINEST, INFO, OFF, SEVERE und "
			+ "WARNING. Default ist OFF. FINER zeigt alle "
			+ "Methodenaufrufe an, mit FINEST kann die Funktionalität "
			+ "des Algorithmus nachvollzogen werde,";

	// Erklaerung der Kommandozeilenoption zum Angeben
	// einer Ausgabedatei
	public static final String output = "Dieser Parameter legt den "
			+ "Pfad fuer die Ausgabedatei fest. Default ist Pfad und "
			+ "Name der Eingabedatei. Endet die Eingabedatei auf .in"
			+ " wird .in durch .out ersetzt. Andernfalls wird nur .out "
			+ "an den Namen der " + "Eingabedatei angehaengt.";

	// Beschreibung zur Angabe der Eingabedatei
	public static final String input = "Hier muss der Pfad zur "
			+ "Eingabedatei angegeben werden. Hier gibt es keinen "
			+ "Default-Wert. Die Eingabedatei muss angegeben werden.";

	// Erklaerung zum Kommandozeilen-Option -t bzw. --timestamp
	public static final String timeStamp = "Gibt die benoetigte "
			+ "Laufzeit des Algorithmus aus. Die zeit zum Einlesen und "
			+ "Ausgeben ist nicht " + "mit eingeschlossen";

	// Beschreibung der Kommandozeilen-Option -a bzw. --algorithm
	public static final String algo = "Legt fest welcher Algoritmus "
			+ "verwendet werden soll. Zur Auswahl stehen Tiefensuche "
			+ "und Breitensuche. Es wird empfohlen Tiefensuche zu "
			+ "benutzen.";

	// ##############################################################
	// ################### Fehlermeldungen ##########################
	// ##############################################################

	//Diese Fehlermeldung wird ausgegeben, wenn bei der Berechnung 
	//mehr Speicher benoetigt wird als der Java VM zugeteilt ist,
	public static String keinSpeicher = "Es steht nicht genug Speicher"
			+ " zur Verfuegung. Das Programm wird abgebrochen.";

	public static String leerzeile = "In der Eingabedatei befindet" +
			" sich eine Leerzeile.";

	// Diese Fehlermeldung wird ausgegeben, wenn die angegebene
	// Eingabedatei nicht gefunden wurde.
	public static final String inputFileNotFound = "Die Eingabedatei "
			+ "konnte nicht gefunden werden. Das Programm wird "
			+ "abgebrochen.";

	// Diese Fehlermeldung wird ausgegeben, wenn in der Eingabedatei
	// ein ungueltiges Wort gefunden wurde und der Formatierer eine
	// Fehlermeldung in einer bestimmten Zeile produziert. Ueber den
	// Parameter line kann die betroffene Zeile direkt in die
	// Fehlermeldung integriert werden.
	public static String buchstabeInZeile(int line) {
		return "Eingabedatei ist falsch formatiert. Ein eingegebenes "
				+ "Wort besteht nicht ausschliesslich aus Buchstaben. "
				+ "Der Fehler " + "befindet sich in der Naehe von "
				+ "Zeile " + line + ".";
	}

	// Diese Fehlermeldung wird ausgegeben, wenn weniger als zwei worte
	// in der Eingabedatei enthalten sind.
	public static final String nichtGenugWorte = "Die Eingabedatei "
			+ "muss mindestens 2 Woerter enthalten, damit das Problem "
			+ "sinnvoll zu bearbeiten ist.";

	// Sollte ein Pfad fuer die Ausgabedatei angegeben worden sein,
	// dieser aber ungueltig ist, so wird diese Fehlermeldung
	// ausgegeben.
	public static final String outputPathInvalid = "Es liegt ein "
			+ "Problem mit " + "dem Pfad der Ausgabedatei vor.";

	// Wenn beim Lesen oder Schreiben von Dateien ein nicht naeher
	// definierbarer Fehler auftritt, wird dieser Fehler ausgegeben.
	public static final String unknownIoErr = "Unbekannter Fehler beim "
			+ "Lesen oder Schreiben von Dateien.";

	// Wenn beim beim Formatieren der Daten ein nicht naeher
	// definierbarer Fehler auftritt, wird dieser Fehler ausgegeben.
	public static final String unknownFormatErr = "Unbekannter Fehler"
			+ " formatieren der Ein- oder Ausgabe.";

	// Sollte keine Loesung gefunden werden, etwa weil die
	// Buchstabenmengen der Worte disjunkt sind, wird diese
	// Fehlermeldung angezeigt.
	public static final String keineLoesung = "Zu den eingegebenen "
			+ "Worten " + "kann kein Raetsel gebildet werden.";

	// ##############################################################
	// ####################### Ausgabe ##############################
	// ##############################################################

	// Diese Anzeige wird verwendet, wenn der Benutzer ueber -t eine
	// Zeitmessung verlangt hat.
	public static final String laufzeit = "Laufzeit: ";

	// Wird beim Ausgeben der Ausgabedatei verwendet, um das versteckte
	// Raetsel zu beschreiben
	public static final String raetselVersteckt = "Raetsel versteckt";

	// Wird beim Ausgeben der Ausgabedatei verwendet, um das noch nicht
	// versteckte Raetsel zu beschreiben
	public static final String raetselNichtVersteckt = "Raetsel "
			+ "nicht versteckt";
}
