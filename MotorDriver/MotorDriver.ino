// Прошивка драйвера двигателей. Версия 2019.05.22.

// Закомментировано все, что свзяано с вращением камеры.

#include <Servo.h> 
 
const int LEFT_MOTOR_DIR_PIN = 7;
const int LEFT_MOTOR_PWM_PIN = 9;
const int RIGHT_MOTOR_DIR_PIN = 8;
const int RIGHT_MOTOR_PWM_PIN = 10;

// Эти константы задают крайние положения для сервоприводов
//const byte CAMERA_EXTREME_LEFT = 180;
//const byte CAMERA_CENTER = 90;
//const byte CAMERA_EXTREME_RIGHT = 0;
//const byte CAMERA_EXTREME_DOWN = 42;
//const byte CAMERA_STRAIGHT_FORWARD = 75;
//const byte CAMERA_EXTREAM_UP = 180;
//const byte CAMERA_STEP = 3;

//Servo cameraLeftRight;
//Servo cameraUpDown;

// Хранимое значение направления вращения двигателей.
byte leftDirection = 0;
byte rightDirection = 0;

// Хранимое значепние мощности двигателя.
byte leftPower = 0;
byte rightPower = 0;

//----------------------------------------------------------
void setup()
{
    // Setup the pins
    pinMode( LEFT_MOTOR_DIR_PIN, OUTPUT );
    pinMode( LEFT_MOTOR_PWM_PIN, OUTPUT );
    pinMode( RIGHT_MOTOR_DIR_PIN, OUTPUT );
    pinMode( RIGHT_MOTOR_PWM_PIN, OUTPUT );

    digitalWrite( LEFT_MOTOR_DIR_PIN, HIGH );
    digitalWrite( RIGHT_MOTOR_DIR_PIN, HIGH );
    
//    cameraLeftRight.attach(12);
//    cameraLeftRight.write(CAMERA_CENTER);
//    cameraUpDown.attach(13);
//    cameraUpDown.write(CAMERA_STRAIGHT_FORWARD);

    Serial.begin(115200, SERIAL_8N1);
    while (!Serial) {}
    
}
 
//----------------------------------------------------------
void loop()
{
  byte pos;
  
  if(Serial.available() > 0){
  
    // Чтение команды
    int inByte = Serial.read();

    if(inByte == 'd') {
      delay(5);

      if(Serial.available() >= 4){
        
        // Направление вращение левого двигателя.
        byte b = Serial.read();
        if( b != leftDirection){
          leftDirection = b;
          if(leftDirection == 0){
            digitalWrite( LEFT_MOTOR_DIR_PIN, HIGH);
          } else {
            digitalWrite( LEFT_MOTOR_DIR_PIN, LOW);
          }
        }
        
        // Мощность вращение левого двигателя.
        b = Serial.read();
        if( b != leftPower){
          leftPower = b;
          analogWrite( LEFT_MOTOR_PWM_PIN, leftPower);
        }
        
        // Направление вращение левого двигателя.
        b = Serial.read();
        if( b != rightDirection){
          rightDirection = b;
          if(rightDirection == 0){
            digitalWrite( RIGHT_MOTOR_DIR_PIN, HIGH);
          } else {
            digitalWrite( RIGHT_MOTOR_DIR_PIN, LOW);
          }
        }
        
        // Мощность вращение левого двигателя.
        b = Serial.read();
        if( b != rightPower){
          rightPower = b;
          analogWrite( RIGHT_MOTOR_PWM_PIN, rightPower);
        }
        
      }
      
    }
 
//    // Обработка команд от малинки
//    switch(inByte){
//      case 'u':
//        // Камера вверх
//        pos = cameraUpDown.read();
//        pos += CAMERA_STEP;
//        if(pos < CAMERA_EXTREAM_UP) {
//          cameraUpDown.write(pos);
//        }
//        Serial.println(pos);
//        break;
//        
//      case 'd':
//        // Камера вверх
//        pos = cameraUpDown.read();
//        pos -= CAMERA_STEP;
//        if(pos > CAMERA_EXTREME_DOWN) {
//          cameraUpDown.write(pos);
//        }
//        Serial.println(pos);
//        break;
//
//      case 'q':
//        // Камера влево
//        pos = cameraLeftRight.read();
//        pos += CAMERA_STEP;
//        if(pos < CAMERA_EXTREME_LEFT) {
//          cameraLeftRight.write(pos);
//        }
//        Serial.println(pos);
//        break;
//        
//      case 'e':
//        // Камера вправо
//        pos = cameraLeftRight.read();
//        pos -= CAMERA_STEP;
//        if(pos > CAMERA_EXTREME_RIGHT) {
//          cameraLeftRight.write(pos);
//        }
//        Serial.println(pos);
//        break;
//
//      case 'w':
//        // Камера по центру.
//        cameraLeftRight.write(CAMERA_CENTER);
//        cameraUpDown.write(CAMERA_STRAIGHT_FORWARD);
//        break;
//    }
    
    Serial.write(inByte);
    Serial.write('\n');
  
  } else {
    // Если никакая команда не пришла, то поспать немного, чтобы зря не расходовать батарею.
    delay(10);
  }

}
