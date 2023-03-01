#include <Arduino.h>
#include "./devices/Photoresistor.h"
#include "./devices/Pir.h"
#include "impl.h"

#define LIGHT_THRESHOLD 600

/*
 * Second example, about tasks that can be run
 * thanks to FreeRTOS support.  
 *
 */
 
TaskHandle_t Task1;
TaskHandle_t Task2;

const int pir_led = 39;
//Photoresistor* photoresistor = new Photoresistor(40, 100);
//Pir* pir = new Pir(pir_led, 10);

void setup() {
  Serial.begin(9600); 
  xTaskCreatePinnedToCore(impl::moveDetectorTask,"Task1",10000,NULL,1,&Task1,0);
  xTaskCreatePinnedToCore(impl::photoresistorTask,"Task2",10000,NULL,1,&Task2,1);
}

void loop() {
  //Serial.print("this is the main loop running on core ");
  //Serial.println(xPortGetCoreID());

}