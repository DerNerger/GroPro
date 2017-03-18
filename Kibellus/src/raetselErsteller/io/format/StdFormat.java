package raetselErsteller.io.format;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import raetselErsteller.daten.AusgabeDaten;
import raetselErsteller.daten.EingabeDaten;
import raetselErsteller.io.format.FormatException.GrundType;

/**
 * Diese Klasse kann zum formatieren von Ausgabedaten und zum parsen
 * von Eingabedaten verwendet werden. Das verwendete Format genuegt den
 * in der Aufgabenanalyse definierten Kriterien.
 * 
 * @author Felix Kibellus
 * */
public class StdFormat implements Formatierer {

	//zum Loggen benoetigt
	public static Logger logger = Logger.getLogger(StdFormat.class
			.getName());

	/**
	 * Formatiert die Ausgabedaten. Dazu wird das in der
	 * toString-Methode definierte format gewaehlt.
	 * @param data Die Ausgabedaten, welche formatiert werden sollen.
	 * */
	@Override
	public String formatiere(AusgabeDaten data) {
		logger.log(Level.FINER, "starte Methode");

		return data.toString();
	}

	/**
	 * Diese Methode erzeugt aus den aus der Eingabedatei gelesenen
	 * Zeilen ein Objekt der Klasse EingabeDaten. Dabei werden die 
	 * Kommentare gespeichert, sodass diese spaeter ausgegeben werden
	 * koennen. Ausserdem werden Fehler in den Eingabedaten erkannt.
	 * Beispielsweise koennen weniger als 2 Worte angegeben worden sein,
	 * oder ein Wort nicht ausschliesslich aus Buchstaben bestehen.
	 * */
	@Override
	public EingabeDaten parse(List<String> inputString)
			throws FormatException {
		// logger.log(Level.FINER, "starte Methode");

		// lese kommentare
		List<String> kommentare = new LinkedList<>();
		int i;
		for (i = 0; i < inputString.size(); i++) {
			String line = inputString.get(i);
			if (line.startsWith(";"))
				kommentare.add(line);
			else
				break; // nun kommen die worte
		}
		List<String> worte = new LinkedList<>();
		for (int j = i; j < inputString.size(); j++) {
			String line = inputString.get(j);
			line = line.trim();
			if(line.equals(""))
				throw new FormatException(j+1, GrundType.Leerzeile);
			if (!istKorrekt(line))
				throw new FormatException(j+1, GrundType.KeinBuchstabe);
			worte.add(line.toUpperCase());
		}
		if (worte.size() <= 1) {
			throw new FormatException(i+1, GrundType.NichtGenugWorte);
		}
		return new EingabeDaten(kommentare, worte);
	}

	/**
	 * Diese Funktion prueft, ob es sich bei der uebergebenen Zeile
	 * ausschliesslich um Buchstaben handelt.
	 * */
	private boolean istKorrekt(String line) {
		for (int i = 0; i < line.length(); i++) {
			if (!Character.isLetter(line.charAt(i)))
				return false;
		}
		return true;
	}

}
