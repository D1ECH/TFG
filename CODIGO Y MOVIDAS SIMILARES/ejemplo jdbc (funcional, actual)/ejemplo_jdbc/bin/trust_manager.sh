#!/bin/bash

#Colours
greenColour="\e[0;32m\033[1m"
endColour="\033[0m\e[0m"
redColour="\e[0;31m\033[1m"
blueColour="\e[0;34m\033[1m"
yellowColour="\e[0;33m\033[1m"
purpleColour="\e[0;35m\033[1m"
turquoiseColour="\e[0;36m\033[1m"
grayColour="\e[0;37m\033[1m"

function ctrl_c(){
  echo -e "\n\n${redColour}[!] Saliendo...${endColour}\n"
  tput cnorm
  exit 1
}

# Ctrl + c
trap ctrl_c INT




# Broker MQTT
BROKER="mqtt://localhost:1883"

# Client IDs
CLIENT1_ID="client1"
CLIENT2_ID="client2"

# Topics
TOPIC="test_topic"

# Mensaje a publicar
MESSAGE="Hello, MQTT!"

# Función para el cliente 1 (publicador)
client1() {
    mqtt pub -t "$TOPIC" -m "$MESSAGE" -h localhost -p 1883
}


# Función para el cliente 2 (suscriptor)
client2() {
    mqtt sub -t "$TOPIC" -h localhost -p 1883 |
    while read -r line
    do
        echo "Mensaje recibido: $line"
    done
}

# Ejecutar el cliente 1 en segundo plano
client2 &
# Esperar un segundo para asegurarse de que el cliente 1 publique el mensaje
sleep 1
# Ejecutar el cliente 2 en segundo plano
client1 &

# Esperar un segundo para asegurarse de que el cliente 2 esté suscrito antes de salir
sleep 1