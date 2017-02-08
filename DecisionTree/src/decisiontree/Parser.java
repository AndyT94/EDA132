package decisiontree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
	public Parser(String file, String goal) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;

			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty() && !line.startsWith("%") && !line.startsWith("@data")) {
					String[] split = line.split("\\s+");
					if (split[0].equals("@relation")) {

					} else if (split[0].equals("@attribute")) {

					} else {
						String[] data = split[0].split(",");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
