package raetselErsteller.logik.algorithmus;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import raetselErsteller.daten.AktuelleKombination;
import raetselErsteller.daten.AusgabeDaten;
import raetselErsteller.daten.EingabeDaten;
import raetselErsteller.daten.Punkt;
import raetselErsteller.daten.SchlechtesteAktuelleKombination;

/**
 * Diese Klasse stellt einen Loesungsalgorithmus zum Erstellen von
 * wortraetseln dar. Sie ist in der Lage, zu einer gegebenen Menge von
 * Worten ein Raetsel mit minimaler Ausdehnung zu finden. Dazu wird ein
 * Backtracking-Algorithmus verwendet, der saemtliche Kombinations-
 * moeglichkeiten ausprobiert, und somit einen Suchbaum aufbaut. Da in
 * diesem alle moeglichen Loesungen enthalten sind wird die beste
 * Loesung gefunden. Es besteht die Moeglichkeit diesen Suchbaum in
 * Breiten- oder Tiefensuche zu durchlaufen. Empfohlen ist die
 * Verwendung einer Tiefensuche.
 * 
 * @author Felix Kibellus
 * */
public class BacktrackingAlgorithmus implements LoesungsAlgorithmus {

	// Wird zum Loggen der Funktionalitaet benoetigt
	public static Logger logger = Logger
			.getLogger(BacktrackingAlgorithmus.class.getName());

	// gibt an ob der Suchbaum in Tiefen- oder Breitensuche durchlaufen
	// wird
	private boolean tiefensuche;

	/**
	 * Erstellt ein Objekt vom Typ BacktrackingAlgorithmus
	 * 
	 * @param tiefensuche
	 *            gibt an ob der Algorithmus Tiefensuche oder
	 *            Breitensuche verwenden soll
	 * 
	 * */
	public BacktrackingAlgorithmus(boolean tiefensuche) {
		this.tiefensuche = tiefensuche;
	}

	/**
	 * Diese Methode liefert ausgehend von eingelesenen Eingabedaten
	 * ein Raetsel mit minimaler Ausdehnung. Dazu wird ein
	 * Backtracking-Ansatz werdende.
	 * 
	 * @param eingabe
	 *            Ein Objekt der Klasse EingabeDaten, welches die Worte
	 *            enthaelt, welche zu einem Raetsel kombiniert werden
	 *            sollen.
	 * 
	 * @return Das fertige Raetsel mit minimaler Ausdehnung.
	 * */
	@Override
	public AusgabeDaten loese(EingabeDaten eingabe)
			throws KeineLoesungException {
		logger.log(Level.FINER, "starte Methode");
		// aktuell beste Kombination merken
		AktuelleKombination besteLoesung = //schlechtest moegliche
				new SchlechtesteAktuelleKombination();

		// Merke aktuelle Blaetter des Suchbaums
		// Suchbaum wird als Liste der unteren Elemente des Baums
		// gespeichert
		LinkedList<AktuelleKombination> q = new LinkedList<>();
		// Einfuegen des Wurzelknoten (beliebiges Wort)
		// horiz0ntale Ausrichtung genuegt hier, da die durch vertikale
		// Ausrichtung gefundene Loesung in diese ueberfuehrt werden
		// kann
		q.add(new AktuelleKombination(eingabe, true));

		while (!q.isEmpty()) {
			// hole Kombination aus Suchbaum
			AktuelleKombination a = q.poll();
			
			// a besser als aktuell beste?
			if(besteLoesung.compareTo(a) < 0){
				if(a.istFertig()){//a fertige kombination?
					besteLoesung = a;//a ist neue beste loesung
					continue;
				}
			} else {
				continue;//verwerfe a
			}

			// fuer alle verfuegbaren Worte
			for (String s : a.getVerfuegbareWoerter()) {
				// bilde alle Kombinationsmieglichkeiten
				List<AktuelleKombination> kombis = bildeKombinationen(
						s, a);
				// fuer jede Kombinationsmoeglichkeit
				for (AktuelleKombination k : kombis)
					// fuege in Suchbaum ein, wenn diese Kombination
					// nicht schon
					// in einem anderen Teilbaum vorliegt.
					if (!q.contains(k)){
						logger.log(Level.FINEST, "\n"+k.toString());
						// Tiefenschue=> vorne in Liste einfuegen
						if (tiefensuche)
							q.addFirst(k);
						else
							q.offer(k);
					}

			}
		}
		if (besteLoesung instanceof SchlechtesteAktuelleKombination) {
			throw new KeineLoesungException();
		} else {
			char[][] schwer = fuelleAuf(besteLoesung.getKombi());
			return new AusgabeDaten(
					eingabe, besteLoesung.getKombi(), schwer);
		}
	}

