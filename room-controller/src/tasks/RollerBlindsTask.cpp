#include "RollerBlindsTask.h"

RollerBlindsTask::RollerBlindsTask(ServoMotor* servoMotor){
  this->servo = servoMotor;
}
  
void RollerBlindsTask::init(int period){
  Task::init(period);
  rollerBlindsState = AUTO;
}
  
void RollerBlindsTask::tick(){
  if(automatic) {
    rollerBlindsState = AUTO;
  } else {
    rollerBlindsState = MANUAL;
  }
  
  switch (rollerBlindsState){
    case AUTO:
      if(!is_day && !movement_rel) {
        servo->move(0);
      } else if(is_day && movement_rel) {
        servo->move(180);    
      }
    break;

    case MANUAL:
      servo->move(rollerBlindsOpening);
    break;
  }
}
