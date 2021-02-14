FROM openjdk:8-jdk-alpine

RUN apk add tzdata bash tmux

#redstonegang-java

# Aqui preciso saber se vai salvar o mundo
# E ter o arquivo de configurações

WORKDIR /server/

ENV TZ="America/Sao_Paulo"

ARG API_TOKEN=""
ARG API_URL=""

# Se vai abri a porta 5005 pra acessar o debuger remoto
ENV JAVA_DEBUG=0
## Configurações gerais de todos os jars
ENV JAVA_JAR_FILE=""
ENV JAVA_JAR_ARGUMENTS=""
ENV JAVA_ARGUMENTS=""

# Minimo de ram alocado pro container que o processo vai usar, não pode colocar 100% pq esse é só o valor do heap
ENV JAVA_MIN_PERCENTAGE="80.0"
# Maximo de ram
ENV JAVA_MAX_PERCENTAGE="80.0"
# Nome do servidor que foi aberto pra adicionar no bungee
ENV SERVER_NAME="Default"
# Se o servidor está em modo de desenvolvimento
ENV SERVER_DEV=false
# Se o servidor está em modo debug(mandar mais mensagens)
ENV SERVER_DEBUG=false
# Onde que ele vai tentar chamar a api do servidor
ENV SERVER_API_URL=$API_URL
# A token que ele vai usar
ENV SERVER_API_TOKEN=$API_TOKEN
# Comando usado pra fechar o server
ENV SERVER_STOP_COMMAND="stop"
# Removendo a barra de status do tmux
RUN echo "set -g status off" > /root/.tmux.conf

EXPOSE 5005

COPY copyJars.sh /server/
COPY run.sh /server/
COPY cmd /usr/bin/

CMD ["/server/run.sh"]
