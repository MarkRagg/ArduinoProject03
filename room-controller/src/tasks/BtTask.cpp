#include "BtTask.h"
#include <ArduinoJson.h>

BtTask::BtTask(int rxPin, int txPin, int ledPin, int servoPin){
    this->rxPin = rxPin;
    this->txPin = txPin;
    this->led = new Led(ledPin); 
    this->servoMotor = new ServoMotor(servoPin);
}

void BtTask::init(int period){
    Task::init(period);
    channel = new SoftwareSerial(rxPin, txPin);
    channel->begin(9600);
}

void BtTask::tick(){
    Serial.print(".");

    /*
        Chek if there is data transmitted via BT in the Serial channel,
        then read character by character until the end of the message.
    */
    if (channel->available()) {

        msgChar = (char)channel->read();
        BT_input = "";

        while(msgChar != '\n') {
            BT_input.concat(msgChar);
            msgChar = (char)channel->read();
        }

        StaticJsonDocument<56> doc;
        deserializeJson(doc, BT_input);

        Serial.println(BT_input);
        if(doc["State"] == "ON") {
            led->switchOn();
        } else if (doc["State"] == "OFF") {
            led->switchOff();
        } else if ((int(doc["State"]) >= 0 ) && (int(doc["State"]) <= 100)) {
            servoMotor->move(int(doc["State"]));
        }

    }
}
