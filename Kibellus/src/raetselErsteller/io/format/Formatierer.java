package raetselErsteller.io.format;

import java.util.List;

import raetselErsteller.daten.AusgabeDaten;
import raetselErsteller.daten.EingabeDaten;

/**
 * Diese Schnittstelle dient der Abstraktion des Formates von
 * Ein- und Ausgabedatei.
 * @author Felix Kibellus
 * */
public interface Formatierer {
	
	/**
	 * Formatiert die Ausgabedaten zu einem Text, der in die 
	 * Ausgabedatei geschrieben werden kann.
	 * */
	String formatiere(AusgabeDaten out);

	/**
	 * Erzeugt aus den aus der Eingabedatei eingelesenen Zeilen
	 * ein Objekt vom Typ Eingabedaten.
	 * */
	EingabeDaten parse(List<String> in) throws FormatException;
}
