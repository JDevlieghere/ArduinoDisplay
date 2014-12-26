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
    if(received == "_DISABLE"){
      disable();
    }else if(received == "_ENABLE"){
      enable();
    }
    }else{
      print(received);
    }
  }
}

void print(String s){
  lcd.clear();
  int currentLine = 0;
  int increment = LCD_ROWS;
  for (int start = 0; start < s.length() ; start += increment){
    if(currentLine < 4){
      while(s.substring(start).startsWith(" ")){
        start++;
      }
      String line;
      if(start + LCD_ROWS > s.length()){
        line = s.substring(start);
      }else{
        line = s.substring(start, start + LCD_ROWS);
      }
      lcd.setCursor(0,currentLine++);
      lcd.print(line);
    }
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

void disable(){
  lcd.noDisplay();
  lcd.noBacklight();
}

void enable(){
  lcd.display();
  lcd.backlight();
}