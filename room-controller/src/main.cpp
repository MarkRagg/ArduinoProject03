#include <Arduino.h>
#include "scheduler/Scheduler.h"
#include "tasks/SerialCommunication.h"

Scheduler sched;

void setup() {
  Task* serial = new SerialCommunication();
  Serial.begin(9600);
  Serial.setTimeout(1);
  serial->init(500);

  sched.addTask(serial);
}

void loop() {
  Serial.println("CIAO");
  delay(1000);
  //sched.schedule();
}