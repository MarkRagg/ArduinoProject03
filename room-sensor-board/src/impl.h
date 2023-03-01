#include "./devices/Pir.h"
#include "./devices/Led.h"
#include <Arduino.h>

class impl {
public:
  static void moveDetectorTask( void* parameter);
};