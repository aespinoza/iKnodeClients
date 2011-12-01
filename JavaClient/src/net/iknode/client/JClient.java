package net.iknode.client;

import net.iknode.lib.*;

/**
 * JClient Class.
 * 
 * iKnode Java Client main program.
 * 
 * @author jgemedina
 * @version 1.0
 * @since 1.0
 */
public final class JClient {
	// this two constants must be set using your iknode account values.
	private static String USER_ID = "";
	private static String API_KEY = "";
	/**
	 * @param args Program arguments
	 */
	public static void main(String[] args) {
		User u = UserFactory.Create(USER_ID, API_KEY);
		Task t = TaskFactory.Create("RandomPhraseTeller", "Tell");
		
		TaskExecutionContext execCtx = t.createExecutionContextFor(u);
		execCtx.execute();
	}
}
