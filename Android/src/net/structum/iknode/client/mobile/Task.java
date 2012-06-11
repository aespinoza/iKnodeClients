package net.structum.iknode.client.mobile;

/**
 * <p>
 * Task Class.
 * </p>
 * 
 * <p>
 * Represents an iKnode Task. Which is usually compound of an
 * {@link Application} and a {@link Method}.
 * </p>
 * 
 * @author jgemedina
 * @since 0.1
 * @version 0.1
 */
final class Task {
	/**
	 * The associated {@link Application}.
	 * 
	 * @since 0.1
	 */
	private Application _application;

	/**
	 * The associated {@link Application} {@link Method}.
	 * 
	 * @since 0.1
	 */
	private Method _method;

	/**
	 * Default constructor.
	 * 
	 * @since 0.1
	 */
	public Task() {

	}

	/**
	 * Gets the associated {@link Application}.
	 * 
	 * @return The {@link Application}.
	 * @since 0.1
	 */
	public Application getApplication() {
		return _application;
	}

	/**
	 * Sets the associated {@link Application}.
	 * 
	 * @param _application
	 *            The {@link Application}.
	 * @since 0.1
	 */
	public void setApplication(Application _application) {
		this._application = _application;
	}

	/**
	 * Gets the associated Application {@link Method}.
	 * 
	 * @return The {@link Method}.
	 * @since 0.1
	 */
	public Method getMethod() {
		return _method;
	}

	/**
	 * Sets the associated Application {@link Method}.
	 * 
	 * @param _method
	 *            The {@link Method}.
	 * @since 0.1
	 */
	public void setMethod(Method _method) {
		this._method = _method;
	}

	/**
	 * Creates an empty Task Execution context.
	 * 
	 * @return {@link TaskExecutionContext} instance.
	 * @since 0.1
	 */
	public TaskExecutionContext createExecutionContext() {
		return new TaskExecutionContext();
	}

	/**
	 * Creates a new Task execution context using the current instance Task and
	 * the provided user.
	 * 
	 * @param u
	 *            {@link User} to use in the context.
	 * @return {@link TaskExecutionContext} instance.
	 * @since 0.1
	 */
	public TaskExecutionContext createExecutionContextFor(User u) {
		return new TaskExecutionContext(this, u);
	}

	/**
	 * Gets the Task signature, iKnode supports the signature Application:Method
	 * 
	 * @return {@link Application} signature string.
	 * @since 0.1
	 */
	public String getTaskSignature() {
		return this._application.getName() + ":" + this._method.getName();
	}
}
