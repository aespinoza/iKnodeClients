package net.iknode.lib;

public final class Task {
	// The source application.
	private Application _application;
	
	// The source method for the task.
	private Method _method;
	
	/**
	 * Default constructor.
	 */
	public Task() {
	
	}
		
	/**
	 * Gets the application.
	 * 
	 * @return the _application
	 */
	public Application getApplication() {
		return _application;
	}

	/**
	 * Sets the application.
	 * 
	 * @param _application the _application to set
	 */
	public void setApplication(Application _application) {
		this._application = _application;
	}

	/**
	 * Gets the method.
	 * 
	 * @return the _method
	 */
	public Method getMethod() {
		return _method;
	}

	/**
	 * Sets the method.
	 * 
	 * @param _method the _method to set
	 */
	public void setMethod(Method _method) {
		this._method = _method;
	}
	
	/**
	 * Creates an empty Execution context.
	 * 
	 * @return TaskExecutionContext
	 */
	public TaskExecutionContext createExecutionContext() {
		return new TaskExecutionContext();
	}
	
	/**
	 * Creates a new Task execution context using the current
	 * instance Task and the provided user.
	 * 
	 * @param u User to use in the context.
	 * 
	 * @return TaskExecutionContext
	 */
	public TaskExecutionContext createExecutionContextFor(User u) {
		return new TaskExecutionContext(this, u);
	}
	
	/**
	 * Gets the Task signature, iKnode supports the signature Application:Method
	 * 
	 * @return Application signature string.
	 */
	public String getTaskSignature() {
		return this._application.getName() + ":" + this._method.getName();
	}
}
