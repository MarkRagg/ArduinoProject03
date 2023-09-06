#include "RollerBlindsTask.h"

bool is_light;
bool is_day;
bool movement_rel;
int rollerBlindsOpening;

RollerBlindsTask::RollerBlindsTask(int pin){
  this->servo = new ServoMotor(pin); 
}
  
void RollerBlindsTask::init(int period){
  Task::init(period);
  rollerBlindsState = UP;
}
  
void RollerBlindsTask::tick(){
  switch (rollerBlindsState){
    case UP:
      servo->move(0);
      if(!is_day && !movement_rel) {
        rollerBlindsState = DOWN;
      }
    break;
    
    case DOWN:
      servo->move(180);    
      if(is_day && movement_rel) {
        rollerBlindsState = UP;
      }
    break;

    case MANUAL:
      servo->move(rollerBlindsOpening);
    break;
  }
}
