#include "Arduino.h"
#include "../devices/Led.h"
#include "SerialCommunication.h"

extern bool lightOn;

void SerialCommunication::init(int period) {
  Task::init(period);
  Serial.begin(9600);
  Serial.setTimeout(1);
}

void SerialCommunication::tick() {
  Serial.println("LightOn: "+ String(lightOn));
  if (Serial.available()){
  char data = Serial.read();
    if (data =='1'){
      Serial.println("ON");  
    } else if (data =='0'){
      Serial.println("OFF");
    }
  }  
}