	/**
	 * Diese Methode bildet alle Kombinationsmoeglichkeiten aus einem
	 * Wort und einer AktuellenKombination. Dabei werden nur korrekte
	 * Kombinationen erstellt. Das Wort wird also an einer Stelle
	 * eingefuegt, es mindestens ein anderes Wort der Kombination
	 * schneidet. Ausserdem wird das Wort nur dann eingefuegt, wenn an
	 * allen Kreuzungspunkten mit andern Woertern die Buchstaben
	 * uebereinstimmen. Die uebergebene Kombination wird nicht
	 * veraender, sondern es werden Kopien der Kombination erzeugt. Ist
	 * es in keinem Fall moeglich das Wort in die Kombination
	 * einzufuegen, so ist die zurueckgegebene Liste leer.
	 * 
	 * @param wort
	 *            Das Wort, welches in die Kombination eingefuegt
	 *            werden soll.
	 * @param kombi
	 *            Die Kombination, in welche das Wort eingefuegt werden
	 *            soll
	 * @return eine Liste aus allen Kombinationsmoeglichkeiten von Wort
	 *         und Kombination
	 * */
	private List<AktuelleKombination> bildeKombinationen(String wort,
			AktuelleKombination kombi) {
		List<AktuelleKombination> result = new LinkedList<>();
		// iteriere ueber alle Buchstaben des Wortes
		for (int i = 0; i < wort.length(); i++) {
			// Alle Punkte an denen der Buchstabe in der Kombination
			// vorkommt
			Set<Punkt> a = kombi.getAllePunkteZu(wort.charAt(i));
			// fuer jeden gefunden Punkt der Kombination
			for (Punkt p : a) {
				// kann das Wort horizontal eingefuegt werden?
				if (kombi.istFrei(wort, p, i, true)) {
					AktuelleKombination copy = new AktuelleKombination(
							kombi);// Kopiere die Konfiguration
					// fuege Wort horizontal ein
					copy.add(wort, p, i, true);
					// fuege erhaltene Kombination zum Resultat hinzu
					result.add(copy);
				}
				// kann das Wort vertikal eingefuegt werden?
				if (kombi.istFrei(wort, p, i, false)) {
					AktuelleKombination copy = new AktuelleKombination(
							kombi);
					// fuege Wort vertikal ein
					copy.add(wort, p, i, false);
					// fuege erhaltene Kombination zum Resultat hinzu
					result.add(copy);
				}
			}
		}
		return result;
	}

	/**
	 * Fuellt alle freien Felder des Arrays mit zufaelligen
	 * Grossbuchstaben. Ein Feld gilt als frei, wenn es den Wert '\0'
	 * enthaelt. Das uebergebene Array wird nicht veraendert. Fuer die
	 * Rueckgabe wird ein neues Array erzeugt. Diese Methode wird
	 * verwendet, um aus dem mit den eingelesenen Worten erzeugten
	 * Array ein fertiges Raetsel zu machen.
	 * 
	 * @param einfach
	 *            Ein zweidimensionales Zeichenarray, welches mit
	 *            zufaelligen Buchstaben aufgefuellt werden soll.
	 * @return Ein neues Feld, welches mit zufaelligen Grossbuchstaben
	 *         aufgefuellt wurde.
	 * */
	private char[][] fuelleAuf(char[][] einfach) {
		Random r = new Random(); // fuer Zufallszahlen
		char[][] result = new char[einfach.length][einfach[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				char c = einfach[i][j]; // alter Wert
				if (c == '\0') { // muss aufgefuellt werden?
					// erstelle zufaelligen Buchstaben
					c = (char) ('A' + r.nextInt(26));
				}
				result[i][j] = c;
			}
		}
		return result;
	}

}
