/**
 * Defines the iknode namespace
 */
var iknode = iknode || {};

/**
 * Creates an instance of an iKnode Client.
 *
 * @author jmedina
 * @version 0.1
 *
 * @constructor
 * @param {object} config Contains the client configuration.
 */
iknode.Client = function(config) {
	if(!(config.hasOwnProperty('userId')
		 && config.hasOwnProperty('apiKey'))) {
		throw "It is required to provide the User Id and Api Key";
	}

	this.userId = config.userId;
	this.apiKey = config.apiKey;

};

/**
 * Defines a constant for blank parameters.
 */
iknode.Client.prototype.EMPTY_PARAMS = "{\"parameters\":\"\"}";

/**
 * Executes a task in the Execute and Forget way, since we do not care about any response from the server.
 *
 * @param {object} execConfig Execution configuration object.
 */
iknode.Client.prototype.execAndForget = function(execConfig) {
	if(!(config.hasOwnProperty('task')
		 && config.hasOwnProperty('params'))) {
		throw "It is required to provide the Task to execute and its parameters";
	}

	_executeRequest(_buildRequest(_getTaskInfo(execConfig.task)), execConfig.params);

	/**
	 * Gets the task information from a Task expression.
	 *
	 * @private
	 * @param {string} task Task expression.
	 * @return {object} Object containing the task information.
	 */
	function _getTaskInfo(task) {
		var taskParts = task.split(":");

		if(taskParts.length !== 2) {
			throw "Malformed task definition found, task should come as [Application]:[Method]";
		}

		return {
			application: taskParts[0],
			method: taskParts[1]
		};
	}

	/**
	 * Builds a XMLHttpRequest object and configures it
	 * to make a POST request to an iKnode Application through iKnode's REST API.
	 *
	 * @private
	 * @param {object} taskInfo Object containing the Task information.
	 * @return {XMLHttpRequest} request object.
	 */
	function _buildRequest(taskInfo) {
		var xhr = new XMLHttpRequest();

		xhr.open("POST", "https://api.iknode.com/Applications/execute/" + taskInfo.application +"/" + taskInfo.method, true);
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.setRequestHeader("iKnode-UserId", this.userId);
		xhr.setRequestHeader("iKnode-ApiKey", this.apiKey);

		return xhr;
	}

	/**
	 * Executes a preconfigured POST request.
	 *
	 * @private
	 * @param {string} r Request object.
	 * @param {string} params Request parameters.
	 */
	function _executeRequest(r, params) {
		r.send(params);
	}
};

iknode.Client.prototype.exec = function(execConfig) {
	if(!(config.hasOwnProperty('task')
		 && config.hasOwnProperty('params'))) {
		throw "It is required to provide the Task to execute and its parameters";
	}

	var callback = execConfig.hasOwnProperty('callback') ? execConfig.callback : null;

	_executeRequest(_buildRequest(_getTaskInfo(execConfig.task)), execConfig.params, callback);

	/**
	 * Gets the task information from a Task expression.
	 *
	 * @private
	 * @param {string} task Task expression.
	 * @return {object} Object containing the task information.
	 */
	function _getTaskInfo(task) {
		var taskParts = task.split(":");

		if(taskParts.length !== 2) {
			throw "Malformed task definition found, task should come as [Application]:[Method]";
		}

		return {
			application: taskParts[0],
			method: taskParts[1]
		};
	}

	/**
	 * Builds a XMLHttpRequest object and configures it
	 * to make a POST request to an iKnode Application through iKnode's REST API.
	 *
	 * @private
	 * @param {object} taskInfo Object containing the Task information.
	 * @return {XMLHttpRequest} request object.
	 */
	function _buildRequest(taskInfo) {
		var xhr = new XMLHttpRequest();

		xhr.open("POST", "https://api.iknode.com/Applications/execute/" + taskInfo.application +"/" + taskInfo.method, true);
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.setRequestHeader("iKnode-UserId", this.userId);
		xhr.setRequestHeader("iKnode-ApiKey", this.apiKey);

		return xhr;
	}

	/**
	 * Executes a preconfigured POST request.
	 *
	 * @private
	 * @param {string} r Request object.
	 * @param {string} params Request parameters.
	 * @param {function} callback Callback function to call.
	 */
	function _executeRequest(r, params, callback) {
		if(callback) {
			r.onreadystatechanged = function() {
				if(r.readyState === 4 && r.status === 200) {
					callback();
				}
			};
		}
		r.send(params);
	}
};