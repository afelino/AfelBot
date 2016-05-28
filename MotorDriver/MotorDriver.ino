// Прошивка драйвера двигателей. Версия 3.0.0.
 
const int LEFT_MOTOR_DIR_PIN = 7;
const int LEFT_MOTOR_PWM_PIN = 9;
const int RIGHT_MOTOR_DIR_PIN = 8;
const int RIGHT_MOTOR_PWM_PIN = 10;

// Этими константами можно выравнять движение вперед, если робот сремится завернуть вправо или влево из-за того, что двигатели разные.
const byte LEFT_MOTOR_SPEED = 220;
const byte RIGHT_MOTOR_SPEED = 255;

//----------------------------------------------------------
void setup()
{
    // Setup the pins
    pinMode( LEFT_MOTOR_DIR_PIN, OUTPUT );
    pinMode( LEFT_MOTOR_PWM_PIN, OUTPUT );
    pinMode( RIGHT_MOTOR_DIR_PIN, OUTPUT );
    pinMode( RIGHT_MOTOR_PWM_PIN, OUTPUT );

    Serial.begin(9600, SERIAL_8N1);
    while (!Serial) {}
    
}
 
//----------------------------------------------------------
void loop()
{
  
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

      case 's':
        // Останавливаемся.
        digitalWrite( LEFT_MOTOR_DIR_PIN, HIGH );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, HIGH);
        analogWrite( LEFT_MOTOR_PWM_PIN, 0 );
        analogWrite( RIGHT_MOTOR_PWM_PIN, 0 );
    }
    
    Serial.write(inByte);
    Serial.write('\n');
  
  } else {
    // Если никакая команда не пришла, то поспать немного, чтобы зря не расходовать батарею.
    delay(10);
  }

}

