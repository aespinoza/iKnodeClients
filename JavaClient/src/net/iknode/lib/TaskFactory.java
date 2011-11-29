package net.iknode.lib;

/**
 * TaskFactory Class.
 * 
 * Represents a Task Object Factory.
 * 
 * @author jgemedina
 *
 */
public final class TaskFactory {
	/**
	 * Creates an empty, uninitialized Task.
	 * @return Task
	 */
	public static Task Create() {
		return new Task();
	}
	
	/**
	 * Creates a new Task using both objects, an Application and a Method.
	 * 
	 * @param a The Application to use.
	 * @param m The Application's Method to use.
	 * @return Task
	 */
	public static Task Create(final Application a, final Method m) {
		Task t = Create();
		t.setApplication(a);
		t.setMethod(m);
		
		return t;
	}
	
	/**
	 * Creates a new Task using two strings, the Application name and Method name.
	 *  
	 * @param a Application name.
	 * @param m Method name
	 * @return Task
	 */
	public static Task Create(final String a, final String m) {
		Application application = new Application();
		application.setName(a);
		
		Method method = new Method();
		method.setName(m);
		
		return Create(application, method);
	}
}
