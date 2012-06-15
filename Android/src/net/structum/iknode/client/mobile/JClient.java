package net.structum.iknode.client.mobile;

/**
 * iKnode Client Class.
 * 
 * @author jgemedina
 * @since 0.1
 * @version 0.1
 */
public final class JClient {
	/**
	 * 
	 */
	private static final String USER_ID = "";
	
	/**
	 * 
	 */
	private static final String API_KEY = "";
	
	/**
	 * 
	 * @param task
	 * @param appArgs
	 * @return
	 */
	public static String execute(String task, String appArgs) {
		String[] taskParts = task.split(":");
		return execute(taskParts[0], taskParts[1], appArgs);
	}

	/**
	 * 
	 * @param application
	 * @param method
	 * @param appArgs
	 * @return
	 */
	public static String execute(String application, String method, String appArgs) {
		return execute(TaskFactory.Create(application, method), appArgs);
	}
		
	/**
	 * 
	 * @param t
	 * @param appArgs
	 * @return
	 */
	public static String execute(Task t, String appArgs) {
		User u = UserFactory.Create(USER_ID, API_KEY);
		TaskExecutionContext execContext = t.createExecutionContextFor(u);
		
		return execContext.execute(appArgs);
	}
}
