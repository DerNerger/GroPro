#!/bin/bash

for FILE in `ls Tests/ | grep [^~]$ | grep -v .out$` 
do
	echo "Starte Programm mit Datei $FILE"
	java -jar ./dist/raetselErsteller.jar Tests/$FILE 
done
