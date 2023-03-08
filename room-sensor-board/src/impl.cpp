#include "impl.h"

#define GREEN_LED 38
#define PIR_LED 39
#define PIR_TRESHOLD 10
#define PHOTORESISTOR_LED 11
#define PHOTORESISTOR_TRESHOLD 600

bool day;
bool movement;

void impl::moveDetectorTask(void* parameter) {
  Pir* pir = new Pir(PIR_LED, PIR_TRESHOLD);
  Led* green_led = new Led(GREEN_LED);
  movement = false;
  pir->calibrating();

  for(;;) {
    if(pir->isDetected()) {
      //Serial.println("Detected!");
      green_led->switchOn();
      movement = true;
    } else {
      //Serial.println("Not Detected!");
      green_led->switchOff();
      movement = false;
    }
    delay(100);
  }
}

void impl::photoresistorTask(void* parameter) {
  Photoresistor* photores = new Photoresistor(PHOTORESISTOR_LED, PHOTORESISTOR_TRESHOLD);
  day = true;

  for(;;) {
    if(photores->isLuminosityHigher()) {
      day = true;
    } else {
      day = false;
    }
    delay(100);
  }
}