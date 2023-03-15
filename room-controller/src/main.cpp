#include <Arduino.h>
#include "scheduler/Scheduler.h"
// #include "tasks/SerialCommunication.h"
#include "tasks/MsgService.h"
#include "tasks/BlinkTask.h"
#include <ArduinoJson.h>

Scheduler sched;
// bool day = true;
// bool movement = false;
// bool manual = false;
// int rollerBlindsOpening = 0;

// int test1 = 1;
// int test2 = 2;

String str;
MsgServiceClass MsgService;

void setup() {
  sched.init(200);

  // Task* serial = new SerialCommunication();
  Serial.begin(9600);
  Serial.setTimeout(1);
  // serial->init(500);

  // sched.addTask(serial);  
  Task* blinking = new BlinkTask(11);

  blinking->init(200);

  sched.addTask(blinking);

  // doc["b"] = test1;
  // doc["a"] = test2;
  // serializeJson(doc, str);

  MsgService.init();
}

void loop() {
  
  delay(1000);
  MsgService.serialEvent();
  if (MsgService.isMsgAvailable()) {
    Msg* msg = MsgService.receiveMsg(); 
    StaticJsonDocument<56> body;
    deserializeJson(body, msg->getContent());
    serializeJson(body, Serial);
    Serial.println("");  

    // day = body["day"];
    // movement = body["movement"];
    // rollerBlindsOpening = body["angle"];

    /* NOT TO FORGET: message deallocation */
    delete msg;
  }

  //sched.schedule();
}