package net.structum.iknode.client.mobile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

/**
 * <p>
 * TaskExecutionContext Class.
 * </p>
 * 
 * <p>
 * Represents a Task execution context.
 * </p>
 * 
 * @author jgemedina
 * @since 0.1
 * @version 0.1
 */
final class TaskExecutionContext {
	/**
	 * Execution context {@link Task}.
	 * 
	 * @since 0.1
	 */
	private Task _task;

	/**
	 * Execution context {@link User}.
	 * 
	 * @since 0.1
	 */
	private User _user;

	/**
	 * Default constructor.
	 * 
	 * @since 0.1
	 */
	public TaskExecutionContext() {

	}

	/**
	 * <p>
	 * Parameterized constructor.
	 * </p>
	 * 
	 * <p>
	 * Creates a new Task execution context using the Task and User provided.
	 * </p>
	 * 
	 * @param t
	 *            {@link Task} to use.
	 * @param u
	 *            {@link User} with access keys to use.
	 * @since 0.1
	 */
	public TaskExecutionContext(Task t, User u) {
		this._task = t;
		this._user = u;
	}

	/**
	 * Gets the {@link Task}.
	 * 
	 * @return The {@link Task}.
	 * @since 0.1
	 */
	public Task getTask() {
		return _task;
	}

	/**
	 * Sets the {@link Task}.
	 * 
	 * @param _task
	 *            The {@link Task} to set.
	 * @since 0.1
	 */
	public void setTask(Task _task) {
		this._task = _task;
	}

	/**
	 * Gets the {@link User}.
	 * 
	 * @return The {@link User}.
	 * @since 0.1
	 */
	public User getUser() {
		return _user;
	}

	/**
	 * Sets the {@link User}.
	 * 
	 * @param _user
	 *            The {@link User} to set.
	 * @since 0.1
	 */
	public void setUser(User _user) {
		this._user = _user;
	}

	/**
	 * <p>
	 * Performs the execution of the {@link Task} with no arguments
	 * </p>
	 * 
	 * <p>
	 * Uses {@link #execute(String)} to perform the actual request.
	 * </p>
	 * 
	 * @see #execute(String)
	 * 
	 * @return Execution output.
	 * @since 0.1
	 */
	public String execute() {
		return this.execute("");
	}

	/**
	 * <p>
	 * Performs the execution of the {@link Task}.
	 * </p>
	 * 
	 * <p>
	 * Buids a connection and a request body to issue the iKnode Request.
	 * </p>
	 * 
	 * @see #getConnection()
	 * @see #getRequestBody()
	 * 
	 * @param arguments
	 *            The iKnode Application arguments.
	 * @return Execution output.
	 * @since 0.1
	 */
	public String execute(String arguments) {
		String result;

		try {
			URLConnection connection = this.getConnection();
			OutputStream os = connection.getOutputStream();

			byte[] requestBodyBytes = this.getRequestBody(arguments).getBytes();
			os.write(requestBodyBytes, 0, requestBodyBytes.length);
			os.flush();

			InputStream response = connection.getInputStream();
			BufferedReader buffr = new BufferedReader(new InputStreamReader(
					response));

			String line;
			StringBuffer executionOutput = new StringBuffer();
			while ((line = buffr.readLine()) != null) {
				executionOutput.append(line);
			}
			result = executionOutput.toString();
		} catch (Exception e) {
			result = "";
		}

		return this.sanitizeResponse(result);
	}

	/**
	 * Gets a new connection to the iKnode API service URL.
	 * 
	 * @see #getServiceUrl()
	 * 
	 * @return {@link HttpURLConnection} instance holding the connection.
	 * @since 0.1
	 */
	private final HttpURLConnection getConnection() {
		HttpURLConnection connection;

		try {
			final URL url = new URL(this.getServiceUrl());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.addRequestProperty("iKnode-UserId", this._user.getId());
			connection.addRequestProperty("iKnode-ApiKey",
					this._user.getApiKey());

		} catch (Exception e) {
			connection = null;
		}

		return connection;
	}

	/**
	 * Builds and returns the request body.
	 * 
	 * @param appArguments
	 *            The iKnode Application arguments.
	 * @return The Request body.
	 * @since 0.1
	 */
	private final String getRequestBody(String appArguments) {
		return String.format("{\"parameters\":\"%s\"}", appArguments);
	}

	/**
	 * Builds the iKnode service URL.
	 * 
	 * @return Service URL
	 * @since 0.1
	 */
	private final String getServiceUrl() {
		return String.format(
				"https://api.iknode.net/Applications/execute/%s/%s", this._task
						.getApplication().getName(), this._task.getMethod()
						.getName());
	}

	/**
	 * <p>
	 * Cleans up the response text.
	 * </p>
	 * 
	 * <p>
	 * Removes some response tags from the actual response text and also leading
	 * and trailing whitespaces.
	 * </p>
	 * 
	 * @param response
	 *            The response returned by the server.
	 * @return Response text.
	 * @since 0.1
	 */
	private final String sanitizeResponse(String response) {
		String sanitized;
		
		if(response == "") {
			sanitized = response; 
		} else {
			sanitized = response.replaceAll("\\\\", "");
			sanitized = sanitized.substring(1, sanitized.length() - 1);
		}

		return sanitized;

		/*
		return response
				.replace(
						"<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">",
						"").replace("</string>", "").replaceAll("\\\\", "").trim().substring(1, response.length() - 1);*/
	}
}
