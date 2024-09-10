package moka.brunoviewer.app.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Directory {
	Boolean isBru;
	private String directoryPath;
	private String directoryName;
	List<Directory> subDirectoryList;
}
