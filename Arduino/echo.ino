#include <stdio.h>

void setup() {
	// Initialize Serial Communication
	Serial.begin(9600);
	// Say Hello
	Serial.println("Arduino says Hello!");
}

void loop() {
	if(Serial.available()){
		String received = Serial.readStringUntil('\n');
		Serial.println(received);
	}
}

