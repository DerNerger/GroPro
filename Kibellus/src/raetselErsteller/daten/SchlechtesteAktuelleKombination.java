package raetselErsteller.daten;

/**
 * DIes ist eine aktuelle Kombination, welche immer schlechter als
 * eine andere aktuelle Kombination ist. Sie wird verwendet um
 * die aktuell beste Kombination im Loesungsalgorithmus zu 
 * initialisieren.
 * @author Felix Kibellus
 * */
public class SchlechtesteAktuelleKombination 
	extends AktuelleKombination{
	
	@Override
	public int compareTo(AktuelleKombination o) {
		return -1;
	}
}
