/**
 * Defines the iKnode namespace.
 */
var iKnode = iKnode || {};

/**
 * Creates an instance of an iKnode Client.
 *
 * @author jmedina
 * @version 0.1
 *
 * @constructor
 * @param {object} config Contains the client configuration.
 */
iKnode.Client = function (config) {
    if (!(config.hasOwnProperty('userId') && config.hasOwnProperty('apiKey'))) {
        throw "It is required to provide the User Id and Api Key";
    }

    this.userId = config.userId;
    this.apiKey = config.apiKey;
};

/**
 * Defines a static field for blank parameters.
 */
iKnode.Client.EMPTY_PARAMS = "{\"parameters\":\"{}\"}";

/**
 * Executes a task in the Execute and Forget way, since we do not care about any response from the server.
 *
 * @param {object} config Execution configuration object.
 */
iKnode.Client.prototype.execAndForget = function (config) {
    if (!(config.hasOwnProperty('task') && config.hasOwnProperty('params'))) {
        throw "It is required to provide the Task to execute and its parameters";
    }

    this._executeRequest(this._buildRequest(this._getTaskInfo(config.task)), config.params);
};

/**
 * Executes a task and passes control to the callback method once the execution successfully ends.
 *
 * @param {object} config Execution configuration object.
 */
iKnode.Client.prototype.exec = function (config) {
    if (!config.hasOwnProperty('task')) {
        throw "It is required to provide the Task to execute and its parameters";
    }

	if(this._isNullOrUndefined(config.params)) {
		config.params = iKnode.Client.EMPTY_PARAMS;
	}

    var callback = config.hasOwnProperty('callback') ? config.callback : null;
    this._executeRequest(this._buildRequest(this._getTaskInfo(config.task)), config.params, callback);
};

/**
 * Defines the REST API request Uri.
 *
 * @private
 */
iKnode.Client.prototype._requestUri = "https://api.iKnode.com/Applications/execute/";

/**
 * Gets the task information from a Task expression.
 *
 * @private
 * @param {string} task Task expression.
 * @return {object} Object containing the task information.
 */
iKnode.Client.prototype._getTaskInfo = function (task) {
    var taskParts = task.split(":");

    if (taskParts.length !== 2) {
        throw "Malformed task definition found, task should come as [Application]:[Method]";
    }

    return {
        application: taskParts[0],
        method: taskParts[1]
    };
};

/**
 * Builds a XMLHttpRequest object and configures it
 * to make a POST request to an iKnode Application through iKnode's REST API.
 *
 * @private
 * @param {object} taskInfo Object containing the Task information.
 * @return {XMLHttpRequest} request object.
 */
iKnode.Client.prototype._buildRequest = function (taskInfo) {
    var xhr = window.XMLHttpRequest
        ? new XMLHttpRequest()
        : new ActiveXObject("Microsoft.XMLHTTP");

    xhr.open("POST", this._requestUri + taskInfo.application +"/" + taskInfo.method, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("iKnode-UserId", this.userId);
    xhr.setRequestHeader("iKnode-ApiKey", this.apiKey);

    return xhr;
};

/**
 * Executes a preconfigured POST request.
 *
 * @private
 * @param {string} r Request object.
 * @param {string} params Request parameters.
 * @param {function} callback Callback function to call.
 */
iKnode.Client.prototype._executeRequest = function (r, params, callback) {
    if (callback) {
        r.onreadystatechange = function () {
            if (r.readyState === 4 && r.status === 200) {
				var cleanReponse = r.responseText.trim().replace(/^"|"$/g, "").replace(/\\\"/g, '"');
				var response = eval("(" + cleanReponse + ")");

				response.toString = function() {
					return cleanReponse;
				};

                callback(response);
            }
        };
    }

    r.send(params);
};

iKnode.Client.prototype._isNullOrUndefined = function(object)
{
    return null === object || typeof(object) === "undefined";
};