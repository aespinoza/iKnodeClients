package net.structum.iknode.client.mobile;

/**
 * iKnode Client Class.
 * 
 * @author jgemedina
 * @since 0.1
 * @version 0.1
 */
public final class JClient {
	
	private String _userId, _apiKey;
	
	/**
	 * 
	 * @param userId
	 * @param apiKey
	 */
	public JClient(String userId, String apiKey) {
		this._userId = userId;
		this._apiKey = apiKey;
	}
	
	/**
	 * 
	 * @param task
	 * @param appArgs
	 * @return
	 */
	public String execute(String task, String appArgs) {
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
	public String execute(String application, String method, String appArgs) {
		return execute(TaskFactory.Create(application, method), appArgs);
	}
		
	/**
	 * 
	 * @param t
	 * @param appArgs
	 * @return
	 */
	public String execute(Task t, String appArgs) {
		User u = UserFactory.Create(this._userId, this._apiKey);
		TaskExecutionContext execContext = t.createExecutionContextFor(u);
		
		return execContext.execute(appArgs);
	}
}
