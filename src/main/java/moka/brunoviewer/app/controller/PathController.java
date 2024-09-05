package moka.brunoviewer.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moka.brunoviewer.global.config.spring.BrunoProperty;
import moka.brunoviewer.app.model.Directory;
import moka.brunoviewer.global.model.http.HttpErrorResponse;
import moka.brunoviewer.global.model.http.RestErrorResponse;
import moka.brunoviewer.global.model.http.RestResponse;

/**
 * 경로에 대해 조회하는 Controller
 * */
@Slf4j
@RequiredArgsConstructor
@RestController
public class PathController {

	private final BrunoProperty brunoProperty;

	@GetMapping("/api/bruno-path")
	public ResponseEntity<?> path(
		@RequestParam(value = "dir", required = false) String dir
	) {

		if (dir == null) {
			dir = "";
		}

		String rootPath = brunoProperty.getRootPath();

		String directoryPath = rootPath + dir;

		// 파일 및 디렉토리 리스트 조회
		// 1. 해당 디렉토리가 존재 하는지 확인
		Path path = Path.of(directoryPath);
		if(!Files.exists(path)){
			// 2. 존재하지 않는다면 에러 처리
			final HttpErrorResponse response = HttpErrorResponse.builder()
				.httpStatus(HttpStatus.NOT_FOUND)
				.errorCode("NOT_FOUND")
				.message("Directory not found")
				.build();
			return new RestErrorResponse(response);
		}

		// 3. 디렉토리 리스트 조회
		List<Directory> directories = new ArrayList<>();

		// 최상위가 아닌 경우 (..) 상위 디렉토리를 추가
		if (!dir.isEmpty()) {
			Directory directory = new Directory();
			directory.setDirectoryName("..");
			directory.setDirectoryPath("");
			directory.setIsBru(false);
			directories.add(directory);
		}

		try (Stream<Path> stream = Files.list(path)) {
			directories = stream.filter(
				file -> file != null && ( Files.isDirectory(file) || file.getFileName().toString().endsWith(".bru") )
			).map(file -> {

				// 디렉토리인 경우 디렉토리 정보를 반환
				// .bru 파일인 경우 Bruno 파일로 인식
				if(Files.isDirectory(file)) {
					Directory directory = new Directory();
					directory.setDirectoryName(file.getFileName().toString());
					String fileOrDirPath = file.toString().replace(rootPath, "");
					// windows 에서는 \ 를 / 로 변경
					fileOrDirPath = fileOrDirPath.replace("\\", "/");

					directory.setDirectoryPath(fileOrDirPath);
					directory.setIsBru(false);

					return directory;
				}else if(file.getFileName().toString().endsWith(".bru")) {
					Directory directory = new Directory();
					directory.setDirectoryName(file.getFileName().toString());
					String fileOrDirPath = file.toString().replace(rootPath, "");
					// windows 에서는 \ 를 / 로 변경
					fileOrDirPath = fileOrDirPath.replace("\\", "/");
					directory.setDirectoryPath(fileOrDirPath);
					directory.setIsBru(true);

					return directory;
				}
				return null;
			}).toList();
		} catch (IOException e) {
			log.error("directory list error", e);
			final  HttpErrorResponse response = HttpErrorResponse.builder()
				.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.errorCode("INTERNAL_SERVER_ERROR")
				.message("Directory list error")
				.build();
			return new RestErrorResponse(response);
		}

		// 4. 디렉토리 리스트를 반환
		return new RestResponse(directories);
	}

}
