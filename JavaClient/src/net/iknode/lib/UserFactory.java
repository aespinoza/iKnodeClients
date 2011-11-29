package net.iknode.lib;

/**
 * UserFactory Class.
 * 
 * Represents a User Object Factory.
 * 
 * @author jgemedina
 *
 */
public final class UserFactory {
	
	/**
	 * Creates an empty User.
	 * 
	 * @return User
	 */
	public static User Create() {
		return new User();
	}
	
	/**
	 * Creates a new User using the userId and API key provided.
	 * 
	 * @param userId User id.
	 * @param apikey User API key
	 * @return User
	 */
	public static User Create(String userId, String apikey) {
		User u = Create();
		u.setId(userId);
		u.setApiKey(apikey);
		
		return u;
		
	}
}
