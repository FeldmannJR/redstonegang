#!/bin/bash
XMLFILE="addons.xml"
if [ $1 ]; then
    XMLFILE="${1}"    
fi
if [ ! -f "${XMLFILE}" ]; then
    echo "Arquivo não encontrado"
    exit 0
fi
java -classpath jooq-3.11.7.jar:jooq-meta-3.11.7.jar:jooq-codegen-3.11.7.jar:mysql-connector-java-8.0.11.jar:. org.jooq.codegen.GenerationTool $XMLFILE
