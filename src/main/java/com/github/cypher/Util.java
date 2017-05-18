package com.github.cypher;

import java.io.*;

public class Util {

	static String capitalize(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	static String decapitalize(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	// Creates the user data folder path
	static String getUserDataDirectoryPath(String applicationName) {
		if (System.getenv("APPDATA") != null) { // Windows style
			return System.getenv("APPDATA") + File.separator + capitalize(applicationName);
		} else if (System.getenv("XDG_CONFIG_HOME") != null){ // config style
			return System.getProperty("XDG_CONFIG_HOME") + File.separator + "." + decapitalize(applicationName);
		} else { //Unix style
			return System.getProperty("user.home") + File.separator + "." + decapitalize(applicationName);
		}
	}
}
