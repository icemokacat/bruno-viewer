package moka.brunoviewer.controller;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moka.brunoviewer.global.reader.MarkdownFileReader;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MarkdownController {

	private final MarkdownFileReader markdownFileReader;

	@GetMapping("/page/{fileName}")
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

}
