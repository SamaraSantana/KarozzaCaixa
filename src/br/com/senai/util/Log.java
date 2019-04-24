package br.com.senai.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Log {
	private static int lastId;
	
	public Log() {
		try {
			Scanner leitor = new Scanner(new FileReader("log.txt"));
			lastId = leitor.nextInt();
			leitor.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
   }
	public static void Write(String log) {
		try {
			FileWriter escritor = new FileWriter("log.txt", true);
			escritor.write(log + "\n");
			escritor.close();
			FileWriter escritor2 = new FileWriter("id_log.txt");
			escritor2.write(lastId + "\n");
			escritor2.close();
		} catch (IOException e) {
			System.out.println("Erro durante inclusão do log:\n"
					+ e.getMessage());
		}
	}
}
