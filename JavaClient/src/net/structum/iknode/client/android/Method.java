package net.structum.iknode.client.android;

/**
 * <p>
 * Method Class.
 * </p>
 * 
 * <p>
 * Represents an {@link Application} Method.
 * </p>
 * 
 * @author jgemedina
 * @since 0.1
 * @version 0.1
 */
final class Method {
	/**
	 * Method name.
	 * 
	 * @since 0.1
	 */
	private String _name;

	/**
	 * Gets the Method name.
	 * 
	 * @return The Method name.
	 * @since 0.1
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Sets the Method name.
	 * 
	 * @param _name
	 *            The Method name.
	 * @since 0.1
	 */
	public void setName(String _name) {
		this._name = _name;
	}
}