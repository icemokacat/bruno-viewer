package moka.brunoviewer.global.config.spring;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * application.yml 에서 사용할 Custom Property
 * */
@Getter
@Setter
@ToString
public class BrunoProperty {

	private String rootPath;
}
