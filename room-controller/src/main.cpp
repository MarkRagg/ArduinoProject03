#include <Arduino.h>
#include <ArduinoJson.h>
#include "scheduler/Scheduler.h"
#include "tasks/MsgService.h"
#include "tasks/RollerBlindsTask.h"
#include "tasks/BlinkTask.h"
#include "tasks/SerialTask.h"
#include "tasks/BtTask.h"
#include "devices/ServoMotor.h"

Scheduler sched;
ServoMotor* servoMotor = new ServoMotor(9);

Task* rollerBlinds = new RollerBlindsTask(servoMotor);
Task* blinking = new BlinkTask(5);
Task* serial = new SerialTask();
Task* bt = new BtTask(3, 2, 5, servoMotor);

void setup() {
  servoMotor->on();

  sched.init(200);

  Serial.begin(9600);
 
  rollerBlinds->init(500);
  blinking->init(200);
  serial->init(1000);
  bt->init(200);

  sched.addTask(blinking);
  sched.addTask(serial);
  sched.addTask(rollerBlinds);
  sched.addTask(bt);

}

void loop() {
  sched.schedule();
}
