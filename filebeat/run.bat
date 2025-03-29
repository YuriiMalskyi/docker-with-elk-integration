@echo off

docker run -d ^
  --name filebeat-standalone ^
  --network host ^
  -v "D:\docker-logs\book-service:/var/log/book-service:ro" ^
  -v "D:\docker-logs\author-service:/var/log/author-service:ro" ^
  -e ELASTICSEARCH_HOSTS=http://192.168.1.147:9200 ^
  local-filebeat-sidecar
