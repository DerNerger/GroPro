package raetselErsteller.logik;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import raetselErsteller.Konstanten;
import raetselErsteller.io.FileIO;
import raetselErsteller.io.format.StdFormat;
import raetselErsteller.logik.algorithmus.BacktrackingAlgorithmus;

/**
 * Diese Klasse beinhaltet den Startpunkt des Programms und die Logik
 * zum Parsen von Argumenten. Ausserdem wird in dieser Klasse das
 * Logging konfiguriert und die benoetigten Logger auf das vom Benutzer
 * gewuenscht Level eingestellt.
 * @author Felix Kibellus
 * */
public class Main {

	/**
	 * Startpunkt des Programms. Zunaechst werden die uebergebenen
	 * Argumente analysiert und auf Korrektheit geprueft. Dann wird das
	 * Programm, wie in den Argumenten beschrieben, konfiguriert.
	 * Anschliessend wird es gestartet.
	 * */
	public static void main(String[] args) {
		Namespace ns = parseArgs(args);// Parsen der Argumente
		// initialisieren des Loggers
		if (ns.getString("log") == null)
			initLogger(Level.OFF);
		else
			initLogger(Level.parse(ns.getString("log")));
		// timestamp
		Controller.timestamp = ns.getBoolean("timestamp");
		// algorithmus
		if (ns.getString("algorithm").equals("Breitensuche"))
			Controller.tiefensuche = false;
		// erstellen der Klasse Controller
		Controller main = null;
		String out = ns.getString("output");
		if (out != null)
			main = new Controller(
					ns.getString(Konstanten.inputData), out);
		else
			main = new Controller(ns.getString(Konstanten.inputData));
		// starten des Loesungsalgorithmus
		main.loese();
	}

	/**
	 * Diese Funktion liest die uebergebenen Argumente ein und reagiert
	 * darauf. Sollte der Benutzer falsche Argumente angeben oder die
	 * Hilfe anfordern, so wird das Programm in dieser Methode beendet.
	 * 
	 * @param args
	 *            Die von Benutzer hinter dem Programmnamen
	 *            eingegebenen Argumente.
	 * @return Eine Map, welche von Argument auf Parameter abbildet.
	 * */
	private static Namespace parseArgs(String[] args) {
		String name = Konstanten.projectname;
		ArgumentParser parser = ArgumentParsers
				.newArgumentParser(name).defaultHelp(true)
				.description(Konstanten.beschreibung);
		parser.addArgument("-l", "--log").help(Konstanten.log);
		parser.addArgument("-o", "--output").help(Konstanten.output);
		parser
				.addArgument("-t", "--timestamp")
				.help(Konstanten.timeStamp)
				.action(Arguments.storeTrue());
		parser.addArgument(Konstanten.inputData).help(Konstanten.input);
		parser
				.addArgument("-a", "--algorithm").help(Konstanten.algo)
				.setDefault("Tiefensuche");
		Namespace ns = null;
		try {
			ns = parser.parseArgs(args);
		} catch (ArgumentParserException e) {
			// Der Parser gibt selbst eine Fehlermeldung aus, und
			// erklaert dem Benutzer wie das Programm zu verwenden ist
			//(korrekte Parameter).
			parser.handleError(e);
			System.exit(1);// beendet das Programm
		}
		return ns;
	}

	/**
	 * Diese Funktion konfiguriert die Logger aller Klassen. Der
	 * Default-Handler zur Ausgaeb wird entfernt und ein eigener
	 * Handler gesetzt. Bei diesem Handler wird ein Logging-Level
	 * gesetzt, sodass er nur Meldungen ausgibt, die mindestens dem
	 * gesetzten Level entsprechen.
	 * 
	 * @param level
	 *            Alle Logging-Meldungen die mindestens dieses Level
	 *            haben werden ausgegeben.
	 * */
	private static void initLogger(Level level) {
		// deaktiviere die default handler
		Controller.logger.setUseParentHandlers(false);
		BacktrackingAlgorithmus.logger.setUseParentHandlers(false);
		StdFormat.logger.setUseParentHandlers(false);
		FileIO.logger.setUseParentHandlers(false);
		// logger level setzen
		Controller.logger.setLevel(Level.ALL);
		BacktrackingAlgorithmus.logger.setLevel(Level.ALL);
		StdFormat.logger.setLevel(Level.ALL);
		FileIO.logger.setLevel(Level.ALL);
		// eigenen Handler erstellen
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(level);
		// eigenen handler setzen
		Controller.logger.addHandler(consoleHandler);
		BacktrackingAlgorithmus.logger.addHandler(consoleHandler);
		StdFormat.logger.addHandler(consoleHandler);
		FileIO.logger.addHandler(consoleHandler);
	}

}
