package moka.brunoviewer.app.controller;

import java.nio.charset.StandardCharsets;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moka.brunoviewer.global.model.http.RestResponse;
import moka.brunoviewer.global.reader.BrunoDocReader;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MarkdownRestController {

	private final BrunoDocReader brunoDocReader;

	@GetMapping("/api/bruno/**")
	public ResponseEntity<?> getHtmlDataFromBruMarkdown(HttpServletRequest request) {

		/*
		 * If only English characters are used in the URL, there is no need to encode and decode the URL.
		 * */
		String urlEncodedPath = request.getRequestURI().substring("/api/bruno/".length());
		log.debug("URL Encoded Request URI: {}", urlEncodedPath);

		String urlDecodedPath = java.net.URLDecoder.decode(urlEncodedPath, StandardCharsets.UTF_8);
		log.debug("URL Decoded Request URI: {}", urlDecodedPath);

		String brunoValueFormLocal = brunoDocReader.getMarkdownValue(urlDecodedPath);

		Parser markdownParser = Parser.builder().build();
		Node markdownNode = markdownParser.parse(brunoValueFormLocal);
		HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

		final String htmlContents = htmlRenderer.render(markdownNode);

		return new RestResponse(htmlContents);
	}

}
