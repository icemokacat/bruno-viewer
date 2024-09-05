package moka.brunoviewer.app.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moka.brunoviewer.global.reader.BrunoDocReader;
import moka.brunoviewer.global.reader.MarkdownFileReader;
import org.commonmark.ext.gfm.tables.TablesExtension;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MarkdownController {

	private final MarkdownFileReader markdownFileReader;
	private final BrunoDocReader brunoDocReader;

	@GetMapping("/page/test/{fileName}")
	public String markdownPage(
		@PathVariable("fileName")
		String fileName, Model model) {

		String markdownValueFormLocal = markdownFileReader.getMarkdownValue(fileName);

		Parser markdownParser = Parser.builder().build();
		Node markdownNode = markdownParser.parse(markdownValueFormLocal);
		HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

		model.addAttribute("contents", htmlRenderer.render(markdownNode));

		return "thymeleaf/markdown-viewer";
	}

	/*
	 *
	 *
	 */
	@GetMapping("/page/bruno/**")
	public String brunoPage(HttpServletRequest request, Model model) {

		/*
		* If only English characters are used in the URL, there is no need to encode and decode the URL.
		* */
		String urlEncodedPath = request.getRequestURI().substring("/page/bruno/".length());
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

		model.addAttribute("contents", htmlRenderer.render(markdownNode));

		return "thymeleaf/markdown-viewer";
	}

}
