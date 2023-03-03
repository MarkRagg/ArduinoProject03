#include "./devices/Pir.h"
#include "./devices/Led.h"
#include "./devices/Photoresistor.h"
#include <Arduino.h>

extern bool day;

class impl {
public:
  static void moveDetectorTask( void* parameter);
  static void photoresistorTask( void* parameter);
};