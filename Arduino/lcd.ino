#include <LiquidCrystal.h>

// Initialize the library with the appropriate pins
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

void setup(){
	// Configure LCD
	lcd.begin(16, 4);

	// Initialize serial communication
	Serial.begin(9600);

	// Say hello
	Serial.println("Arduino says Hello!");
}

void loop()
{
	if (Serial.available()) {
		// Wait for data to arrive
		delay(100);

		// Clear the Display
		lcd.clear();

		// Write data to screen
		while (Serial.available() > 0) {
			lcd.write(Serial.read());
		}
	}
}