package net.structum.iknode.client.mobile;

/**
 * <p>
 * Application Class.
 * </p>
 * 
 * <p>
 * Represents an iKnode Codex/Application.
 * </p>
 * 
 * @author jgemedina
 * @since 0.1
 * @version 0.1
 */
final class Application {
	/**
	 * The Application or Codex name-
	 * 
	 * @since 0.1
	 */
	private String _name;

	/**
	 * Gets the Application name.
	 * 
	 * @return The Application name.
	 * @since 0.1
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Sets the Application name.
	 * 
	 * @param _name
	 *            The Application name.
	 * @since 0.1
	 */
	public void setName(String _name) {
		this._name = _name;
	}
}