using System;
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
            string iKnodeSvcUrl = String.Format("{0}/execute", ConfigurationManager.AppSettings["iKnodeServiceUrl"]);

            HttpWebRequest request = (HttpWebRequest) WebRequest.Create(iKnodeSvcUrl);
            request.Method = "POST";
            request.ContentType = "application/json";

            string userId = ConfigurationManager.AppSettings["userId"];
            string apiKey = ConfigurationManager.AppSettings["apiKey"];

            string requestBody = "{\"userId\":\""+userId+"\",\"apiKey\":\""+apiKey+"\",\"command\":\""+command+"\"}";

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
