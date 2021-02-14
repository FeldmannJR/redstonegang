#!/bin/bash


copyJar(){
    cp -Lvf /builds/jars/${1}.jar /server/${2}
}
copyPlugin(){
    mkdir -p "/server/plugins/"
    copyJar $1 "plugins"
}

copyPluginAbsolute(){
     mkdir -p "/server/plugins/"
     cp -Lvf ${1}.jar /server/plugins

}
