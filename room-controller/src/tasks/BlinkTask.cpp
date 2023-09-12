#include "BlinkTask.h"

BlinkTask::BlinkTask(int pin){
  this->led = new Led(pin); 
}
  
void BlinkTask::init(int period){
  Task::init(period);
  ledState = AUTO;    
}
  
void BlinkTask::tick(){
  if(automatic) {
    ledState = AUTO;
  } else {
    ledState = MANUAL;
  }

  switch (ledState){
    case AUTO:
      if(movement_rel && !is_day) {
        led->switchOn();
        light_on = true;
      } else {
        led->switchOff();
        light_on = false;
      }
    break;
    
    case MANUAL:
      light_on = is_light;
      if(is_light) {
        led->switchOn();
      } else {
        led->switchOff();
      }
    break;
  }
}
