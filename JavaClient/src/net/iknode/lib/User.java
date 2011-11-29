package net.iknode.lib;

/**
 * User Class.
 * 
 * Represents a User.
 * 
 * @author jgemedina
 *
 */
public final class User {
	// The User Id, which is conformed by a GUID.
	private String _id;
	
	// The user API key, which is conformed as a GUID.
	private String _apiKey;
	
	/**
	 * Gets the User id.
	 * 
	 * @return the _userId
	 */
	public String getId() {
		return _id;
	}

	/**
	 * Sets the User id.
	 * 
	 * @param _userId the _userId to set
	 */
	public void setId(String _id) {
		this._id = _id;
	}

	/**
	 * Gets the API key.
	 * 
	 * @return the _apiKey
	 */
	public String getApiKey() {
		return _apiKey;
	}

	/**
	 * Sets the API key.
	 * 
	 * @param _apiKey the _apiKey to set
	 */
	public void setApiKey(String _apiKey) {
		this._apiKey = _apiKey;
	}
}
