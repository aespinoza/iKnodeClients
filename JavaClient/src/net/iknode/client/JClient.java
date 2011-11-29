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
	
	private static String USER_ID = "73c1ccf9-13e1-4428-b5fc-4a8388d3552a";
	private static String API_KEY = "e75aed47-f8f9-4df6-b409-707d7b0f7820";
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
