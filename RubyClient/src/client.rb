#!/usr/bin/ruby
require 'net/https'
def main()
  service_url = ""
  api_key = ""
  user_id = ""

  #parse config file
  file = File.new("config", "r")
  line_count = 0
  while (line = file.gets)
    line = line.split("=")
    line_count += 1
    if line.length != 2 
      abort "Error on config file, line " + line_count.to_s
    end
    key = line[0].strip
    value = line[1].strip
    if key == "service_url"
      service_url = value
    elsif key == "api_key"
      api_key = value
    elsif key == "user_id"
      user_id = value
    end
  end
  file.close
  if service_url == "" or api_key == "" or user_id == ""
    abort "Incomplete config file, must contain service_url, api_key and user_id values" 
  end
  
  #validate ARGV
  if ARGV.length == 0
    abort "Usage: ApplicationName:MethodName [--param_name=para_value]..."
  end

  #prepare request
  uri = URI(service_url)
  body = "{\"userId\": \"" + user_id + "\", \"apiKey\": \"" + api_key + 
    "\", \"command\": \""+ ARGV[0] + "\"}"
  request = Net::HTTP::Post.new(uri.path, initheader = {"Content-Type" => 
                                  "application/json"})
  request.body = body
  http = Net::HTTP.new(uri.host, uri.port) 
  http.use_ssl = uri.scheme == 'https' 
  response = http.start do |http|
    http.request(request)
  end
  puts response.body
end

main
