package tsp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationHelper {
	
	public static void serializeToFile(Object obj, File file) throws IOException {
		try(FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(obj);
		}
	}
	
	public static Object deserializeFromFile(File file) throws ClassNotFoundException, IOException {
		try(FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
			return in.readObject();
		}
	}
}
