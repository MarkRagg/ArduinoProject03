#include "SerialTask.h"

bool is_light = false;
bool light_on = false;
bool is_day = false;
bool movement_rel = false;
int rollerBlindsOpening = 0;
bool automatic = true;

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
    
    is_light = body["lightOn"];
    

    is_day = body["day"];
    

    movement_rel = body["movement"];

    rollerBlindsOpening = body["rollerBlindsAngle"];

    data["automatic"] = body["automatic"];
    data["lightOn"] = light_on;
    data["day"] = body["day"];
    data["movement"] = body["movement"];
    data["rollerBlindsAngle"] = body["rollerBlindsAngle"];

    serializeJson(data, Serial);
    Serial.println("");

    /* NOT TO FORGET: message deallocation */
    delete msg;
  }
}