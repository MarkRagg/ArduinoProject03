#ifndef __SERIALTASK__
#define __SERIALTASK__

#include "Task.h"
#include "tasks/MsgService.h"
#include <ArduinoJson.h>
// #include "RollerBlindsTask.h"

extern bool is_light;
extern bool light_on;
extern bool is_day;
extern bool movement_rel;
extern int rollerBlindsOpening;
extern bool automatic;
extern bool bt_command;

class SerialTask: public Task {
public:
  void init(int period);
  void tick();

private:
  int baudrate;
  MsgServiceClass MsgService;
  String str;
};

#endif