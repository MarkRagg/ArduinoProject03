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
    StaticJsonDocument<56> data;
    deserializeJson(body, msg->getContent());
    is_day = body["day"];
    data["day"] = body["day"];
    data["movement"] = body["movement"];
    movement_rel = body["movement"];
    rollerBlindsOpening = body["rollerBlindsAngle"];
    serializeJson(body, Serial);
    Serial.println("");

    // day = body["day"];
    // movement = body["movement"];
    // rollerBlindsOpening = body["angle"];

    /* NOT TO FORGET: message deallocation */
    delete msg;
  }
}