package superadmin.assertions;

import org.testng.Assert;

import superadmin.utils.ToastResponse;

public class ToastAssertions {
	public static void assertToastSuccess(ToastResponse toast, String expectedMessage) {
		Assert.assertEquals(toast.getType(), "success", "Expected success toast type");
		Assert.assertEquals(toast.getMessage(), expectedMessage, "Success toast message mismatch");
	}

	public static void assertToastSuccess(ToastResponse toast, String expectedMessage, String expectedType) {
		Assert.assertEquals(toast.getType(), expectedType, "Toast type mismatch");
		Assert.assertEquals(toast.getMessage(), expectedMessage, "Toast message mismatch");
	}

	public static void assertToastError(ToastResponse toast, String expectedMessage) {
		Assert.assertEquals(toast.getType(), "error", "Expected error toast type");
		Assert.assertEquals(toast.getMessage(), expectedMessage, "Error toast message mismatch");
	}

	public static void assertToastError(ToastResponse toast, String expectedMessage, String expectedType) {
		Assert.assertEquals(toast.getType(), expectedType, "Toast type mismatch");
		Assert.assertEquals(toast.getMessage(), expectedMessage, "Toast message mismatch");
	}
}
