#include "Arduino.h"
#include "MsgService.h"

bool MsgServiceClass::isMsgAvailable(){
  return this->msgAvailable;
}

Msg* MsgServiceClass::receiveMsg(){
  if (this->msgAvailable){
    Msg* msg = currentMsg;
    this->msgAvailable = false;
    this->currentMsg = NULL;
    this->content = "";
    return msg;  
  } else {
    return NULL; 
  }
}

void MsgServiceClass::init(){
  Serial.begin(9600);
  while(!Serial) {

  }
  this->content.reserve(256);
  this->content = "";
  this->currentMsg = NULL;
  this->msgAvailable = false;  
}

void MsgServiceClass::sendMsg(const String& msg){
  Serial.println(msg);  
}

// void MsgServiceClass::sendMsg(const Msg& msg){
//   Serial.println(msg.getContent());  
// }

void MsgServiceClass::serialEvent() {
  /* reading the content */
  while (Serial.available()) {
    char ch = (char) Serial.read();
    if (ch == '\n'){
      this->currentMsg = new Msg(this->content);
      this->msgAvailable = true;      
    } else {
      this->content += ch;      
    }
  }
}
