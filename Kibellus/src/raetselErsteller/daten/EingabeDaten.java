package raetselErsteller.daten;

import java.util.List;

/**
 * Diese Klasse dient der Repraesentation der Eingabedaten. Ein Objekt
 * dieser Klasse enthaelt: 
 * -Liste der Kommentare ausn der Eingabedatei
 * -List der Worte, welche in einer Kombination verwendet werden sollen
 * 
 * @author Felix Kibellus
 * */
public class EingabeDaten {
	// liste der Kommentare aus der Eingabedatei
	private List<String> kommentare;
	// liste der Worte, welche kombiniert werden sollen
	private List<String> woerter;

	/**
	 * Erstellt ein Objekt der Klasse EingabeDaten
	 * 
	 * @param kommentar
	 *            Liste der Kommentare aus der Eingabedatei
	 * @param woerter
	 *            Liste der Worte aus der Eingabedatei
	 * */
	public EingabeDaten(List<String> kommentar, List<String> woerter) {
		this.kommentare = kommentar;
		this.woerter = woerter;
	}

	/**
	 * Liefert die Liste der Kommentare zurueck. Diese Methode wird in
	 * der aktuellen implementierung nicht verwendet, wird aber
	 * benoetigt, wenn ein alternativer Formatierer die Eingabedaten
	 * formatieren soll.
	 * */
	public List<String> getKommentare() {
		return kommentare;
	}

	/**
	 * Liefert die Liste der Worte zurueck. Diese Methode wird in der
	 * aktuellen implementierung nicht verwendet, wird aber benoetigt,
	 * wenn ein alternativer Formatierer die Eingabedaten formatieren
	 * soll.
	 * */
	public List<String> getWorte() {
		return woerter;
	}

	/**
	 * Wandelt die Eingabedaten in einen String um. Die Darstellung
	 * genuegt dem in der Aufgabenanalyse beschriebenen Format.
	 * */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//schreibe Kommentare:
		for (String s : kommentare) {
			sb.append(s + "\n");
		}
		//schreibe eingelesene Worte:
		sb.append("Eingelesene Woerter:\n");
		for (int i = 0; i < woerter.size(); i++) {
			sb.append(woerter.get(i));
			if (i != woerter.size() - 1)
				sb.append(", ");
		}
		return sb.toString();
	}
}
