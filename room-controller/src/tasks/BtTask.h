#ifndef __BTTASK__
#define __BTTASK__

#include "Task.h"
#include "SoftwareSerial.h"
#include "MsgService.h"
#include "../devices/Led.h"
#include "../devices/ServoMotor.h"

class BtTask: public Task {

public:
    BtTask(int rxPin, int txPin, int ledPin, ServoMotor* servoMotor);
    void init(int period);
    void tick();

private:
    int rxPin;
    int txPin;
    String BT_input;                    // to store the complete message received via BT.
    char msgChar;                       // to store input character received via BT.
    Led* led;                           // the led to turn on/off.
    ServoMotor* servo;                  // the servoMotor to move.
    SoftwareSerial* channel;            // the channel where to read the data transmitted via BT.
};

#endif
