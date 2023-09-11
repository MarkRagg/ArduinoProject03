#include "SerialTask.h"

bool is_light;
bool is_day;
bool movement_rel;
int rollerBlindsOpening;
bool automatic;

void SerialTask::init(int period) {
  Task::init(period);
  MsgService.init();
}

void SerialTask::tick() {
  MsgService.serialEvent();

  if (MsgService.isMsgAvailable()) {
    Msg* msg = MsgService.receiveMsg(); 
    StaticJsonDocument<112> body;
    StaticJsonDocument<112> data;

    deserializeJson(body, msg->getContent());
    automatic = body["automatic"];
    data["automatic"] = body["automatic"];
    
    is_light = body["lightOn"];
    data["light"] = body["lightOn"];

    is_day = body["day"];
    data["day"] = body["day"];

    movement_rel = body["movement"];
    data["movement"] = body["movement"];

    rollerBlindsOpening = body["rollerBlindsAngle"];
    data["rollerBlindsAngle"] = body["rollerBlindsAngle"];

    serializeJson(data, Serial);
    Serial.println("");

    /* NOT TO FORGET: message deallocation */
    delete msg;
  }
}