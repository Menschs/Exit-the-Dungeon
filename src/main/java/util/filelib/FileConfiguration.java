package util.filelib;

import util.filelib.exceptions.FileIncompatibleException;

import java.io.*;
import java.util.*;

public class FileConfiguration {
	
	private final File file;
	private final TreeMap<String, String> loaded = new TreeMap<>();
	private final List<String> comments = new ArrayList<>();
	private final String SPLITTER = ":";
	private final String SIGNATUR = "texture";
	
	/**
	 * 
	 * @param _file the File everything is saved into
	 * @param overwrite indicates if the conents of the file are overwritten | !!!NOT IMPLEMENTED YET!!!
	 * @throws FileIncompatibleException File must not be null or a directory, has to end with .polly
	 */
	public FileConfiguration(String _file, boolean overwrite) throws FileIncompatibleException, IOException {
		if(_file.isEmpty()) {
			throw(new FileIncompatibleException());
		}
		file = new File(edit(_file));
		createDirectories(file.getAbsolutePath());

		if(file.isDirectory()) {
			throw(new FileIncompatibleException());
		}
		if(!file.exists()) file.createNewFile();

		load(overwrite);
	}

	/**
	 *
	 * @param _file the File everything is saved into
	 * @param overwrite indicates if the conents of the file are overwritten | !!!NOT IMPLEMENTED YET!!!
	 * @throws FileIncompatibleException File must not be null or a directory, has to end with .polly
	 */
	public FileConfiguration(File _file, boolean overwrite) throws FileIncompatibleException, IOException {
		file = _file;
		createDirectories(file.getAbsolutePath());

		if(file.isDirectory()) {
			throw(new FileIncompatibleException());
		}
		if(!file.exists()) file.createNewFile();

		load(overwrite);
	}

	/**
	 * creates the Directories required to save the file
	 * @param path path where the file is saved
	 */
	private void createDirectories(String path) {
		path = path.replace("\\", "/");
		String[] nodes = path.split("/");

		String latestPath = "";

		for(int i = 0; i < nodes.length-1; i++) {
			latestPath += nodes[i] + "/";
			File file = new File(latestPath);
			if(!file.isDirectory()) {
				file.mkdir();
			}
		}
	}


	public static boolean isCompatible(String file) {
		File f = new File(edit(file));
		return (f.isDirectory() || file.endsWith(".polly"));
	}


	public static boolean isCompatible(File file) {
		return file.getName().endsWith(".polly");
	}

	/**
	 *
	 * @param input String to be modified
	 * @return modified input String
	 */
	private static String edit(String input) {
		return input;
	}
	
	/**
	 * sets a certain value in the file
	 * @param path path to the value
	 * @param value the value the path refers to
	 * @return
	 */
	public FileConfiguration set(String path, Object value) {
		if(value == null || value.toString().isEmpty()) return remove(path);
		path = path.toLowerCase();
		path = path.replace(" ", "");
		if(!loaded.containsKey(path)) loaded.put(path, value.toString());
			else loaded.replace(path, value.toString());
		return this;
	}
	
	/**
	 * 
	 * @return returns the contents of the file as Map<String, String>
	 */
	public Map<String, String> asMap() {
		return loaded;
	}
	
	/**
	 * removes a certain value from the file
	 * @param path leading to the value to delete
	 * @return
	 */
	public FileConfiguration remove(String path) {
		path = path.toLowerCase();
		path = path.replace(" ", "");
		if(loaded.containsKey(path)) {
			loaded.remove(path);
		}
		return this;
	}
	
	/**
	 * loads the file and its contents into memory
	 * @param overwrite not implemented yet
	 * @throws FileIncompatibleException
	 */
	private void load(boolean overwrite) throws FileIncompatibleException {
		loaded.clear();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			List<String> currentPath = new ArrayList<>();
			int latestWhiteSpaces = 0;
			String s;
			boolean first = true;
			while((s = br.readLine()) != null) {
				if(first) {
					if(s.startsWith("#" + SIGNATUR)) {
						first = false;
						continue;
					} else {
						throw(new FileIncompatibleException());
					}
				}
				if(s.trim().startsWith("#")) {
					comments.add(s);
					continue;
				}
				int beginIndex = s.indexOf(SPLITTER) + SPLITTER.length() + 1;
				if(s.startsWith("'")) {
					latestWhiteSpaces = 0;
					currentPath.clear();

					String value = s.substring(beginIndex);
					String path = s.split("'")[1];
					if(!value.isEmpty()) loaded.put(path, value);
					continue;
				}
				if(!s.startsWith(" ")) {
					latestWhiteSpaces = 0;
					currentPath.clear();
					currentPath.add(s.replace(SPLITTER, ""));
					continue;
				}
				
				int currentWhiteSpaces = s.indexOf(SPLITTER) - s.substring(0, s.indexOf(SPLITTER)).trim().length();
				while((latestWhiteSpaces -= 4) >= currentWhiteSpaces) currentPath.remove(currentPath.size() - 1);
				latestWhiteSpaces = currentWhiteSpaces;
				
				if(!s.trim().startsWith("'")) {
					currentPath.add(s.trim().replace(SPLITTER, ""));
					continue;
				}
				String value = s.substring(beginIndex);
				String path = "";
				for(String part : currentPath) path += part + ".";
				if(value.equals("{}")) value = "";
				if(!value.isEmpty()) loaded.put(path + s.split("'")[1], value);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			}
//		if(!overwrite) memory = loaded;
	}
	
