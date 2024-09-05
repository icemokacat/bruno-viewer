package moka.brunoviewer.global.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import moka.brunoviewer.global.exception.Custom404Exception;

@Slf4j
@Component
public class BrunoDocReader {

	private final String SERVER_FOLDER_PATH;

	private static final ConcurrentHashMap<String, String> fileCache = new ConcurrentHashMap<>();

	// Constructor injection for dependency management and flexibility
	public BrunoDocReader(
		@Value("${bruno.root-path}") String serverFolderPath
	)
	{
		this.SERVER_FOLDER_PATH = serverFolderPath;
	}

	public String getMarkdownValue(String relativePath) {
		return fileCache.computeIfAbsent(relativePath, this::loadFileContent);
	}

	private String loadFileContent(String relativePath) {
		Path filePath = Paths.get(SERVER_FOLDER_PATH + relativePath);

		// 파일이 존재하는지 체크 후 존재하지 않으면 Custom404Exception 발생
		if (!Files.exists(filePath)) {
			throw new Custom404Exception("docs file not found: " + filePath);
		}

		StringBuilder docsContent = new StringBuilder();

		try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)){

			String line;
			boolean inDocsSection = false;

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

		} catch (IOException e) {
			throw new RuntimeException("Failed to read file: " + filePath, e);
		}

		// docs {} 안쪽 내용을 반환
		return docsContent.toString();
	}

	@Scheduled(fixedRate = 10000)
	public void clearCache() {
		//log.info("Clearing BrunoDocReader cache");
		fileCache.clear();
	}

	public void clearCache(String relativePath) {
		fileCache.remove(relativePath);
	}

}
