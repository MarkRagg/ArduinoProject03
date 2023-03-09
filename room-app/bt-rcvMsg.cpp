#include "bt-rcvMsg.h"

String content;
SoftwareSerial channel(RX_PIN, TX_PIN);

MsgServiceSerial MsgService;
MsgServiceBluetooth MsgServiceBT;

bool MsgServiceSerial::isMsgAvailable(){
  return msgAvailable;
}

Msg* MsgServiceSerial::receiveMsg(){
  if (msgAvailable){
    Msg* msg = currentMsg;
    msgAvailable = false;
    currentMsg = NULL;
    content = "";
    return msg;  
  } else {
    return NULL; 
  }
}

void MsgServiceSerial::init(){
  Serial.begin(9600);
  content.reserve(128);
  content = "";
  currentMsg = NULL;
  msgAvailable = false;  
}

void MsgServiceSerial::sendMsg(const String& msg){
  Serial.println(msg);  
}

bool MsgServiceBluetooth::isMsgAvailable(){
  return msgAvailable;
}

Msg* MsgServiceBluetooth::receiveMsg(){
  if (msgAvailable){
    Msg* msg = currentMsg;
    msgAvailable = false;
    currentMsg = NULL;
    content = "";
    return msg;  
  } else {
    return NULL; 
  }
}

void MsgServiceBluetooth::init(){
  channel.begin(9600);
  content.reserve(128);
  content = "";
  currentMsg = NULL;
  msgAvailable = false;  
}

void readSerialMessage(bool useBT, bool useSerial){
   if(useSerial){
    while (Serial.available()) {
      char ch = (char) Serial.read();
      if (ch == '\n'){      
        if (content.length() > 0) {
            MsgService.currentMsg = new Msg(content);
            MsgService.msgAvailable = true;
        }
      } else {
        content += ch;      
      }
    }
  }
  if(useBT){
    while (channel.available()) {
      char ch = (char) channel.read();
      if (ch == '\n'){   
        if (content.length() > 0) {
            content.trim();
            MsgServiceBT.currentMsg = new Msg(content);
            MsgServiceBT.msgAvailable = true;
        }
      } else {
        content += ch;      
      }
    }
  }
}