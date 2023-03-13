#ifndef __ROLLERBLINDSTASK__
#define __ROLLERBLINDSTASK__

#include "Task.h"
#include "../devices/ServoMotor.h"

class RollerBlindsTask: public Task {
public:
  RollerBlindsTask(int pin);
  void init(int period);
  void tick();

private:
  ServoMotor* servo;
  enum { UP, DOWN, MANUAL} rollerBlindsState;
};

#endif