package net.iknode.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

/**
 * TaskExecutionContext Class.
 * 
 * Represents a Task execution context.
 * 
 * @author jgemedina
 *
 */
public final class TaskExecutionContext {
	// The task to execute.
	private Task _task;
	
	// The access key to use. 
	private User _user;
	
	/**
	 * Default constructor.
	 */
	public TaskExecutionContext() {
		
	}
	
	/**
	 * Creates a new Task execution context using the Task and User provided.
	 * 
	 * @param t Task to use.
	 * @param u User with access keys to use.
	 */
	public TaskExecutionContext(Task t, User u) {
		this._task = t;
		this._user = u;
	}

	/**
	 * Gets the Task.
	 * 
	 * @return the _task
	 */
	public Task getTask() {
		return _task;
	}

	/**
	 * Sets the Task.
	 * 
	 * @param _task the _task to set
	 */
	public void setTask(Task _task) {
		this._task = _task;
	}

	/**
	 * Gets the User.
	 * 
	 * @return the _user
	 */
	public User getUser() {
		return _user;
	}

	/**
	 * Sets the User.
	 * 
	 * @param _user the _user to set
	 */
	public void setUser(User _user) {
		this._user = _user;
	}
	
	/**
	 * Performs the execution of the Task.
	 */
	public void execute() {
		URLConnection connection = this.getConnection();
		String requestBody = this.getRequestBody();
		
		try {
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			osw.write(requestBody);
			
			osw.flush();
			osw.close();
			
			BufferedReader buffr = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = buffr.readLine()) != null) {
				System.out.println(line);
			}			
		} catch(Exception e) {
			
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private final HttpURLConnection getConnection() {
		HttpURLConnection connection;
		
		try {
			final URL url = new URL("https://api.iknode.net/Commands/execute");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");			
		} catch(Exception e) {
			connection = null;
		}
		
		return connection;
	}
	
	/**
	 * 
	 * @return
	 */
	private final String getRequestBody() {
		final String taskSignature = this._task.getTaskSignature();
		
		return "{\"userId\":\""+this._user.getId()+"\", \"apiKey\":\""+this._user.getApiKey()+"\", \"command\":\""+taskSignature+"\"}";
	}
}
