#ifndef __LED__
#define __LED__

extern bool lightOn;

class Led {
public:
	Led(int pin);
	void switchOn();
	void switchOff();

private:
	int pin;  
};

#endif