	/**
	 * saves everything into the specified file
	 * @return
	 */
	public FileConfiguration save() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("#" + SIGNATUR + "\n");
			for(String s : comments) {
				bw.write(s + "\n");
			}
			
			List<String> latestPath = new ArrayList<>();
			for(String path : loaded.keySet()) {
				String[] nodes = path.split("\\.");
				String empty = "";
				String finalPath = "";
				boolean fuckIt = false;
				for(int i = 0; i < nodes.length; i++) {
					if((latestPath.size()-1) > i && nodes[i].equals(latestPath.get(i)) && !fuckIt) {
						empty += "    ";
					} else if(i < nodes.length - 1) {
						fuckIt = true;
						finalPath += empty + nodes[i] + SPLITTER + "\n";
						empty += "    ";
					} else {
						fuckIt = true;
						finalPath += empty + "'" + nodes[i] + "'" + SPLITTER + " " + loaded.get(path) + "\n";
					}
				}
				bw.write(finalPath);
				latestPath = Arrays.asList(nodes);
			}
			bw.close();
			System.out.println("File saved to: '" + file.getAbsolutePath() + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * 
	 * @param path path leading to a specific value
	 * @return the value linked to the path
	 */
	public String get(String path) {
		path = path.toLowerCase().replace(" ", "");
		return loaded.get(path);
	}

	/**
	 * 
	 * @param path path leading to a specific value
	 * @return the value linked to the path
	 */
	public int getInt(String path) {
		path = path.toLowerCase().replace(" ", "");
		try {
			return Integer.parseInt(get(path));
		} catch (NumberFormatException | NullPointerException nx) {
			return 0;
		}
	}
	
	/**
	 * 
	 * @param path path leading to a specific value
	 * @return the value linked to the path
	 */
	public long getLong(String path) {
		path = path.toLowerCase().replace(" ", "");
		try {
			return Long.parseLong(get(path));
		} catch (NumberFormatException | NullPointerException nx) {
			return 0;
		}
	}
	
	/**
	 * 
	 * @param path path leading to a specific value
	 * @return the value linked to the path
	 */
	public float getFloat(String path) {
		path = path.toLowerCase().replace(" ", "");
		try {
			return Float.parseFloat(get(path));
		} catch (NumberFormatException | NullPointerException nx) {
			return 0;
		}
	}
	
	/**
	 * 
	 * @param path path leading to a specific value
	 * @return the value linked to the path
	 */
	public double getDouble(String path) {
		path = path.toLowerCase().replace(" ", "");
		try {
			return Double.parseDouble(get(path));
		} catch (NumberFormatException | NullPointerException nx) {
			return 0;
		}
	}

	/**
	 *
	 * @param path path leading to a specific value
	 * @return the value linked to the path
	 */
	public short getShort(String path) {
		path = path.toLowerCase().replace(" ", "");
		try {
			return Short.parseShort(get(path));
		} catch (NumberFormatException | NullPointerException nx) {
			return 0;
		}
	}

	/**
	 *
	 * @param path path leading to a specific value
	 * @return the value linked to the path
	 */
	public byte getByte(String path) {
		path = path.toLowerCase().replace(" ", "");
		try {
			return Byte.parseByte(get(path));
		} catch (NumberFormatException | NullPointerException nx) {
			return 0;
		}
	}

	/**
	 *
	 * @param path path leading to a specific value
	 * @return the value linked to the path
	 */
	public boolean getBoolean(String path) {
		path = path.toLowerCase().replace(" ", "");
		return Boolean.valueOf(get(path));
	}

	public TreeMap<String, String> getLoaded() {
		return loaded;
	}
}
