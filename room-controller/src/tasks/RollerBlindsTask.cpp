#include "RollerBlindsTask.h"

RollerBlindsTask::RollerBlindsTask(ServoMotor* servoMotor){
  this->servo = servoMotor;
}
  
void RollerBlindsTask::init(int period){
  Task::init(period);
  rollerBlindsState = UP;
}
  
void RollerBlindsTask::tick(){
    if(automatic) {
    rollerBlindsState = UP
  } else {
    rollerBlindsState = MANUAL;
  }
  
  switch (rollerBlindsState){
    case UP:
      servo->move(10);
      if(!is_day && !movement_rel) {
        rollerBlindsState = DOWN;
      }
    break;
    
    case DOWN:
      servo->move(170);    
      if(is_day && movement_rel) {
        rollerBlindsState = UP;
      }
    break;

    case MANUAL:
      servo->move(rollerBlindsOpening);
    break;
  }
}
