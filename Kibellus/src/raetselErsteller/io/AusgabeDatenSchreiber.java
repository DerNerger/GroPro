package raetselErsteller.io;

import raetselErsteller.daten.AusgabeDaten;

/**
 * Dieses Interface definiert die Schnittstelle zum Schreiben der
 * Ausgabedaten. Durch die Abstraktion dieser Schnittstelle muss der
 * Controller nicht wesentlich veraendert werden, um die
 * Ausgabefunktionalitaet zu aendern.
 * 
 * @author Felix Kibellus
 * */
public interface AusgabeDatenSchreiber {

	/**
	 * Gibt die Ausgabedaten aus. Wie die Ausgabe genau funktioniert
	 * (Datei, Konsole, Netzwerk,...) muss durch eine konkrete Klasse
	 * entschieden werden, welche dieses Interface implementiert.
	 * 
	 * @param outputData
	 *            Die Ausgabedaten, welche die fertigen Raetsel
	 *            beinhalten.
	 * */
	void schreibeAusgabe(AusgabeDaten out)
			throws FileIOException;
}
