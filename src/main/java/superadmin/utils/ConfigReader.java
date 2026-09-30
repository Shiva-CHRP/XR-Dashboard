package superadmin.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties prop = new Properties();

	static {

		String path = System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties";

		try (FileInputStream fis = new FileInputStream(path)) {

			prop.load(fis);

		} catch (IOException e) {

			throw new RuntimeException("Failed to load config file: " + path, e);
		}
	}

	static {

		try {
			prop.load(new FileInputStream("src/main/resources/testdata/config.properties"));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static String get(String key) {

		return prop.getProperty(key);
	}

	public static String getBrowser() {

		return prop.getProperty("browser");
	}

	public static String getUrl() {

		return prop.getProperty("url");
	}

	public static String getClientUrl() {
		return prop.getProperty("clientUrl");
	}

	public static String getUsername() {
		return prop.getProperty("username");
	}

	public static String getPassword() {
		return prop.getProperty("password");
	}

	public static String getClientOrgCode() {
		return prop.getProperty("clientOrgCode");
	}

	public static String getClientUsername() {
		return prop.getProperty("clientUsername");
	}

	public static String getClientPassword() {
		return prop.getProperty("clientPassword");
	}

	public static String getClientManagerUsername() {
		return prop.getProperty("clientManagerUsername");
	}

	public static String getClientManagerPassword() {
		return prop.getProperty("clientManagerPassword");
	}

	public static String getClientTrainerUsername() {
		return prop.getProperty("clientTrainerUsername");
	}

	public static String getClientTrainerPassword() {
		return prop.getProperty("clientTrainerPassword");
	}
}
