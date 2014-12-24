#include <Wire.h>  // Comes with Arduino IDE
#include <string.h>
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x27, 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE);

#define LCD_ROWS 20
#define LCD_COLS 4

void setup(){
  Serial.begin(9600);
  lcd.begin(LCD_ROWS,LCD_COLS);
  lcd.backlight();
  lcd.setCursor(7,1);
  lcd.print("Ready!");
}

void loop(){
  if(Serial.available()){
    String received = Serial.readStringUntil('\n');
    print(received);
  }
}

void print(String s){
  lcd.clear();
  int currentLine = 0;
  int increment = LCD_ROWS;
  for (int start = 0; start < s.length() ; start += increment){
    int end;
    if(start + LCD_ROWS >= s.length()){
      end = s.length();
    }else{
      end = start  + LCD_ROWS;
    }
    lcd.setCursor(0,currentLine++);
    String line = s.substring(start, end);
    lcd.print(line);
  }
  delay(100);
}

void blink(int n){
  for(int i = 0; i<n; i++)
  {
    lcd.backlight();
    delay(250);
    lcd.noBacklight();
    delay(250);
  }
}

