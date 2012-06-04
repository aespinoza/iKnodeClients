using System;
using System.Linq;
using System.IO;
using System.Net;
using System.Text;
using System.Configuration;
using System.ServiceModel.Web;

namespace iKnodeConsole
{
    /// <summary>
    /// Defines the Program class.
    /// </summary>
    class Program
    {
        /// <summary>
        /// True for Debug mode, false otherwise.
        /// </summary>
        private static bool IsDebug = ConfigurationManager.AppSettings["mode"].ToLower().Equals("debug");

        /// <summary>
        /// Main Entry point for the application.
        /// </summary>
        /// <param name="args">Console Arguments.</param>
        static void Main(string[] args)
        {                        
            try {
                ShowHeader();

                if(args.Length == 0) {
                    ShowUsage();
                    return;
                }

                string command = args[0];

                DateTime executionStart = DateTime.Now;
                Console.WriteLine(Execute(command));
                Console.WriteLine("");
                Console.WriteLine(">> Total:" + DateTime.Now.Subtract(executionStart).Milliseconds + "ms");

            } catch(Exception ex) {
                Console.WriteLine(ex.Message);
                Console.WriteLine(ex.StackTrace);
            }
        }

        /// <summary>
        /// Returns the current version of the application.
        /// </summary>
        /// <returns><c>string</c></returns>
        private static string GetCurrentVersion()
        {
            return System.Reflection.Assembly.GetExecutingAssembly().GetName().Version.ToString();
        }

        /// <summary>
        /// Shows the Console Header.
        /// </summary>
        private static void ShowHeader()
        {
            Console.WriteLine("Structum (R) iKnode Console Version "+GetCurrentVersion());
            Console.WriteLine("Copyright (C) Alejandro Espinoza Esparza 2010-2011. All Rights Reserved.");
            Console.WriteLine("");
        }

        /// <summary>
        /// Shows the Console Usage.
        /// </summary>
        private static void ShowUsage()
        {
            Console.WriteLine("Usage: ApplicationName:MethodName [--param_name=para_value]...");
            Console.WriteLine("");
            Console.WriteLine("Arguments:");
            Console.WriteLine("     * ApplicationName: Application Name Defined in Command Center.");
            Console.WriteLine("     * MethodName: The Method Name to Call.");
            Console.WriteLine("     * param_name: Task Parameter Name.");
            Console.WriteLine("     * param_value: Task Parameter Value.");
            Console.WriteLine("");
            Console.WriteLine("Example:");
            Console.WriteLine("iKnodeConsole \"Psychologist.Answer --question=What R U doin'?\"");
            Console.WriteLine("");
        }

        /// <summary>
        /// Executes the selected iKnode Command.
        /// </summary>
        /// <param name="command">Command to Execute.</param>
        private static string Execute(string command)
        {
            string[] appDef = GetAppDefinition(command);
            string iKnodeSvcUrl = String.Format("{0}/execute/{1}/{2}", ConfigurationManager.AppSettings["iKnodeServiceUrl"],
                                                appDef[0], appDef[1]);

            string userId = ConfigurationManager.AppSettings["userId"];
            string apiKey = ConfigurationManager.AppSettings["apiKey"];

            HttpWebRequest request = (HttpWebRequest) WebRequest.Create(iKnodeSvcUrl);
            request.Method = "POST";
            request.ContentType = "application/json";
            request.Headers.Add("iKnode-UserId", userId);
            request.Headers.Add("iKnode-ApiKey", apiKey);

            string requestBody = "{ " + String.Format("\"parameters\": \"{{ {0} }}\"", appDef[2]) + " }";

            byte[] data = Encoding.UTF8.GetBytes(requestBody);
            using(Stream dataStream = request.GetRequestStream()) {
                dataStream.Write(data, 0, data.Length);
            }

            HttpWebResponse response = (HttpWebResponse) request.GetResponse();

            string result = String.Empty;
            if(IsDebug) {
                result = GetResponseHeader(response);
            }

            result += ConvertResponseToString(response);

            return result;
        }

        /// <summary>
        /// Gets the application definition.
        /// </summary>
        /// <remarks>
        /// Extracts the applicatino and the its task, also the arguments list. This is splited
        /// into the app name, app task and a transformed list of arguments.
        /// </remarks>
        /// <param name="command">Full issued command</param>
        /// <returns>Array containing the command definition</returns>
        private static string[] GetAppDefinition(string command)
        {
            string arguments;
            string[] appDef = command.Split(':');

            string applicationName = appDef[0];
            string applicationTask = appDef[1].TrimEnd().IndexOf(" ") >= 0
                ? appDef[1].Substring(0, appDef[1].IndexOf(" "))
                : appDef[1].TrimStart().TrimEnd();

            if (appDef[1].TrimEnd().IndexOf(" ") >= 0) {
                arguments = appDef[1].Substring(appDef[1].IndexOf(" "), appDef[1].Length - appDef[1].IndexOf(" "));
            } else {
                arguments = String.Empty;
            }

            return new string[] {
                applicationName,
                applicationTask,
                AdjustArgumentsSyntax(arguments.Trim())
            };
        }

        /// <summary>
        /// Adjusts the arguments sintax.
        /// </summary>
        /// <remarks>
        /// Removes the leading '--' and transforms the parameter and its value
        /// to a property-value representation, this is applied to all parameters.
        /// </remarks>
        /// <param name="arguments">Arguments string</param>
        /// <returns>New arguments representation</returns>
        private static string AdjustArgumentsSyntax(string arguments)
        {
            StringBuilder newArgumentsBuffer;

            if (String.IsNullOrEmpty(arguments)) {
                newArgumentsBuffer = new StringBuilder(String.Empty);
            } else {
                newArgumentsBuffer = new StringBuilder();

                string[] argumentsCollection = arguments.Split(' ');
                foreach (string arg in argumentsCollection) {
                    string[] argParts = arg.Split('=');

                    Console.Write(argParts[0]);

                    newArgumentsBuffer.Append(String.Format("{0}:", argParts[0].Replace("--", "")));
                    newArgumentsBuffer.Append(String.Format("'{0}',", argParts[1]));
                }
            }

            string newArguments = newArgumentsBuffer.ToString();
            
            if (newArguments.LastIndexOf(",") >= 0) {
                newArguments.Remove(newArguments.LastIndexOf(","), 1);
            }

            return newArguments;
        }

        /// <summary>
        /// Returns the Response Header.
        /// </summary>
        /// <param name="response">Http Response.</param>
        /// <returns>Response Header.</returns>
        private static string GetResponseHeader(HttpWebResponse response)
        {
            string result = "Status code: " + (int)response.StatusCode + " " + response.StatusCode + "\r\n";

            foreach (string key in response.Headers.Keys) {
                result += string.Format("{0}: {1} \r\n", key, response.Headers[key]);
            }

            result += "\r\n";

            return result;
        }

        /// <summary>
        /// Converts the Response Stream to String.
        /// </summary>
        /// <param name="response">Http Response.</param>
        /// <returns>Response Text.</returns>
        private static string ConvertResponseToString(HttpWebResponse response)
        {
            string result = new StreamReader(response.GetResponseStream()).ReadToEnd();
            return result.Replace("<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">", "").Replace("</string>", "");
        }
    }
}
