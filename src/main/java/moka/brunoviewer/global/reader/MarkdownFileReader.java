package moka.brunoviewer.global.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MarkdownFileReader {

	private static final Logger log = LoggerFactory.getLogger(MarkdownFileReader.class);
	private final BufferedReaderPool bufferedReaderPool;
	private final String SERVER_FOLDER_PATH;

	private static final ConcurrentHashMap<String, String> fileCache = new ConcurrentHashMap<>();

	// Constructor injection for dependency management and flexibility
	public MarkdownFileReader(
		@Value("${mdreader.poolsize:10}") int poolSize,
		@Value("${bruno.root-path}") String serverFolderPath) {
		this.SERVER_FOLDER_PATH = serverFolderPath;
		this.bufferedReaderPool = new BufferedReaderPool(poolSize);
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

	public void shutdown() {
		try {
			bufferedReaderPool.closeAll();
		} catch (IOException e) {
			log.error("Failed to close BufferedReader pool", e);
		}
	}

}
