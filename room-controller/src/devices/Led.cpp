#include "Arduino.h"
#include "Led.h"

bool lightOn = false;

Led::Led(int pin) {
  this->pin = pin;
  pinMode(pin,OUTPUT);
}

void Led::switchOn() {
  lightOn = true;
  digitalWrite(pin,HIGH);
}

void Led::switchOff() {
  lightOn = false;
  digitalWrite(pin,LOW);
};
