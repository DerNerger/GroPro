package raetselErsteller.daten;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import raetselErsteller.Konstanten;

/**
 * Diese Klasse definiert eine aktuelle Kombinationsmoeglichkeit von
 * benutzten Worten. Sie enthaelt eine Menge von noch nicht benutzten
 * Worten, und ein char[] der aktuellen Kombination. Ueber die Methoden
 * istFrei und add koennen aus dieser Kombination neue Kombinationen
 * entwickelt werden.
 * */
public class AktuelleKombination 
	implements Comparable<AktuelleKombination>{

	// Referenz auf die eingelesenen Eingabedaten
	private EingabeDaten eingabeDaten;

	/*
	 * Alle Worte in dieser Liste wurden noch nicht benutzt und koennen
	 * noch eingefuegt werden. Wird ein Wort ueber add eingefuegt, so
	 * wird es automatisch aus dieser Liste geloescht.
	 */
	private List<String> verfuegbareWorte;

	// Aktuelle Kombination der bereits eingefuegten Worte
	private char[][] kombi;

	//speichert die Positionen von Buchstaben
	private Map<Character, Set<Punkt>> map;

	/**
	 * Erstellt eine neue Kombination mit einem Startwort. Als Startwort
	 * wird das erste Wort in den Eingabedaten verwendet.
	 * 
	 * @param eingabe
	 *            Die Eingabedatei, welche unter anderen die Worte
	 *            enthaelt, die in einem Objekt dieser Klassen
	 *            kombiniert werden sollen.
	 * 
	 * @param horizontal
	 *            Gibt an ob das Startwort horizontal oder vertikal
	 *            eingefuegt werden soll.
	 * */
	public AktuelleKombination(EingabeDaten eingabe, 
			boolean horizontal) {
		this.eingabeDaten = eingabe;
		verfuegbareWorte = new LinkedList<>(eingabe.getWorte());
		if (Konstanten.useMap)
			map = new HashMap<Character, Set<Punkt>>();
		// benutze erstes verfuegbares Wort
		String startWort = verfuegbareWorte.remove(0);
		// fuege das Wort horizontal oder vertikal ein
		if (horizontal) {
			kombi = new char[1][startWort.length()];
			for (int i = 0; i < kombi[0].length; i++) {
				kombi[0][i] = startWort.charAt(i);
				addToMap(startWort.charAt(i), new Punkt(0, i));
			}
		} else {
			kombi = new char[startWort.length()][1];
			for (int i = 0; i < kombi.length; i++) {
				kombi[i][0] = startWort.charAt(i);
				addToMap(startWort.charAt(i), new Punkt(i, 0));
			}
		}
	}

	/**
	 * Kopierkonstruktor zum Erzeugen einer tiefen Kopie.
	 * */
	public AktuelleKombination(AktuelleKombination kombi2) {
		this.eingabeDaten = kombi2.eingabeDaten;

		this.verfuegbareWorte = new LinkedList<>(
				kombi2.verfuegbareWorte);
		this.kombi = new char[kombi2.kombi.length][kombi2.kombi[0].length];
		for (int i = 0; i < kombi.length; i++) {
			for (int j = 0; j < kombi[0].length; j++) {
				kombi[i][j] = kombi2.kombi[i][j];
			}
		}
		if (Konstanten.useMap) {
			map = new HashMap<>();
			for (char c : kombi2.map.keySet()) {
				Set<Punkt> mapCpy = new HashSet<>();
				for (Punkt p : kombi2.map.get(c))
					mapCpy.add(new Punkt(p));
				map.put(c, mapCpy);
			}
		}
	}
	
	/**
	 * Default Konstruktor.
	 * */
	public AktuelleKombination(){
		eingabeDaten = null;
		verfuegbareWorte = new LinkedList<>();
		kombi = new char[0][0];
		map = new HashMap<>();
	}

	/**
	 * Diese Methode prueft ob ein Wort in die aktuelle Kombination
	 * eingefuegt werden kann, ohne das es eine Kollision mit einem
	 * anderen Buchstaben gibt. Diese Methode prueft nicht, ob die
	 * Kombination danach noch gueltig ist(etwa weil das eingefuegte
	 * Wort keine Ueberschneidungen mit dem Rest der Kombination
	 * aufweist.
	 * 
	 * @param wort
	 *            Das Wort, welches in die Kombination eingefuegt werden
	 *            soll.
	 * @param p
	 *            Die Position(basierend auf dem Koordinatensystem der
	 *            Kombination) an welcher der Buchstabe wort[index]
	 *            eingefuegt werden soll.
	 * @param index
	 *            An dieser Stelle des Wortes soll das Wort an der
	 *            Stelle p in die Kombination eingefuegt werden.
	 * @param horizontal
	 *            Gibt an ob das Wort horizontal oder vertikal
	 *            eingefuegt werden soll.
	 * */
	public boolean istFrei(String wort, Punkt p, int index,
			boolean horizontal) {
		if (horizontal)
			return istFreiHorizontal(wort, p, index);
		else
			return istFreiVertikal(wort, p, index);
	}

	/**
	 * Ueberprueft, ob das Wort horizontal eingefuegt werden kann.
	 * Siehe: istFrei
	 */
	private boolean istFreiHorizontal(String wort, Punkt p, int index) {
		for (int i = 0; i < wort.length(); i++) {
			// vor linkem Rand => springe nach vorne
			if (p.y + i - index < 0)
				i = index - p.y;
			// hinter rechtem Rand => keine Kollision mehr moeglich
			if (p.y + i - index >= kombi[0].length)
				return true;
			char zuVergleichen = kombi[p.x][p.y + i - index];
			if (zuVergleichen != '\0' // Position ist noch frei
					&& zuVergleichen != wort.charAt(i))
				return false; // Kollision mit anderem Wort
		}
		return true; // Wort kann eingefuegt werden
	}

	/**
	 * Ueberprueft, ob das Wort vertikal eingefuegt werden kann. Siehe:
	 * istFrei
	 */
	private boolean istFreiVertikal(String wort, Punkt p, int index) {
		for (int i = 0; i < wort.length(); i++) {
			// vor oberem Rand => springe nach unten
			if (p.x + i - index < 0)
				i = index - p.x;
			// hinter unterem Rand => keine Kollision mehr moeglich
			if (p.x + i - index >= kombi.length)
				return true;
			char zuVergleichen = kombi[p.x + i - index][p.y];
			if (zuVergleichen != '\0'// Position ist noch frei
					&& zuVergleichen != wort.charAt(i))
				return false;// Kollision mit anderem Wort
		}
		return true;// Wort kann eingefuegt werden
	}

	/**
	 * Diese Methode fuegt ein neues Wort zur Kombination hinzu.
	 * Achtung: vorher sollte ueber istFrei geprueft werden, ob das Wort
	 * eingefuegt werden kann.
	 * 
	 * @param wort
	 *            Das Wort, welches in die Kombination eingefuegt werden
	 *            soll.
	 * @param p
	 *            Die Position(basierend auf dem Koordinatensystem der
	 *            Kombination) an welcher der Buchstabe wort[index]
	 *            eingefuegt werden soll.
	 * @param index
	 *            An dieser Stelle des Wortes soll das Wort an der
	 *            Stelle p in die Kombination eingefuegt werden.
	 * @param horizontal
	 *            Gibt an ob das Wort horizontal oder vertikal
	 *            eingefuegt werden soll.
	 * */
	public void
			add(String wort, Punkt p, int index, boolean horizontal) {
		if (horizontal)
			addHorizontal(wort, p, index);
		else
			addVertikal(wort, p, index);
		// Wort ist nicht mehr verfuegbar, da es eingefuegt wurde
		verfuegbareWorte.remove(wort);
	}

	/**
	 * Fuegt das angegebene Wort horizontal in die Kombination ein.
	 * Siehe: add
	 * */
	private void addHorizontal(String wort, Punkt p, int index) {
		// pruefe ob array erweitert werden muss
		int linksFehlt = p.y - index < 0 ? index - p.y : 0;
		int rechtsFehlt = p.y - index + wort.length() > kombi[0].length
				? p.y - index + wort.length() - kombi[0].length
				: 0;
		// erweitere das Array
		Punkt offset = new Punkt(0, linksFehlt);
		erweitereArray(0, linksFehlt + rechtsFehlt, offset);
		// fuege Wort in Array ein
		for (int i = 0; i < wort.length(); i++) {
			kombi[p.x][p.y + linksFehlt + i - index] = wort.charAt(i);
			// add punkt zur map
			addToMap(wort.charAt(i), new Punkt(p.x, p.y + linksFehlt
					+ i - index));
		}
	}

	/**
	 * Fuegt das angegebene Wort vertikal in die Kombination ein. Siehe:
	 * add
	 * */
	private void addVertikal(String wort, Punkt p, int index) {
		// pruefe ob array erweitert werden muss
		int obenFehlt = p.x - index < 0 ? index - p.x : 0;
		int untenFehlt = p.x - index + wort.length() > kombi.length
				? p.x - index + wort.length() - kombi.length
				: 0;
		// erweitere das Array
		Punkt offset = new Punkt(obenFehlt, 0);
		erweitereArray(obenFehlt + untenFehlt, 0, offset);
		for (int i = 0; i < wort.length(); i++) {
			kombi[p.x + i - index + obenFehlt][p.y] = wort.charAt(i);
			// add punkt zur map
			addToMap(wort.charAt(i), new Punkt(p.x + i - index
					+ obenFehlt, p.y));
		}
	}

	/**
	 * Erweitert das Array in x und/oder y Richtung und kopiert die
	 * Daten des alten Arrays in das neue.
	 * 
	 * @param x
	 *            Anzahl Elemente, welche in x-Richtung erweitert werden
	 *            muessen.
	 * @param y
	 *            Anzahl Elemente, welche in x-Richtung erweitert werden
	 *            muessen.
	 * 
	 * @paran p Position im neuen Koordinatensystem, an der das alte
	 *        Array eingefuegt werden soll (obere linke Ecke des alten
	 *        Arrays).
	 * */
	private void erweitereArray(int x, int y, Punkt p) {
		if (x <= 0 && y <= 0)
			return;
		char[][] neu = new char[kombi.length + x][kombi[0].length + y];
		for (int i = 0; i < kombi.length; i++) {
			for (int j = 0; j < kombi[0].length; j++) {
				neu[i + p.x][j + p.y] = kombi[i][j];
			}
		}
		kombi = neu;
		if(!Konstanten.useMap)return;
		//map veraendern
		for(char c: map.keySet()){
			Set<Punkt> newSet = new HashSet<>();
			for(Punkt p2 : map.get(c)){
				newSet.add(new Punkt(p2.x+p.x, p2.y+p.y));
			}
			map.put(c, newSet);
		}
	}

	/**
	 * Gibt zurueck, ob die aktuelle Kombination zu einer vollstaendigen
	 * (wenn auch nicht optimalen) Loesung geworden ist.
	 * */
	public boolean istFertig() {
		return verfuegbareWorte.size() == 0;
	}

	/**
	 * Vergleicht die AktuelleKombination mit einem anderen Objekt
	 * der selben Klasse. Vergleichskriterium ist die Bewertung.
	 * Diese ist definiert als Flaecheninhalt des kombi-Feldes.
	 * 
	 * @return 1 wenn other<this, -1 wenn other>this, 0 sonst.
	 * */
	@Override
	public int compareTo(AktuelleKombination o) {
		if(o instanceof SchlechtesteAktuelleKombination) return 1;
		int bewertungThis = kombi.length*kombi[0].length;
		int bewertungOther = o.kombi.length*o.kombi[0].length;
		if(bewertungThis<bewertungOther) return 1;
		if(bewertungThis>bewertungOther) return -1;
		return 0;
	}

	/**
	 * Gibt alle Punkte zurueck, an denen in dieser Kombination ein
	 * uebergebenes Zeichen steht.
	 * 
	 * @param c
	 *            Das Zeichen nach dem gesucht werden soll.
	 * */
	public Set<Punkt> getAllePunkteZu(char c) {
		if (Konstanten.useMap) {
			if (map.get(c) == null)
				return new HashSet<>();
			return map.get(c);
		} else {
			Set<Punkt> result = new HashSet<>();
			for (int i = 0; i < kombi.length; i++) {
				for (int j = 0; j < kombi[0].length; j++) {
					if(kombi[i][j]==c)
						result.add(new Punkt(i,j));
				}
			}
			return result;
		}
	}

	/**
	 * Liefert alle Worte zurueck, die noch nicht in dieser Kombination
	 * benutzt wurden.
	 * */
	public List<String> getVerfuegbareWoerter() {
		return verfuegbareWorte;
	}

	/**
	 * Liefert die in der aktuellen Kombination verwendeten Buchstaben
	 * als Zeichenarray zurueck.
	 * 
	 * @return char[][], welches die Kombination repraesentiert.
	 * */
	public char[][] getKombi() {
		return kombi;
	}

	/**
	 * Wandelt die aktuelle Kombination in einen String um. Dargestellt
	 * wird zuerst die Anordnung der Buchstaben in Rasterform und dann
	 * die Liste der noch verfuegbaren Worte.
	 * 
	 * @return Repraesentation der Kombination als String.
	 * */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// char[][] als Raster darstekken
		for (int i = 0; i < kombi.length; i++) {
			for (int j = 0; j < kombi[0].length; j++) {
				char c = kombi[i][j];
				sb.append("|");
				if (c == '\0')
					sb.append("_");
				else
					sb.append(c);
			}
			sb.append("|\n");
		}
		// noch nicht verwendete Worte
		sb.append("Worte:\n");
		for (String s : verfuegbareWorte)
			sb.append(s + "\n");
		return sb.toString();
	}

	/**
	 * Fuegt einen neuen Buchstaben zur Map hinzu
	 * */
	private void addToMap(char c, Punkt p) {
		if (!Konstanten.useMap)
			return;
		if (map.get(c) == null)
			map.put(c, new HashSet<Punkt>());
		Set<Punkt> s = map.get(c);
		s.add(p);
	}

	/**
	 * Erstellt einen Hash-Code zu der aktuellen Kombination.
	 **/
	@Override
	public int hashCode() {
		return kombi[0].length * kombi.length
				+ verfuegbareWorte.hashCode();
	}

	/**
	 * Vergleicht, ob ein objekt dieser Klasse und ein beliebiges
	 * anderes Objekt gleich sind. Handelt es sich dabei um ein Objekt
	 * der Klasse AktuelleKombination, so gelten sie als gleich, wenn
	 * die bereits eingefuegte Kombination und die noch verfuegbaren
	 * Worte gleich sind.
	 * */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AktuelleKombination other = (AktuelleKombination) obj;
		// pruefe dimensionen
		if (other.kombi.length != kombi.length)
			return false;
		if (other.kombi[0].length != kombi[0].length)
			return false;
		// pruefe Worte
			if (!verfuegbareWorte.equals(other.verfuegbareWorte))
				return false;
		// pruefe felder
		for (int i = 0; i < kombi.length; i++) {
			for (int j = 0; j < kombi[0].length; j++) {
				if (kombi[i][j] != other.kombi[i][j])
					return false;					
			}
		}
		
		return true;
	}
	
}
