package moka.brunoviewer.app.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
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

		List<Extension> extensions = List.of(TablesExtension.create());

		Parser markdownParser = Parser.builder()
			.extensions(extensions)
			.build();

		Node markdownNode = markdownParser.parse(brunoValueFormLocal);
		HtmlRenderer htmlRenderer = HtmlRenderer.builder()
			.extensions(extensions)
			.build();

		final String htmlContents = htmlRenderer.render(markdownNode);

		return new RestResponse(htmlContents);
	}

}
