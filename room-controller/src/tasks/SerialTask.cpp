#include "SerialTask.h"

void SerialTask::init(int period) {
  Task::init(period);
  MsgService.init();
}

void SerialTask::tick() {
  MsgService.serialEvent();
  if (MsgService.isMsgAvailable()) {
    Msg* msg = MsgService.receiveMsg(); 
    StaticJsonDocument<56> body;
    deserializeJson(body, msg->getContent());
    serializeJson(body, Serial);
    Serial.println("");

    // day = body["day"];
    // movement = body["movement"];
    // rollerBlindsOpening = body["angle"];

    /* NOT TO FORGET: message deallocation */
    delete msg;
  }
}