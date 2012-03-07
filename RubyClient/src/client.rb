#!/usr/bin/ruby
require 'net/http'
def main()
  service_url = ""
  api_key = ""
  user_id = ""
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
end

main
