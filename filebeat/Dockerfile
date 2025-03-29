FROM docker.elastic.co/beats/filebeat:7.17.28

COPY filebeat.yml /usr/share/filebeat/filebeat.yml

USER root
RUN chmod 600 /usr/share/filebeat/filebeat.yml

CMD ["filebeat", "-e"]
