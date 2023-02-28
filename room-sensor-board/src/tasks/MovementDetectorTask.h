#ifndef __LCDTASK__
#define __LCDTASK__

#include "../devices/Pir.h"
#include <Arduino.h>

class MovementDetectorTask {
public:
  MovementDetectorTask(const int pir_pin, const int treshold_time);
  void init(int period);  
  static void moveDetector(void* a);

private:
  Pir* pir;
  TaskHandle_t Task1;
};

#endif