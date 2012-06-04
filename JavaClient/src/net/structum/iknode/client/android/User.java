package net.structum.iknode.client.android;

/**
 * User Class.
 * 
 * Represents a User.
 * 
 * @author jgemedina
 * @since 0.1
 * @version 0.1
 */
final class User {
	/**
	 * The User Id.
	 * 
	 * @since 0.1
	 */
	private String _id;

	/**
	 * The User's API key.
	 * 
	 * @since 0.1
	 */
	private String _apiKey;

	/**
	 * Gets the User Id.
	 * 
	 * @return the User Id.
	 * @since 0.1
	 */
	public String getId() {
		return _id;
	}

	/**
	 * Sets the User Id.
	 * 
	 * @param _userId
	 *            The User Id.
	 * @since 0.1
	 */
	public void setId(String _id) {
		this._id = _id;
	}

	/**
	 * Gets the API key.
	 * 
	 * @return the API Key.
	 * @since 0.1
	 */
	public String getApiKey() {
		return _apiKey;
	}

	/**
	 * Sets the API key.
	 * 
	 * @param _apiKey
	 *            The API key.
	 * @since 0.1
	 */
	public void setApiKey(String _apiKey) {
		this._apiKey = _apiKey;
	}
}
