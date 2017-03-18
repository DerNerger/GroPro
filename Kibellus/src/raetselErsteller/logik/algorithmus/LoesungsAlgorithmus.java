package raetselErsteller.logik.algorithmus;

import raetselErsteller.daten.EingabeDaten;
import raetselErsteller.daten.AusgabeDaten;

/**
 * Dieses Interface dient der abstraktion des Loesealgorithmus. Es
 * definiert die Schnittstelle fuer einen Algorithmus, welcher aus den
 * eingelesenen Eingabedaten die benoetigten Ausgabedaten generiert.
 * 
 * @author Felix Kibellus
 * */
public interface LoesungsAlgorithmus {

	/**
	 * Kombiniert die Worte aus den Eingabedaten zu einem Raetsel mit
	 * minimaler Ausdehnung(Flaecheninhalt).
	 * 
	 * @param inputData
	 *            Ein Objekt der Klasse EingabeDaten, welches die Worte
	 *            enthaelt, welche zu einem Raetsel kombiniert werden
	 *            sollen.
	 * 
	 * @return Das fertige Raetsel mit minimaler Ausdehnung.
	 * */
	AusgabeDaten loese(EingabeDaten in)
			throws KeineLoesungException;
}
