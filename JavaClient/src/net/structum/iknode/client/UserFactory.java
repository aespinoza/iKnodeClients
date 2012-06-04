package net.structum.iknode.client;

/**
 * <p>
 * UserFactory Class.
 * </p>
 * 
 * <p>
 * Represents a {@link User} Object Factory.
 * </p>
 * 
 * @author jgemedina
 * @since 0.1
 * @version 0.1
 */
final class UserFactory {

	/**
	 * Creates an empty {@link User}.
	 * 
	 * @return {@link User} instance.
	 * @since 0.1
	 */
	public static User Create() {
		return new User();
	}

	/**
	 * Creates a new {@link User} using the Id and API key provided.
	 * 
	 * @param userId
	 *            {@link User} id.
	 * @param apikey
	 *            {@link User} API key
	 * @return {@link User} instance.
	 * @since 0.1
	 */
	public static User Create(String userId, String apikey) {
		User u = Create();
		u.setId(userId);
		u.setApiKey(apikey);

		return u;
	}
}
