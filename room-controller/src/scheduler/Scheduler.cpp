#include "Scheduler.h"
#include <TimerOne.h>

volatile bool timerFlag;

void timerHandler(void) {
  timerFlag = true;
}

void Scheduler::init(int basePeriod) {
  this->basePeriod = basePeriod;
  this->previousMillis = 0;
  timerFlag = false;
  nTasks = 0;
}

bool Scheduler::addTask(Task* task) {
  if (nTasks < MAX_TASKS-1) {
    taskList[nTasks] = task;
    nTasks++;
    return true;
  } else {
    return false; 
  }
}
  
void Scheduler::schedule() {   
  this->currentMillis = millis();
  if(this->currentMillis - this->previousMillis > basePeriod) {
    this->previousMillis = this->currentMillis;
    for (int i = 0; i < nTasks; i++) {
      if (taskList[i]->updateAndCheckTime(basePeriod)) {
        taskList[i]->tick();
      }
    }
  }
}