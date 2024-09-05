package moka.brunoviewer.global.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MarkdownFileReader {

	private final String SERVER_FOLDER_PATH;

	private static final ConcurrentHashMap<String, String> fileCache = new ConcurrentHashMap<>();

	// Constructor injection for dependency management and flexibility
	public MarkdownFileReader(@Value("${bruno.root-path}") String serverFolderPath) {
		this.SERVER_FOLDER_PATH = serverFolderPath;
	}

	public String getMarkdownValue(String fileName) {
		return fileCache.computeIfAbsent(fileName, this::loadFileContent);
	}

	private String loadFileContent(String fileName) {
		Path filePath = Paths.get(SERVER_FOLDER_PATH + fileName);
		StringBuilder contentBuilder = new StringBuilder();

		try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)){

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				contentBuilder.append(line).append("\n");
			}

		} catch (IOException e) {
			throw new RuntimeException("Failed to read file: " + fileName, e);
		}

		return contentBuilder.toString();
	}

}
