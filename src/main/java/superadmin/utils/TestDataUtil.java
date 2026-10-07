package superadmin.utils;

import java.util.Random;

public class TestDataUtil {

	private static final Random random = new Random();

	public static String getRandomOrgName() {
		return "AutomationOrg_" + System.currentTimeMillis();
	}

	public static String getRandomOrgCode() {
		return "AO" + (1000 + random.nextInt(9000));
	}

	public static String getRandomEmail() {
		return "testuser_" + System.currentTimeMillis() + "@yopmail.com";
	}

	public static String getRandomPhone() {
		long num = 9000000000L + (long)(random.nextDouble() * 999999999L);
		return String.valueOf(num);
	}

	public static String getRandomDomain() {
		return "portal" + System.currentTimeMillis() + ".xrdashboard.com";
	}

	public static String getRandomEmployeeId() {
		return "EMP" + (10000 + random.nextInt(90000));
	}

	public static String getRandomTicketSubject() {
		return "Sanity Ticket " + System.currentTimeMillis();
	}

	public static String getRandomEventName() {
		return "Sanity Event " + System.currentTimeMillis();
	}

	public static String getRandomMasterName(String prefix) {
		return prefix + "_" + System.currentTimeMillis();
	}
}
