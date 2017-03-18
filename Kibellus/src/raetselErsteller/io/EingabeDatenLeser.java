package raetselErsteller.io;

import raetselErsteller.daten.EingabeDaten;
import raetselErsteller.io.format.FormatException;

/**
 * Dieses Interface definiert die Schnittstelle zum Lesen der
 * Eingabedaten. Durch die Abstraktion dieser Schnittstelle muss der
 * Controller nicht wesentlich veraendert werden, um die
 * Eingabefunktionalitaet zu aendern.
 * 
 * @author Felix Kibellus
 * */
public interface EingabeDatenLeser {

	/**
	 * Liesst die Eingabedaten ein. Wie die Eingabe genau funktioniert
	 * (Datei, Konsole, Netzwerk,...) muss durch eine konkrete Klasse
	 * entschieden werden, welche dieses Interface implementiert.
	 * 
	 * @return Die Eingabedaten, welche die Worte enthalten, die zu
	 *         einem fertigen Raetsel kombiniert werden sollen
	 * */
	EingabeDaten leseEingabe() throws FileIOException, FormatException;
}
