#include <LiquidCrystal_I2C.h> // From https://bitbucket.org/fmalpartida/new-liquidcrystal
#include <Wire.h>              // Comes with Arduino IDE
#include <string.h>

#define LCD_ROWS 20
#define LCD_COLS 4

LiquidCrystal_I2C lcd(0x27, 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE);

void print();
void blink();
void disable();
void enable();

void setup() {
  Serial.begin(9600);
  lcd.begin(LCD_ROWS, LCD_COLS);
  lcd.setCursor(7, 1);
  lcd.print("Ready!");
}

void loop() {
  if (Serial.available()) {
    String received = Serial.readStringUntil('\n');
    if (received == "_DISABLE") {
      disable();
    } else if (received == "_ENABLE") {
      enable();
    } else {
      print(received);
    }
  }
}

void print(const String &s) {
  lcd.clear();
  int currentLine = 0;
  int increment = LCD_ROWS;
  for (int start = 0; start < s.length() && currentLine < 4;
       start += increment) {
    while (s.substring(start).startsWith(" ")) {
      start++;
    }
    String line;
    if (start + LCD_ROWS > s.length()) {
      line = s.substring(start);
    } else {
      line = s.substring(start, start + LCD_ROWS);
    }
    lcd.setCursor(0, currentLine++);
    lcd.print(line);
  }
  delay(100);
}

void blink(int n) {
  for (int i = 0; i < n; i++) {
    lcd.backlight();
    delay(250);
    lcd.noBacklight();
    delay(250);
  }
}

void disable() {
  lcd.noDisplay();
  lcd.noBacklight();
}

void enable() {
  lcd.display();
  lcd.backlight();
}
