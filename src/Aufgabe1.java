import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aufgabe1 {

	public static void main(String[] args) {

		Aufgabe1 task = new Aufgabe1();

		String filepath = task.getTextPath("Please choose your text!");
		String text = task.getTextfromFile(filepath);

		filepath = task.getTextPath("Please choose your pattern!");
		String pattern = task.getTextfromFile(filepath);
		pattern = task.toRegEx(pattern);

		if (text.isEmpty() || text.isBlank()) {
			System.err.println("Invalid Text File");
		}
		if (pattern.isEmpty() || pattern.isBlank()) {
			System.err.println("Invalid Pattern File");
		}

		Matcher m = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(text);
		while(m.find()){
			System.out.println(m.group());
		}

	}

	public Aufgabe1() {
	}

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

	private String toRegEx(String content) {

		return content.replaceAll("_","\\\\p{Alnum}+");
	}

}
