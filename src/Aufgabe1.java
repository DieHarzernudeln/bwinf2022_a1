import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Klassenserver7b
 */
public class Aufgabe1 {

	/**
	 * Main method
	 *
	 * @param args UNUSED
	 */
	public static void main(String[] args) {

		Aufgabe1 task = new Aufgabe1();
		task.performCheck();

	}

	/**
	 * Dummy Constructor
	 */
	public Aufgabe1() {
	}

	/**
	 * Forces the user to select text- and patternfile and applies the pattern on the text Result in console!
	 */
	public void performCheck() {

		String filepath = getTextPath("Please choose your text!");
		String text = getTextfromFile(filepath);

		filepath = getTextPath("Please choose your pattern!");
		String pattern = getTextfromFile(filepath);
		pattern = toRegEx(pattern);

		if (text.isEmpty() || text.isBlank()) {
			System.err.println("Invalid Text File");
		}
		if (pattern.isEmpty() || pattern.isBlank()) {
			System.err.println("Invalid Pattern File");
		}

		Matcher m = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(text);
		while (m.find()) {
			System.out.println(m.group());
		}
	}

	/**
	 * Opens a {@link JFileChooser} where the user can select a file and returns the path of this file
	 *
	 * @param dialoguetitle The title of the {@link JFileChooser}
	 * @return The {@link Path} of the selected file as a String
	 */
	private String getTextPath(String dialoguetitle) {
		JFileChooser chooser = new JFileChooser();
		chooser.setApproveButtonText("Load");
		chooser.setDialogTitle(dialoguetitle);
		chooser.setMultiSelectionEnabled(false);
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		int rueckgabeWert = chooser.showOpenDialog(null);

		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getPath();
		}
		return "";
	}

	/**
	 * Reads the given file and returns the file's content as a String
	 *
	 * @param path The Path of the File which should get loaded.
	 * @return The content of the file as a String
	 */
	private String getTextfromFile(String path) {

		if (path.isBlank() || path.isEmpty()) {
			return "";
		}

		try {
			return Files.readString(Path.of(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Converts the given patternstring into a regular expression
	 *
	 * @param patternstring The pattern which should be converted into a RegEx
	 * @return The created RegEx as a String
	 */
	private String toRegEx(String patternstring) {

		return patternstring.replaceAll("_", "\\\\p{Alnum}+");
	}

}
