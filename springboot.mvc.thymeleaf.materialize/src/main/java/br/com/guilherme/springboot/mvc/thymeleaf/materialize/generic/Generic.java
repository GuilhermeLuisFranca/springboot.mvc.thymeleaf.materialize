package br.com.guilherme.springboot.mvc.thymeleaf.materialize.generic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Generic {

	private static void print(String file, int time) throws Exception {
		Thread.sleep(time);
		Path arq = Paths.get("src/main/webapp/generic/"+file+".txt");
        Files.lines(arq).forEach(System.out::println);
	}
	
	public static void openSource() throws Exception {
		print("openSource", 0);
	}

	public static void coffee() throws Exception {
		print("coffee", 180000);
	}

	
	
}
