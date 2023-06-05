#ifndef __SERIALTASK__
#define __SERIALTASK__

#include "Task.h"
#include "tasks/MsgService.h"
#include "tasks/MsgServiceBT.h"
#include <ArduinoJson.h>

extern bool is_day;
extern bool movement_rel;
extern int rollerBlindsOpening;

class SerialTask: public Task {
public:
  void init(int period);
  void tick();

private:
  int baudrate;
  MsgServiceClass MsgService;
  //MsgServiceBTClass MsgServiceBT;
  String str;
};

#endif