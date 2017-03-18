package raetselErsteller.daten;

import raetselErsteller.Konstanten;

/**
 * Diese Klasse dient zur Repraesentation der Ausgabedaten. Ein Objekt
 * dieser Klasse enthaelt eine optimale Kombination der Worte in der
 * Eingabedatei. Diese Kombination wird in dieser Klasse einmal mit und
 * einmal ohne zusaetzliche Auffuellzeichen gespeichert. Darueber
 * hinaus haelt diese Klasse eine Referenz auf die Eingabedaten aus
 * welchen die Loesung erstellt wurde.
 * 
 * @author Felix Kibellus
 * */
public class AusgabeDaten {

	// Eingabedaten, welche zu diesen Ausgabedaten gehoeren
	private EingabeDaten eingabeDaten;
	private char[][] loesungEinfach; // ohne zusaetzliche zeichen
	private char[][] loesungSchwer; // mit zusaetzlichen zeichen

	/**
	 * Erzeugt ein Objekt der Klasse AusgabeDaten.
	 * 
	 * @param eingabeDaten
	 *            die zu den Ausgabedaten gehoerenden Eingabedaten
	 * @param c1
	 *            die Loesung ohne Auffuellzeichen
	 * @param c2
	 *            die Loesung mit Auffuellzeichen
	 * */
	public AusgabeDaten(EingabeDaten eingabeDaten,
			char[][] c1, char[][] c2) {
		this.eingabeDaten = eingabeDaten;
		this.loesungEinfach = c1;
		this.loesungSchwer = c2;
	}

	/**
	 * Wandelt die Ausgabedaten in Textform um. Die Darstellung genuegt
	 * dem in der Aufgabenanalyse definierten Format, und kann vom
	 * Formatierer direkt benutzt werden. Die Ausgabe der Ausgabedaten
	 * schiesst die Ausgabe der Eingabedaten mit ein. Deshalb wird in
	 * dieser Methode an die toString-Methode der Klasse EingabeDaten
	 * deligiert.
	 * */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// Eingabedaten
		sb.append(eingabeDaten.toString() + "\n");
		sb.append("\n");

		// Raetsel nicht verseteckt
		sb.append("** "+Konstanten.raetselNichtVersteckt+"\n");
		sb.append("\n");
		for (int i = 0; i < loesungEinfach.length; i++) {
			for (int j = 0; j < loesungEinfach[0].length; j++) {
				if (loesungEinfach[i][j] == '\0')
					sb.append(" ");
				else
					sb.append(loesungEinfach[i][j]);
			}
			sb.append("\n");
		}

		// Raetsel versteckt
		sb.append("\n");
		sb.append("** "+Konstanten.raetselVersteckt+"\n");
		sb.append("\n");
		for (int i = 0; i < loesungSchwer.length; i++) {
			for (int j = 0; j < loesungSchwer[0].length; j++) {
				sb.append(loesungSchwer[i][j]);
			}
			sb.append("\n");
		}

		// Kompaktheitsmass
		sb.append("\n");
		int mass = loesungSchwer.length * loesungSchwer[0].length;
		sb.append("Kompaktheitsmass: " + mass);

		return sb.toString();
	}
}
