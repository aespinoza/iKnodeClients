package net.structum.iknode.client.android;

/**
 * <p>
 * TaskFactory Class.
 * </p>
 * 
 * <p>
 * Represents a {@link Task} Object Factory.
 * </p>
 * 
 * @author jgemedina
 * @since 0.1
 * @version 0.1
 */
final class TaskFactory {
	/**
	 * Creates an empty, uninitialized Task.
	 * 
	 * @return {@link Task} instance.
	 * @since 0.1
	 */
	public static Task Create() {
		return new Task();
	}

	/**
	 * Creates a new Task using both objects, an {@link Application}tion and a
	 * {@link Method}.
	 * 
	 * @param a
	 *            The {@link Application}pplication to use.
	 * @param m
	 *            The {@link Application}'s {@link Method} to use.
	 * @return {@link Task} instance.
	 * @since 0.1
	 */
	public static Task Create(final Application a, final Method m) {
		Task t = Create();
		t.setApplication(a);
		t.setMethod(m);

		return t;
	}

	/**
	 * Creates a new Task using two strings, the Application name and Method
	 * name.
	 * 
	 * @param a
	 *            {@link Application} name.
	 * @param m
	 *            {@link Method} name.
	 * @return {@link Task} instance.
	 * @since 0.1
	 */
	public static Task Create(final String a, final String m) {
		Application application = new Application();
		application.setName(a);

		Method method = new Method();
		method.setName(m);

		return Create(application, method);
	}
}
