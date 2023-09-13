#include <Arduino.h>
#include <WiFi.h>
#include <PubSubClient.h>
#include "./devices/Photoresistor.h"
#include "./devices/Pir.h"
#include "impl.h"
#include <ArduinoJson.h>

#define MSG_BUFFER_SIZE  50
#define LIGHT_THRESHOLD 600

char str[56];

/* Take global variables */

extern bool day;
extern bool movement;

/* wifi network info */

const char* ssid = "ap";
const char* password = "admin222";

/* MQTT server address */
const char* mqtt_server = "broker.mqtt-dashboard.com";

/* MQTT topic */
const char* topic1 = "light";
const char* topic2 = "movement";

/* MQTT client management */

WiFiClient espClient;
PubSubClient client(espClient);


unsigned long lastMsgTime = 0;
char msg1[MSG_BUFFER_SIZE];
char msg2[MSG_BUFFER_SIZE];
 
TaskHandle_t Task1;
TaskHandle_t Task2;

void setup_wifi() {

  delay(10);

  Serial.println(String("Connecting to ") + ssid);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

/* MQTT subscribing callback */

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.println(String("Message arrived on [") + topic + "] len: " + length );
}

void reconnect() {
  
  // Loop until we're reconnected
  
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    
    // Create a random client ID
    String clientId = String("esp-")+String(random(0xffff), HEX);

    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void setup() {
  Serial.begin(9600);

  // Serial.begin(115200);
  setup_wifi();
  randomSeed(micros());
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);

  xTaskCreatePinnedToCore(impl::moveDetectorTask,"Task1",10000,NULL,1,&Task1,0);
  xTaskCreatePinnedToCore(impl::photoresistorTask,"Task2",10000,NULL,1,&Task2,1);
}

void loop() {

  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  unsigned long now = millis();
  StaticJsonDocument<56> dayJson;
  StaticJsonDocument<56> movementJson;

  if (now - lastMsgTime > 10000) {
    lastMsgTime = now;

    /* creating a msg in the buffer */
    snprintf (msg1, MSG_BUFFER_SIZE, "Light: %d", day);
    snprintf (msg2, MSG_BUFFER_SIZE, "Movement: %d", movement);

    Serial.println(String("Publishing message: ") + msg1);
    Serial.println(String("Publishing message: ") + msg2);

    dayJson["day"] = day;
    movementJson["movement"] = movement;

    serializeJson(dayJson, msg1);
    serializeJson(movementJson, msg2);

    /* publishing the msg */
    client.publish(topic1, msg1);  
    client.publish(topic2, msg2);  
  }
}