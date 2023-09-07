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
    is_light = body["light"];
    data["light"] = body["light"];

    is_day = body["day"];
    data["day"] = body["day"];

    movement_rel = body["movement"];
    data["movement"] = body["movement"];

    rollerBlindsOpening = body["rollerBlindsAngle"];
    data["rollerBlindsAngle"] = body["rollerBlindsAngle"];

    serializeJson(body, Serial);
    Serial.println("");

    // day = body["day"];
    // movement = body["movement"];
    // rollerBlindsOpening = body["angle"];

    /* NOT TO FORGET: message deallocation */
    delete msg;
  }
}