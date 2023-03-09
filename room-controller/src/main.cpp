#include <Arduino.h>
#include "scheduler/Scheduler.h"
#include "tasks/SerialCommunication.h"
#include "tasks/MsgService.h"
#include <ArduinoJson.h>

Scheduler sched;
String b;
int comma = 0;
int colon = 0;

int test1 = 1;
int test2 = 2;
DynamicJsonDocument doc(1024);
String str;

void setup() {
  Task* serial = new SerialCommunication();
  Serial.begin(9600);
  Serial.setTimeout(1);
  serial->init(500);

  sched.addTask(serial);
  b = "pera: ciao, mela: miao, banana: biao, kiwi: kiao";
  

  MsgService.init();
}

void loop() {
  // sched.schedule();
  // int MyI = b.indexOf(",",comma);
  // String s = b.substring(comma,MyI);
  // Serial.println(s + "1");
  // int My = b.indexOf(":",colon);
  // String s2 = b.substring(colon,My);
  // Serial.println(s2 + "2");
  // comma = MyI + 1;
  // colon = MyI + 1;
  // delay(1000);


  delay(1000);
  if (MsgService.isMsgAvailable()) {
    Msg* msg = MsgService.receiveMsg();    
    MsgService.sendMsg("Ricevuto");
    
    /* NOT TO FORGET: message deallocation */
    delete msg;
  } else {
    doc["test1"] = test1;
    doc["test2"] = test2;
    serializeJson(doc, str);
    MsgService.sendMsg(str);
  }
}