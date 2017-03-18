package raetselErsteller.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import raetselErsteller.daten.EingabeDaten;
import raetselErsteller.daten.AusgabeDaten;
import raetselErsteller.io.FileIOException.ErrorType;
import raetselErsteller.io.format.Formatierer;
import raetselErsteller.io.format.FormatException;

/**
 * Diese Klasse kann zum Lesen und Schreiben von Dateien verwendet
 * werden. Sie liest die Eingabedaten aus einer Eingabedatei und
 * schreibt die Ausgabedaten in eine Ausgabedatei. Das Format von Ein-
 * und Ausgabedatei kann ueber einen Formatierer angepasst werden.
 * 
 * @author Felix Kibellus
 * */
public class FileIO implements EingabeDatenLeser, AusgabeDatenSchreiber {

	public static Logger logger = Logger.getLogger(FileIO.class
			.getName());

	// pfad zur Eingabedatei
	private String inPfad;
	// pfad zur Ausgabedatei
	private String outPfad;
	// Formatierer fuer ein und Ausgabe
	private Formatierer formatierer;

	/**
	 * Erstellt ein Objekt der Klasse FileIO.
	 * 
	 * @param inPfad
	 *            der Pfad zur Eingabedatei
	 * @param outPfad
	 *            der Pfad zur Ausgabedatei
	 * @param formatierer
	 *            ein Formatierer, der regelt wie Ein- und Ausgabe
	 *            formatiert werden soll
	 * */
	public FileIO(String inPfad, String outPfad,
			Formatierer formatierer) {
		logger.log(Level.FINER, "starte Methode");

		this.inPfad = inPfad;
		this.outPfad = outPfad;
		this.formatierer = formatierer;
	}

	/**
	 * Schreibt die uebergebenen Ausgabedaten in eine Datei
	 * 
	 * @param out
	 *            Ausgabedaten, welche in eine Datei geschrieben werden
	 *            sollen
	 * */
	@Override
	public void schreibeAusgabe(AusgabeDaten out)
			throws FileIOException {
		logger.log(Level.FINER, "starte Methode");

		String outputString = formatierer.formatiere(out);
		schreibeDatei(outputString);
	}

	/**
	 * Liest die Eingabedaten aus einer Datei und gibt diese zurueck.
	 * Dazu werden zuerst die Zeilen aus der Datei eingelesen, und dann
	 * vom Formatierer in eine Eingabedatei umgewandelt.
	 * 
	 * @return Aus der Datei gelesene Eingabedaten
	 * */
	@Override
	public EingabeDaten leseEingabe() throws FileIOException,
			FormatException {
		logger.log(Level.FINER, "starte Methode");

		List<String> inputStrings = leseDatei();
		EingabeDaten inputData = formatierer.parse(inputStrings);
		return inputData;
	}

	/**
	 * Schreibt einen String in die im Konstruktor angegebene Datei.
	 * 
	 * @param outString
	 *            Text, der in die Ausgabedatei geschrieben werden soll
	 * */
	private void schreibeDatei(String outString)
			throws FileIOException {
		logger.log(Level.FINER, "starte Methode");

		File file = new File(outPfad);
		try (FileWriter fw = new FileWriter(file)) {
			fw.write(outString);
		} catch (IOException e) {
			throw new FileIOException(ErrorType.OutputPathInvalid);
		}
	}

	/**
	 * Liest die im Konstruktor angegebene Datei zeilenweilse aus.
	 * 
	 * @return Liste aus den eingelesenen Zeilen
	 * */
	private List<String> leseDatei() throws FileIOException {
		logger.log(Level.FINER, "starte Methode");

		File file = new File(inPfad);
		List<String> inputStrings = new LinkedList<>();
		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNext()) {
				String line = sc.nextLine();
				inputStrings.add(line);
			}
		} catch (FileNotFoundException e) {
			throw new FileIOException(ErrorType.InputFileNotFound);
		}
		return inputStrings;
	}

}
