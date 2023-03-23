#ifndef __SERIALTASK__
#define __SERIALTASK__

#include "Task.h"
#include "tasks/MsgService.h"
#include <ArduinoJson.h>

extern bool is_day;
extern bool movement_rel;

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