#include "RollerBlindsTask.h"
#include "main.cpp"

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
      if(!day && !movement) {
        rollerBlindsState = DOWN;
      }
    break;
    
    case DOWN:
      servo->move(180);    
      if(day && movement) {
        rollerBlindsState = UP;
      }
    break;

    case MANUAL:
    break;
  }
}
