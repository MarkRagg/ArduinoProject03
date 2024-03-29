#ifndef __BLINKTASK__
#define __BLINKTASK__

#include "Task.h"
#include "../devices/Led.h"
#include "SerialTask.h"

class BlinkTask: public Task {
public:
  BlinkTask(int pin);
  void init(int period);
  void tick();

private:
  Led* led;
  enum { AUTO, MANUAL} ledState;
};

#endif