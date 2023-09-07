#include <Arduino.h>
#include "scheduler/Scheduler.h"
//#include "tasks/MsgService.h"
#include "tasks/BlinkTask.h"
#include "tasks/SerialTask.h"
#include "tasks/BtTask.h"
#include <ArduinoJson.h>

#include "devices/Led.h"


Scheduler sched;
// bool day = true;
// bool movement = false;
// bool manual = false;
// int rollerBlindsOpening = 0;
Led* led = new Led(5); 
// int test1 = 1;
// int test2 = 2;

// String str;
// MsgServiceClass MsgService;
Task* blinking = new BlinkTask(13);
Task* serial = new SerialTask();
Task* bt = new BtTask(3, 2, 5, 9);

void setup() {
  sched.init(200);

   Serial.begin(9600);
  // Serial.setTimeout(1);
  // serial->init(500);

  // sched.addTask(serial);  
  
  blinking->init(200);
  serial->init(1000);
  bt->init(200);

  sched.addTask(blinking);
  sched.addTask(serial);
  sched.addTask(bt);

  // doc["b"] = test1;
  // doc["a"] = test2;
  // serializeJson(doc, str);

  // MsgService.init();
}

void loop() {
  sched.schedule();
}
