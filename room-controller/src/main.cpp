#include <Arduino.h>
#include "scheduler/Scheduler.h"
#include "tasks/SerialCommunication.h"
#include "tasks/MsgService.h"

Scheduler sched;
String b;
int comma = 0;
int colon = 0;

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

  MsgService.sendMsg("TRAMOOne");

  if (MsgService.isMsgAvailable()) {
    Msg* msg = MsgService.receiveMsg();    
    Serial.println(msg->getContent());
    
    /* NOT TO FORGET: message deallocation */
    delete msg;
  }
}