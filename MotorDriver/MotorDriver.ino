// Прошивка драйвера двигателей. Версия 2015-09-27.

// Небольшая программа для демонстрации работы платы Dagu Mini Driver. 
// По идее робот должен двигаться по квадрату, постоянно поворачивая налево.
 
const int LEFT_MOTOR_DIR_PIN = 7;
const int LEFT_MOTOR_PWM_PIN = 9;
const int RIGHT_MOTOR_DIR_PIN = 8;
const int RIGHT_MOTOR_PWM_PIN = 10;
const int LED = 13;

// Этой константой регулируется длинна ребра квадрата, по которой движется робот.
const int DRIVE_FORWARD_TIME_MS = 500;

// Этой константой регулируется угол, на который каждый раз будет поворачивать робот.
const int TURN_TIME_MS = 250;

// Этими константами можно выравнять движение вперед, если робот сремится завернуть вправо или в лево из-за того, что двигатели разные.
const byte LEFT_MOTOR_SPEED = 220;
const byte RIGHT_MOTOR_SPEED = 255;

int led_status = HIGH;

//----------------------------------------------------------
void setup()
{
    // Setup the pins
    pinMode( LEFT_MOTOR_DIR_PIN, OUTPUT );
    pinMode( LEFT_MOTOR_PWM_PIN, OUTPUT );
    pinMode( RIGHT_MOTOR_DIR_PIN, OUTPUT );
    pinMode( RIGHT_MOTOR_PWM_PIN, OUTPUT );

    pinMode( LED, OUTPUT );

    Serial.begin(9600, SERIAL_8N1);
    while (!Serial) {}
    
    digitalWrite( LED, led_status );
    
}
 
//----------------------------------------------------------
void loop()
{
  
  if(Serial.available() > 0){
  
    int inByte = Serial.read(); 
 
    switch(inByte){
      case 'a':
        // Двигаемся вперед
        digitalWrite( LEFT_MOTOR_DIR_PIN, HIGH );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, HIGH );
        analogWrite( LEFT_MOTOR_PWM_PIN, LEFT_MOTOR_SPEED );
        analogWrite( RIGHT_MOTOR_PWM_PIN, RIGHT_MOTOR_SPEED );
        //delay( DRIVE_FORWARD_TIME_MS );
        break;

      case 's':
        // Поворачиваем.
        digitalWrite( LEFT_MOTOR_DIR_PIN, HIGH );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, LOW );
        analogWrite( LEFT_MOTOR_PWM_PIN, 128 );
        analogWrite( RIGHT_MOTOR_PWM_PIN, 128 );
        //delay( TURN_TIME_MS );
        break;

      case 'd':
        // Останавливаемся.
        digitalWrite( LEFT_MOTOR_DIR_PIN, HIGH );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, HIGH);
        analogWrite( LEFT_MOTOR_PWM_PIN, 0 );
        analogWrite( RIGHT_MOTOR_PWM_PIN, 0 );
        //delay( 2000 );
    }
    
    Serial.write(inByte);
    Serial.write('\n');
  
  } else {
    Serial.write("Waiting...");
  } 

  if(led_status == HIGH){
    led_status = LOW;
  } else {
    led_status = HIGH;
  }
  digitalWrite( LED, led_status );
 
}

