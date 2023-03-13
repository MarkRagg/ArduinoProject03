#include <Arduino.h>
#include "scheduler/Scheduler.h"
#include "tasks/MsgService.h"
#include <ArduinoJson.h>

Scheduler sched;

int test1 = 1;
int test2 = 2;

String str;

MsgServiceClass MsgService;

void setup() {
  MsgService.init();
}

void loop() {
  delay(1000);
  MsgService.serialEvent();
  if (MsgService.isMsgAvailable()) {
    Msg* msg = MsgService.receiveMsg(); 
    StaticJsonDocument<56> doc;
    deserializeJson(doc, msg->getContent());
    serializeJson(doc, Serial);
    Serial.println("");  

    /* NOT TO FORGET: message deallocation */
    delete msg;
  }
}