#ifndef __ROLLERBLINDSTASK__
#define __ROLLERBLINDSTASK__

#include "Task.h"
#include "../devices/ServoMotor.h"
#include "SerialTask.h"

class RollerBlindsTask: public Task {
public:
  RollerBlindsTask(ServoMotor* servoMotor);
  void init(int period);
  void tick();

private:
  ServoMotor* servo;
  enum { UP, DOWN, MANUAL} rollerBlindsState;
};

#endif