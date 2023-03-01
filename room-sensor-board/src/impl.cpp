#include "impl.h"

#define GREEN_LED 38
#define PIR_LED 39
#define PIR_TRESHOLD 10
#define PHOTORESISTOR_LED 11
#define PHOTORESISTOR_TRESHOLD 600

void impl::moveDetectorTask( void* parameter) {
  Pir* pir = new Pir(PIR_LED, PIR_TRESHOLD);
  Led* green_led = new Led(GREEN_LED);
  pir->calibrating();

  for(;;) {
    if(pir->isDetected()) {
      //Serial.println("Detected!");
      green_led->switchOn();
    } else {
      //Serial.println("Not Detected!");
      green_led->switchOff();
    }
    delay(100);
  }
}

void impl::photoresistorTask( void* parameter) {
  Photoresistor* photores = new Photoresistor(PHOTORESISTOR_LED, PHOTORESISTOR_TRESHOLD);

  for(;;) {
    Serial.println(photores->readLuminosity());
    delay(100);
  }
}