#include <Arduino.h>
#include "scheduler/Scheduler.h"
// #include "tasks/SerialCommunication.h"
#include "tasks/MsgService.h"
#include "tasks/BlinkTask.h"
#include <ArduinoJson.h>

Scheduler sched;
bool day = true;
bool movement = false;
bool manual = false;
int rollerBlindsOpening = 0;

int test1 = 1;
int test2 = 2;
DynamicJsonDocument doc(1024);
String str;

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

  doc["b"] = test1;
  doc["a"] = test2;
  serializeJson(doc, str);

  MsgService.init();
  delay(10000);
}

void loop() {

  delay(1000);
  if (MsgService.isMsgAvailable()) {
    Msg* msg = MsgService.receiveMsg();    
    MsgService.sendMsg("Ricevuto");

    String json = msg->getContent();

    DynamicJsonDocument body(1024);
    deserializeJson(body, json);

    day = body["day"];
    movement = body["movement"];
    rollerBlindsOpening = body["angle"];

    delete msg;
  } else {
    MsgService.sendMsg(str);
  }

  //sched.schedule();
}