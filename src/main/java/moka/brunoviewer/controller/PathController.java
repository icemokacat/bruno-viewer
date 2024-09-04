package moka.brunoviewer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import moka.brunoviewer.global.config.spring.BrunoProperty;
import moka.brunoviewer.model.Directory;

/**
 * 경로에 대해 조회하는 Controller
 * */
@RequiredArgsConstructor
@RestController
public class PathController {

	private final BrunoProperty brunoProperty;

	@GetMapping("/path")
	public ResponseEntity<List<Directory>> path(
		@RequestParam(value = "dir", required = false) String dir
	) {

		if (dir == null) {
			dir = "";
		}

		String rootPath = brunoProperty.getRootPath();

		String directoryPath = rootPath + dir;

		// 파일 및 디렉토리 리스트 조회

		



		List<Directory> directories = null;

		return ResponseEntity.ok(directories);
	}

}
