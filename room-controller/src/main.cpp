#include <Arduino.h>
#include "scheduler/Scheduler.h"
#include "tasks/SerialCommunication.h"

Scheduler sched;

void setup() {
  Task* serial = new SerialCommunication();
  
  serial->init(500);

  sched.addTask(serial);
}

void loop() {
  sched.schedule();
}