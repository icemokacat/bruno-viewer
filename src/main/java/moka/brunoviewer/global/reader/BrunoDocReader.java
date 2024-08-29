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
public class BrunoDocReader {

	private static final Logger log = LoggerFactory.getLogger(MarkdownFileReader.class);
	private final BufferedReaderPool bufferedReaderPool;
	private final String SERVER_FOLDER_PATH;

	private static final ConcurrentHashMap<String, String> fileCache = new ConcurrentHashMap<>();

	// Constructor injection for dependency management and flexibility
	public BrunoDocReader(
		@Value("${mdreader.poolsize:10}") int poolSize,
		@Value("${bruno.root-path}") String serverFolderPath) {
		this.SERVER_FOLDER_PATH = serverFolderPath;
		this.bufferedReaderPool = new BufferedReaderPool(poolSize);
	}

	public String getMarkdownValue(String relativePath) {
		return fileCache.computeIfAbsent(relativePath, this::loadFileContent);
	}

	private String loadFileContent(String relativePath) {
		Path filePath = Paths.get(SERVER_FOLDER_PATH + relativePath);

		//StringBuilder contentBuilder = new StringBuilder();

		try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)){

			String line;
			boolean inDocsSection = false;
			StringBuilder docsContent = new StringBuilder();

			while ((line = bufferedReader.readLine()) != null) {
				// docs {} 안쪽 내용을 찾기 시작
				if (line.trim().equals("docs {") || line.trim().equals("docs{")) {
					inDocsSection = true;
					continue;
				}

				// docs {} 끝을 만나면 멈춤
				if (inDocsSection && line.equals("}")) {
					break;
				}

				// docs {} 안쪽 내용을 추가
				if (inDocsSection) {
					docsContent.append(line).append(System.lineSeparator());
				}
			}

			// docs {} 안쪽 내용을 반환
			return docsContent.toString();
		} catch (IOException e) {
			throw new RuntimeException("Failed to read file: " + filePath, e);
		}

	}

	public void shutdown() {
		try {
			bufferedReaderPool.closeAll();
		} catch (IOException e) {
			log.error("Failed to close BufferedReader pool", e);
		}
	}

}
