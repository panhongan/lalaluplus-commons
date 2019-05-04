package com.github.lalalu.utils.fs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * lalalu plus
 */

public class FileUtils {

	public static List<String> readFile(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			List<String> list = new ArrayList<>();
			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void readFile(String filePath, Consumer<String> consumer) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				consumer.accept(line);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String readFileAsString(String filePath) {
		FileInputStream input = null;

		try {
			File file = new File(filePath);
			byte[] bytes = new byte[(int) file.length()];
			input = new FileInputStream(file);
			input.read(bytes);
			return new String(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}

}
