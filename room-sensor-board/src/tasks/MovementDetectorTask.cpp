#include "MovementDetectorTask.h"

MovementDetectorTask::MovementDetectorTask(const int pir_pin, const int treshold_time) {
  pir = new Pir(pir_pin, treshold_time);
}

void MovementDetectorTask::init(int period) {
  xTaskCreatePinnedToCore(this->moveDetector,"Task1",10000,NULL,1,&Task1,0);
}

void MovementDetectorTask::moveDetector( void* a) {
}