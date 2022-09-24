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
	 * Dummy constructor
	 */
	public Aufgabe1() {
	}

	/**
	 * Forces the user to select text and pattern file and applies the pattern to the text result in console.
	 */
	public void performCheck() {

		String filePath = getTextPath("Please choose your text!");
		String text = getTextFromFile(filePath);

		filePath = getTextPath("Please choose your pattern!");
		String pattern = getTextFromFile(filePath);
		pattern = toRegEx(pattern);

		if (text.isEmpty() || text.isBlank()) {
			System.err.println("Invalid Text File.");
		}
		if (pattern.isEmpty() || pattern.isBlank()) {
			System.err.println("Invalid Pattern File.");
		}

		Matcher m = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(text);
		while (m.find()) {
			System.out.println(m.group());
		}
	}

	/**
	 * Opens a {@link JFileChooser} where the user can select a file and returns the path of the chosen file.
	 *
	 * @param dialogueTitle The title of the {@link JFileChooser}
	 * @return The {@link Path} of the selected file as a string
	 */
	private String getTextPath(String dialogueTitle) {
		JFileChooser chooser = new JFileChooser();
		chooser.setApproveButtonText("Load");
		chooser.setDialogTitle(dialogueTitle);
		chooser.setMultiSelectionEnabled(false);
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		int returnOption = chooser.showOpenDialog(null);

		if (returnOption == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getPath();
		}
		return "";
	}

	/**
	 * Reads the given file and returns the file's content as a string.
	 *
	 * @param path The path of the file to be loaded
	 * @return The content of the file as a string
	 */
	private String getTextFromFile(String path) {

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
	 * Converts the given pattern string into a regular expression.
	 *
	 * @param patternString The pattern which should be converted into a RegEx
	 * @return The created RegEx as a string
	 */
	private String toRegEx(String patternString) {

		return patternString.replaceAll("_", "\\\\p{Alnum}+");
	}

}
