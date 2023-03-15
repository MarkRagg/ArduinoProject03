#ifndef __MSGSERVICE__
#define __MSGSERVICE__

#include "Arduino.h"

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

class Pattern {
public:
  virtual boolean match(const Msg& m) = 0;  
};

class MsgServiceClass {
    
public: 
  String content;
  
  Msg* currentMsg;
  bool msgAvailable;

  void init();  

  bool isMsgAvailable();
  Msg* receiveMsg();
  
  void sendMsg(const String& msg);

  void serialEvent();
};

extern MsgServiceClass MsgService;

#endif