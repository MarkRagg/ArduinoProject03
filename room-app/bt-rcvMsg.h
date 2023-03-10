#ifndef __MSGSERVICEBT__
#define __MSGSERVICEBT__

#include "Arduino.h"
#include "SoftwareSerial.h"

class Msg {
  String content;

public:
  Msg(String content){
    this->content = content;
  }
  
  String getContent(){
    return content;
  }
};

class MsgServiceSerial {
    
public: 
  
  Msg* currentMsg;
  bool msgAvailable;

  void init();  

  bool isMsgAvailable();
  Msg* receiveMsg();

  void sendMsg(const String& msg);
};

class MsgServiceBluetooth {
    
public: 

  Msg* currentMsg;
  bool msgAvailable;

  void init();  

  bool isMsgAvailable();
  Msg* receiveMsg();  
 
  void sendMsg(const String& msg);
};

void readSerialMessage(bool useBT, bool useSerial);

extern MsgServiceSerial MsgService;
extern MsgServiceBluetooth MsgServiceBT;

#endif
