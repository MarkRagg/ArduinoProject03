#include "SerialTask.h"

bool is_light;
bool is_day;
bool movement_rel;
int rollerBlindsOpening;

void SerialTask::init(int period) {
  Task::init(period);
  MsgService.init();
}

void SerialTask::tick() {
  MsgService.serialEvent();

  // Serial.println("MESSAGGIO? "+MsgService.isMsgAvailable());

  if (MsgService.isMsgAvailable()) {
    Msg* msg = MsgService.receiveMsg(); 
    StaticJsonDocument<56> body;
    StaticJsonDocument<56> data;

    deserializeJson(body, msg->getContent());
    // Serial.println(body["movement"]);
    
    is_light = body["light"];
    data["light"] = body["lightOn"];

    is_day = body["day"];
    data["day"] = body["day"];

    movement_rel = body["movement"];
    data["movement"] = body["movement"];

    rollerBlindsOpening = body["rollerBlindsAngle"];
    data["rollerBlindsAngle"] = body["rollerBlindsAngle"];

    serializeJson(body, Serial);
    Serial.println("");

    // Serial.println("MOVEMENT "+ movement_rel);
    // Serial.println(movement_rel);

    // day = body["day"];
    // movement = body["movement"];
    // rollerBlindsOpening = body["angle"];

    /* NOT TO FORGET: message deallocation */
    delete msg;
  }
}