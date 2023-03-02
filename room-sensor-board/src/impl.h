#include "./devices/Pir.h"
#include "./devices/Led.h"
#include "./devices/Photoresistor.h"
#include <Arduino.h>

class impl {
public:
  static int day;
  static int movement;
  static void moveDetectorTask( void* parameter);
  static void photoresistorTask( void* parameter);
};