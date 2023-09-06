#ifndef __ROLLERBLINDSTASK__
#define __ROLLERBLINDSTASK__

#include "Task.h"
#include "../devices/ServoMotor.h"

extern bool is_light;
extern bool is_day;
extern bool movement_rel;
extern int rollerBlindsOpening;

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