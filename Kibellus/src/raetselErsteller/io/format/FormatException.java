package raetselErsteller.io.format;

/**
 * Diese Klasse wird zur Repraesentation von Aussnahmen verwendet, 
 * welche beim Parsen der Eingabedaten entstehen koennen.
 * Ueber getLine() kann abgefragt werden, in welcher Zeile der Fehler
 * aufgetreten ist. Ueber getType() kann abgefragt werden, welcher
 * Fehler genau aufgetreten ist.
 * @author Felix Kibellus
 * */
public class FormatException extends Exception{

	private static final long serialVersionUID = 1L;
	private int line;//Zeile, in der der Fehler aufgetreten ist
	private GrundType type; //genauer Grund des Fehlers
	
	/**
	 * Erzeugt ein Objekt der Klasse FormatException
	 * @param line Zeile in der der Fehler aufgetreten ist
	 * @param type genauer Grund fuer den Fehlers.
	 * */
	public FormatException(int line, GrundType type){
		this.line = line;
		this.type = type;
	}
	
	/**
	 * Gibt die Zeile an, in der der Fehler aufgetreten ist.
	 * */
	public int getLine(){
		return line;
	}
	
	/**
	 * Gibt den genauen Fehlergrund an.
	 * */
	public GrundType getType() {
		return type;
	}

	//Dient zur Unterscheidung, welcher Fehler genau aufgetreten ist
	public enum GrundType{
		//eingelesenes Wort bestand nicht nur aus Buchstaben
		KeinBuchstabe, 
		//es wurden weniger als 2 Worte eingelesen
		NichtGenugWorte,
		//in der Eingabedatei befand sich eine Leerzeile
		Leerzeile
	}
}
