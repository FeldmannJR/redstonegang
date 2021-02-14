# Como funciona 

## Estrutura
Os containeres bungee,network,spigot extendem o container base, ou sejá para construir eles é necessário primeiro construir o base 
### Como construir?
Dentro de cada pasta junto com o Dockerimage eu coloquei um ./build.sh pra ja construir com o nome certo.  
O correto seria usar um servidor, mas por enquanto ta bom.

### Como rodar um servidor?
Basta executar docker run -it -e VAR1=val -e VAR2=val redstonegang-spigot, porém para facilitar criei uns padrões aqui na pasta inicial, é só executar o sh que vai abrir um servidor top

### Rede
Neste momento eu conectei os containers aqui criados na rede do docker-compose que está o site / banco, assim é possível acessar eles pelo nome do container.

### 'Preciso mudar os arquivos padrões'
Os arquivos do files só são copiados ao construir a imagem, os unicos arquivos que são atualizados direto da sua maquina são os plugins que estão sendo montados dentro dos containers em /jars.
Então para modificar é necessário usar o build.sh na imagem que você alterou os arquivos!

## Environment Variables

### Base
Todas imagens usam estas variaveis
#### Java
 +  `JAVA_DEBUG=0|1` = Se vai o java vai abrir a porta para poder conectar um depurador remoto, ele vai abrir na porta 5005 do container, é preciso mapea-la para o host.
 + `JAVA_JAR_FILE` = Qual arquivo dentro da pasta /server/ ele irá executar, as imagens que herdam a base sobrescrevem esse variavel, não precisa passar.
 + `JAVA_JAR_ARGUMENTS` = Argumentos que serão passados para o jar
 + `JAVA_ARGUMENTS` = Argumentos que serão passados para o java. A imagem base já cuida da memória para você, isso aqui serve se querer calibrar alguma coisa por exemplo Gargabe Collector, a imagem spigot sobrescreve essa var
 + `JAVA_MIN_PERCENTAGE` = Porcentagem da memória passada pelo -m que será utilizado pelo `heap` no começo da execução, cuidado o java precisa de memória para outras coisas como por exemplo o stack.
  + `JAVA_MAX_PERCENTAGE` = Máximo de memória que será alocado para o heap! Cuidado!.
#### Server
  + `SERVER_NAME` = Nome do servidor que o Common está lendo para mandar para o socket/bungee, isto está substuindo o nome da pasta
  + `SERVER_DEBUG=0|1` = Se o servidor está em modo debug, é passado para o common e é possivel acessar pelo `RedstonGang.instance().DEBUG`
  + `SERVER_DEV=0|1` = Se o servidor está em modo developer(bloqueia acesso de quem n tem perm), é passado para o common e é possivel acessar pelo `RedstonGang.instance().DEV`
  + `SERVER_ENVIRONMENT=local|production` = Qual o ambiente do servidor, acessado por ``RedstoneGang.instance().ENVIRONMENT``
  + `SERVER_API_URL` = Url da RESTFUL api do servidor
  + `SERVER_API_TOKEN` = A token de acesso da api RESTFUL
### Spigot
  Caso queira acessar o servidor pelo seu pc é preciso mapear a porta 25565 com o argumento `-p PORTALOCAL:25565`, caso vá usar o bungee cord não é necessário.  
  Para salvar o mapa é necessário montar um volume em /server/worlds se não o mapa é **PERDIDO!** 
  A variavel mais importante é `SPIGOT_GAME_NAME` = Qual game/servidor o Wire vai iniciar, é preciso ser um nome do enum `br.com.redstonegang.wire.game.base.Games` onde você vai determinar qual jogo vai começar!
  Plugins: 
  + `PLUGIN_WORLD_GUARD=0|1` = Se o servidor vai usar o World Guard, assim copiando automaticamente
  + `PLUGIN_WORLD_EDIT=0|1` = Se o servidor vai usar o World Edit, assim copiando automaticamente
 
### Bungee
  O bungee não tem nenhuma variavel de ambiente própria ainda!
  Para acessar ele é só mapear a porta 25565
### Network (Servidor IO/NETTY)
  `NETWORK_PORT` = Qual porta o servidor vai rodar dentro do container, padrão 9000, escolhi alterar da porta 25565 para diferenciar dos outros servidores.


### Como mapear uma porta
Colocar no parametro de run do container `-p {PORTA_NA_SUA_MAQUINA}:{PORTA_NO_CONTAINER}`
Exemplo: `docker run -it -p 8080:80 nginx` vai mapear a porta 80 do container para a sua porta 8080, você vai poder ir no navegador acessar localhost:8080

### Como eu uso os volumes?
Você precisa especificar na criação do container (`run`) que vai usar um volume
Existem dois tipos de volumes que nos enteressam
#### Bind
O docker simplesmete vai montar uma pagina do seu pc dentro do container
usa passando o parametro `-v /pasta/do/seu/pc/:/pasta/no/container/`
#### Named 
O docker vai criar um volume com um nome especificado e o container vai usar esse volume para salvar as coisas. O volume aqui funciona como se fosse um HD.

 Só passar o parametro `-v nome-do-volume:/pasta/no/container`


Vantagens: Facil de controlar / fazer backup, da para compartilhar o mesmo volume entre varios containers.