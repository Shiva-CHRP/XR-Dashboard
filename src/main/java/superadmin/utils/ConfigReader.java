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
		String sys = System.getProperty(key);
		if (sys != null && !sys.trim().isEmpty()) {
			return sys.trim();
		}
		return prop.getProperty(key);
	}

	public static String getBrowser() {
		return get("browser");
	}

	public static String getEnv() {
		String env = System.getProperty("env");
		if (env != null && !env.trim().isEmpty()) {
			return env.trim().toLowerCase();
		}
		String propEnv = prop.getProperty("env");
		if (propEnv != null && !propEnv.trim().isEmpty()) {
			return propEnv.trim().toLowerCase();
		}
		return "stage";
	}

	public static String getUrl() {
		String sysUrl = System.getProperty("url");
		if (sysUrl != null && !sysUrl.trim().isEmpty()) {
			return sysUrl.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String prodUrl = prop.getProperty("prod.url");
			if (prodUrl != null && !prodUrl.isEmpty()) return prodUrl;
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String stageUrl = prop.getProperty("stage.url");
			if (stageUrl != null && !stageUrl.isEmpty()) return stageUrl;
		}
		return prop.getProperty("url");
	}

	public static String getUsername() {
		String sysUser = System.getProperty("username");
		if (sysUser != null && !sysUser.trim().isEmpty()) {
			return sysUser.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String envUser = System.getenv("PROD_USERNAME");
			if (envUser != null && !envUser.trim().isEmpty()) return envUser.trim();
			String prodUser = prop.getProperty("prod.username");
			if (prodUser != null && !prodUser.trim().isEmpty()) return prodUser.trim();
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String envUser = System.getenv("STAGE_USERNAME");
			if (envUser != null && !envUser.trim().isEmpty()) return envUser.trim();
			String stageUser = prop.getProperty("stage.username");
			if (stageUser != null && !stageUser.trim().isEmpty()) return stageUser.trim();
		}
		return prop.getProperty("username");
	}

	public static String getPassword() {
		String sysPass = System.getProperty("password");
		if (sysPass != null && !sysPass.trim().isEmpty()) {
			return sysPass.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String envPass = System.getenv("PROD_PASSWORD");
			if (envPass != null && !envPass.trim().isEmpty()) return envPass.trim();
			String prodPass = prop.getProperty("prod.password");
			if (prodPass != null && !prodPass.trim().isEmpty()) return prodPass.trim();
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String envPass = System.getenv("STAGE_PASSWORD");
			if (envPass != null && !envPass.trim().isEmpty()) return envPass.trim();
			String stagePass = prop.getProperty("stage.password");
			if (stagePass != null && !stagePass.trim().isEmpty()) return stagePass.trim();
		}
		return prop.getProperty("password");
	}

	public static String getClientUrl() {
		String sysUrl = System.getProperty("clientUrl");
		if (sysUrl != null && !sysUrl.trim().isEmpty()) {
			return sysUrl.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String prodUrl = prop.getProperty("prod.clientUrl");
			if (prodUrl != null && !prodUrl.isEmpty()) return prodUrl;
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String stageUrl = prop.getProperty("stage.clientUrl");
			if (stageUrl != null && !stageUrl.isEmpty()) return stageUrl;
		}
		return get("clientUrl");
	}

	public static String getClientOrgCode() {
		String sys = System.getProperty("clientOrgCode");
		if (sys != null && !sys.trim().isEmpty()) {
			return sys.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String envCode = System.getenv("PROD_CLIENT_ORG_CODE");
			if (envCode != null && !envCode.trim().isEmpty()) return envCode.trim();
			String prodCode = prop.getProperty("prod.clientOrgCode");
			if (prodCode != null && !prodCode.trim().isEmpty()) return prodCode.trim();
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String envCode = System.getenv("STAGE_CLIENT_ORG_CODE");
			if (envCode != null && !envCode.trim().isEmpty()) return envCode.trim();
			String stageCode = prop.getProperty("stage.clientOrgCode");
			if (stageCode != null && !stageCode.trim().isEmpty()) return stageCode.trim();
		}
		String envGeneral = System.getenv("CLIENT_ORG_CODE");
		if (envGeneral != null && !envGeneral.trim().isEmpty()) return envGeneral.trim();
		return get("clientOrgCode");
	}

	public static String getClientUsername() {
		String sys = System.getProperty("clientUsername");
		if (sys != null && !sys.trim().isEmpty()) {
			return sys.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String envUser = System.getenv("PROD_CLIENT_USERNAME");
			if (envUser != null && !envUser.trim().isEmpty()) return envUser.trim();
			String prodUser = prop.getProperty("prod.clientUsername");
			if (prodUser != null && !prodUser.trim().isEmpty()) return prodUser.trim();
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String envUser = System.getenv("STAGE_CLIENT_USERNAME");
			if (envUser != null && !envUser.trim().isEmpty()) return envUser.trim();
			String stageUser = prop.getProperty("stage.clientUsername");
			if (stageUser != null && !stageUser.trim().isEmpty()) return stageUser.trim();
		}
		String envGeneral = System.getenv("CLIENT_USERNAME");
		if (envGeneral != null && !envGeneral.trim().isEmpty()) return envGeneral.trim();
		return get("clientUsername");
	}

	public static String getClientPassword() {
		String sys = System.getProperty("clientPassword");
		if (sys != null && !sys.trim().isEmpty()) {
			return sys.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String envPass = System.getenv("PROD_CLIENT_PASSWORD");
			if (envPass != null && !envPass.trim().isEmpty()) return envPass.trim();
			String prodPass = prop.getProperty("prod.clientPassword");
			if (prodPass != null && !prodPass.trim().isEmpty()) return prodPass.trim();
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String envPass = System.getenv("STAGE_CLIENT_PASSWORD");
			if (envPass != null && !envPass.trim().isEmpty()) return envPass.trim();
			String stagePass = prop.getProperty("stage.clientPassword");
			if (stagePass != null && !stagePass.trim().isEmpty()) return stagePass.trim();
		}
		String envGeneral = System.getenv("CLIENT_PASSWORD");
		if (envGeneral != null && !envGeneral.trim().isEmpty()) return envGeneral.trim();
		return get("clientPassword");
	}

	public static String getClientManagerUsername() {
		String sys = System.getProperty("clientManagerUsername");
		if (sys != null && !sys.trim().isEmpty()) {
			return sys.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String envUser = System.getenv("PROD_CLIENT_MANAGER_USERNAME");
			if (envUser != null && !envUser.trim().isEmpty()) return envUser.trim();
			String prodUser = prop.getProperty("prod.clientManagerUsername");
			if (prodUser != null && !prodUser.trim().isEmpty()) return prodUser.trim();
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String envUser = System.getenv("STAGE_CLIENT_MANAGER_USERNAME");
			if (envUser != null && !envUser.trim().isEmpty()) return envUser.trim();
			String stageUser = prop.getProperty("stage.clientManagerUsername");
			if (stageUser != null && !stageUser.trim().isEmpty()) return stageUser.trim();
		}
		String envGeneral = System.getenv("CLIENT_MANAGER_USERNAME");
		if (envGeneral != null && !envGeneral.trim().isEmpty()) return envGeneral.trim();
		return get("clientManagerUsername");
	}

	public static String getClientManagerPassword() {
		String sys = System.getProperty("clientManagerPassword");
		if (sys != null && !sys.trim().isEmpty()) {
			return sys.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String envPass = System.getenv("PROD_CLIENT_MANAGER_PASSWORD");
			if (envPass != null && !envPass.trim().isEmpty()) return envPass.trim();
			String prodPass = prop.getProperty("prod.clientManagerPassword");
			if (prodPass != null && !prodPass.trim().isEmpty()) return prodPass.trim();
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String envPass = System.getenv("STAGE_CLIENT_MANAGER_PASSWORD");
			if (envPass != null && !envPass.trim().isEmpty()) return envPass.trim();
			String stagePass = prop.getProperty("stage.clientManagerPassword");
			if (stagePass != null && !stagePass.trim().isEmpty()) return stagePass.trim();
		}
		String envGeneral = System.getenv("CLIENT_MANAGER_PASSWORD");
		if (envGeneral != null && !envGeneral.trim().isEmpty()) return envGeneral.trim();
		return get("clientManagerPassword");
	}

	public static String getClientTrainerUsername() {
		String sys = System.getProperty("clientTrainerUsername");
		if (sys != null && !sys.trim().isEmpty()) {
			return sys.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String envUser = System.getenv("PROD_CLIENT_TRAINER_USERNAME");
			if (envUser != null && !envUser.trim().isEmpty()) return envUser.trim();
			String prodUser = prop.getProperty("prod.clientTrainerUsername");
			if (prodUser != null && !prodUser.trim().isEmpty()) return prodUser.trim();
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String envUser = System.getenv("STAGE_CLIENT_TRAINER_USERNAME");
			if (envUser != null && !envUser.trim().isEmpty()) return envUser.trim();
			String stageUser = prop.getProperty("stage.clientTrainerUsername");
			if (stageUser != null && !stageUser.trim().isEmpty()) return stageUser.trim();
		}
		String envGeneral = System.getenv("CLIENT_TRAINER_USERNAME");
		if (envGeneral != null && !envGeneral.trim().isEmpty()) return envGeneral.trim();
		return get("clientTrainerUsername");
	}

	public static String getClientTrainerPassword() {
		String sys = System.getProperty("clientTrainerPassword");
		if (sys != null && !sys.trim().isEmpty()) {
			return sys.trim();
		}
		String env = getEnv();
		if ("prod".equalsIgnoreCase(env) || "production".equalsIgnoreCase(env)) {
			String envPass = System.getenv("PROD_CLIENT_TRAINER_PASSWORD");
			if (envPass != null && !envPass.trim().isEmpty()) return envPass.trim();
			String prodPass = prop.getProperty("prod.clientTrainerPassword");
			if (prodPass != null && !prodPass.trim().isEmpty()) return prodPass.trim();
		} else if ("stage".equalsIgnoreCase(env) || "staging".equalsIgnoreCase(env)) {
			String envPass = System.getenv("STAGE_CLIENT_TRAINER_PASSWORD");
			if (envPass != null && !envPass.trim().isEmpty()) return envPass.trim();
			String stagePass = prop.getProperty("stage.clientTrainerPassword");
			if (stagePass != null && !stagePass.trim().isEmpty()) return stagePass.trim();
		}
		String envGeneral = System.getenv("CLIENT_TRAINER_PASSWORD");
		if (envGeneral != null && !envGeneral.trim().isEmpty()) return envGeneral.trim();
		return get("clientTrainerPassword");
	}
}
