#include "BlinkTask.h"
#include "main.cpp"

BlinkTask::BlinkTask(int pin){
  this->led = new Led(pin); 
}
  
void BlinkTask::init(int period){
  Task::init(period);
  ledState = AUTO;    
}
  
void BlinkTask::tick(){
  switch (ledState){
    case AUTO:
      if(movement && !day) {
        led->switchOn();
      } else {
        led->switchOff();
      }
    break;
    
    case MANUAL:
      
    break;
  }
}
