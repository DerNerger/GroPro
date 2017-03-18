package raetselErsteller.daten;

/**
 * Diese Klasse repraesentiert einen Punkt (x,y) im R^2 und wird
 * verwendet um Positionen von Buchstaben in einer Kombination zu
 * beschreiben.
 * 
 * @author Felix Kibellus
 * */
public class Punkt {

	public int x; // x-Koordinate des Punktes
	public int y; // y-Koordinate des Punktes

	/**
	 * Erstellt ein Objekt der Klasse Punkt
	 * 
	 * @param x
	 *            Die x-Koordinate des Punktes
	 * @param y
	 *            Die y-Koordinate des Punktes
	 * */
	public Punkt(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Erstellt eine tiefe Kopie eines Punktes
	 * */
	public Punkt(Punkt p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * Wandelt des Punkt in einen String um. Format: (x,y)
	 * */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public int hashCode() {
		return x+y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Punkt other = (Punkt) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	

}
