# RedstoneGang

RedstoneGang was a minecraft server built by [Feldmann](https://feldmann.dev) and [net32](https://isaias.dev), starting development around august of 2018, and finishing in the project october of 2019.   
Due to various problems the server was never officially opened.  

# Components

## Java

### [Common](redstonegang-common)
Common functions of the entire server, like database connection, punishments, user information, message broker client, among others.  
Developed using jOOQ, HikariCP, Netty, MariaDB

### [Terminal](redstonegang-terminal)
A super simple library in java to create terminal applications.  
Developed using JLine

### [Wire](redstonegang-wire)
Spigot Plugin responsible for all servers, using a modular approach with addons, making possible to run different types of server with the same plugin.  
Using the common package.  
Developed using SpigotMC, Citizens, WorldGuard, PacketListenerAPI, Common.

### [Repeater](redstonegang-repeater)
BungeeCord Plugin responsible for user authentication, and skin changes.  
Developed using BungeeCord, Common.

### [Network](redstonegang-network)
Simple messsage broker server.  
Developed using Netty, Terminal.

### [Discord Bot](redstonegang-discord)
A simple discord bot responsible for role syncronization with the server.  
Developed using JDA, LavaPlayer, Common, Terminal.

### [Docker Images](redstonegang-java-docker)
Docker images for running java based applications.

## Web

### [App/Api](redstonegang-website)
A website/api made with laravel, responsible for caching mojang api, skin uploading queue, user authentication, admin dashboard, among others.
Developed using Laravel

### [Docker Images](redstonegang-php-docker)
PHP based docker images for running the website/forum/shop.

## [Development Environment](redstonegang-dev-env)
The local development environment made with docker-compose, creating the database, website, network, minecraft servers, among others.
 