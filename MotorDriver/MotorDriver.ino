// Прошивка драйвера двигателей. Версия 4.0.

#include <Servo.h> 
 
const int LEFT_MOTOR_DIR_PIN = 7;
const int LEFT_MOTOR_PWM_PIN = 9;
const int RIGHT_MOTOR_DIR_PIN = 8;
const int RIGHT_MOTOR_PWM_PIN = 10;

// Этими константами можно выравнять движение вперед, если робот сремится завернуть вправо или влево из-за того, что двигатели разные.
const byte LEFT_MOTOR_SPEED = 100;
const byte RIGHT_MOTOR_SPEED = 100;

// Эти константы задают крайние положения для сервоприводов
const byte CAMERA_EXTREME_LEFT = 180;
const byte CAMERA_CENTER = 90;
const byte CAMERA_EXTREME_RIGHT = 0;
const byte CAMERA_EXTREME_DOWN = 42;
const byte CAMERA_STRAIGHT_FORWARD = 75;
const byte CAMERA_EXTREAM_UP = 180;
const byte CAMERA_STEP = 3;

Servo cameraLeftRight;
Servo cameraUpDown;

//----------------------------------------------------------
void setup()
{
    // Setup the pins
    pinMode( LEFT_MOTOR_DIR_PIN, OUTPUT );
    pinMode( LEFT_MOTOR_PWM_PIN, OUTPUT );
    pinMode( RIGHT_MOTOR_DIR_PIN, OUTPUT );
    pinMode( RIGHT_MOTOR_PWM_PIN, OUTPUT );

    cameraLeftRight.attach(12);
    cameraLeftRight.write(CAMERA_CENTER);
    cameraUpDown.attach(13);
    cameraUpDown.write(CAMERA_STRAIGHT_FORWARD);

    Serial.begin(9600, SERIAL_8N1);
    while (!Serial) {}
    
}
 
//----------------------------------------------------------
void loop()
{
  byte pos;
  
  if(Serial.available() > 0){
  
    // Чтение команды
    int inByte = Serial.read(); 
 
    // Обработка команд от малинки
    switch(inByte){
      case 'f':
        // Двигаемся вперед
        digitalWrite( LEFT_MOTOR_DIR_PIN, HIGH );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, HIGH );
        analogWrite( LEFT_MOTOR_PWM_PIN, LEFT_MOTOR_SPEED );
        analogWrite( RIGHT_MOTOR_PWM_PIN, RIGHT_MOTOR_SPEED );
        break;

      case 'r':
        // Поворачиваем направо
        digitalWrite( LEFT_MOTOR_DIR_PIN,  LOW);
        digitalWrite( RIGHT_MOTOR_DIR_PIN, HIGH );
        analogWrite( LEFT_MOTOR_PWM_PIN, 128 );
        analogWrite( RIGHT_MOTOR_PWM_PIN, 128 );
        break;

      case 'l':
        // Поворачиваем налево
        digitalWrite( LEFT_MOTOR_DIR_PIN, HIGH );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, LOW );
        analogWrite( LEFT_MOTOR_PWM_PIN, 128 );
        analogWrite( RIGHT_MOTOR_PWM_PIN, 128 );
        break;

      case 'b':
        // Двигаемся назад
        digitalWrite( LEFT_MOTOR_DIR_PIN, LOW );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, LOW );
        analogWrite( LEFT_MOTOR_PWM_PIN, LEFT_MOTOR_SPEED );
        analogWrite( RIGHT_MOTOR_PWM_PIN, RIGHT_MOTOR_SPEED );
        break;

      case 'u':
        // Камера вверх
        pos = cameraUpDown.read();
        pos += CAMERA_STEP;
        if(pos < CAMERA_EXTREAM_UP) {
          cameraUpDown.write(pos);
        }
        Serial.println(pos);
        break;
        
      case 'd':
        // Камера вверх
        pos = cameraUpDown.read();
        pos -= CAMERA_STEP;
        if(pos > CAMERA_EXTREME_DOWN) {
          cameraUpDown.write(pos);
        }
        Serial.println(pos);
        break;

      case 'q':
        // Камера влево
        pos = cameraLeftRight.read();
        pos += CAMERA_STEP;
        if(pos < CAMERA_EXTREME_LEFT) {
          cameraLeftRight.write(pos);
        }
        Serial.println(pos);
        break;
        
      case 'e':
        // Камера вправо
        pos = cameraLeftRight.read();
        pos -= CAMERA_STEP;
        if(pos > CAMERA_EXTREME_RIGHT) {
          cameraLeftRight.write(pos);
        }
        Serial.println(pos);
        break;

      case 'w':
        // Камера по центру.
        cameraLeftRight.write(CAMERA_CENTER);
        cameraUpDown.write(CAMERA_STRAIGHT_FORWARD);
        break;

      case 's':
        // Останавливаемся.
        digitalWrite( LEFT_MOTOR_DIR_PIN, HIGH );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, HIGH);
        analogWrite( LEFT_MOTOR_PWM_PIN, 0 );
        analogWrite( RIGHT_MOTOR_PWM_PIN, 0 );
        break;

    }
    
    Serial.write(inByte);
    Serial.write('\n');
  
  } else {
    // Если никакая команда не пришла, то поспать немного, чтобы зря не расходовать батарею.
    delay(10);
  }

}

