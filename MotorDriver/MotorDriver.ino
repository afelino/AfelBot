// Прошивка драйвера двигателей. Версия 4.0.

const int LEFT_MOTOR_DIR_PIN = 7;
const int LEFT_MOTOR_PWM_PIN = 9;
const int RIGHT_MOTOR_DIR_PIN = 8;
const int RIGHT_MOTOR_PWM_PIN = 10;

const int CAMERA_LEFT_RIGHT_SERVO_PIN = 12;
const int CAMERA_UP_DOWN_SERVO_PIN = 13;

// Этими константами можно выравнять движение вперед, если робот сремится завернуть вправо или влево из-за того, что двигатели разные.
const int LEFT_MOTOR_SPEED = 500;
const int RIGHT_MOTOR_SPEED = 500;

// Эти константы задают крайние положения для сервоприводов
const byte CAMERA_EXTREME_LEFT = 122;
const byte CAMERA_CENTER = 73;
const byte CAMERA_EXTREME_RIGHT = 25;
const byte CAMERA_EXTREME_DOWN = 48;
const byte CAMERA_STRAIGHT_FORWARD = 66;
const byte CAMERA_EXTREAM_UP = 122;

int timerCounter = 0;
int cameraLeftRightServo = CAMERA_CENTER;
int cameraUpDownServo = CAMERA_STRAIGHT_FORWARD;
int leftMotor = 0;
int rightMotor = 0;

// Прерывание таймера.
SIGNAL (TIMER1_COMPA_vect){
  if(timerCounter == cameraLeftRightServo){
    digitalWrite (CAMERA_LEFT_RIGHT_SERVO_PIN, LOW);
  }

  if(timerCounter == cameraUpDownServo){
    digitalWrite (CAMERA_UP_DOWN_SERVO_PIN, LOW);
  }

  if(timerCounter == leftMotor){
    digitalWrite (LEFT_MOTOR_PWM_PIN, LOW);
  }

  if(timerCounter == rightMotor){
    digitalWrite (RIGHT_MOTOR_PWM_PIN, LOW);
  }

  timerCounter++;

  if(timerCounter == 1000){
    timerCounter = 0;

    if(cameraLeftRightServo){
      digitalWrite (CAMERA_LEFT_RIGHT_SERVO_PIN, HIGH);
    }
    if(cameraUpDownServo){
      digitalWrite (CAMERA_UP_DOWN_SERVO_PIN, HIGH);
    }
    if(leftMotor){
      digitalWrite (LEFT_MOTOR_PWM_PIN, HIGH);
    }
    if(rightMotor){
      digitalWrite (RIGHT_MOTOR_PWM_PIN, HIGH);
    }
  }
   
}

//----------------------------------------------------------
void setup()
{
    // Инициализируем 16 битный таймер
    TCCR1A = 0;                          // Сче 
    TCCR1B = _BV(CS11) | _BV(WGM12);     // Предделитель 8, то есть таймер будет работать на частоте в 8 раз меньше, чем основная частота.
    TCNT1 = 0;              // clear the timer count
    TIFR |= _BV(OCF1A);      // clear any pending interrupts;
    TIMSK |=  _BV(OCIE1A) ;  // enable the output compare interrupt
    OCR1A = 39;

    
    // Setup the pins
    pinMode( LEFT_MOTOR_DIR_PIN, OUTPUT );
    pinMode( LEFT_MOTOR_PWM_PIN, OUTPUT );
    pinMode( RIGHT_MOTOR_DIR_PIN, OUTPUT );
    pinMode( RIGHT_MOTOR_PWM_PIN, OUTPUT );
    pinMode( CAMERA_LEFT_RIGHT_SERVO_PIN, OUTPUT );
    pinMode( CAMERA_UP_DOWN_SERVO_PIN, OUTPUT );

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
        leftMotor = LEFT_MOTOR_SPEED;
        rightMotor = RIGHT_MOTOR_SPEED;
        break;

      case 'r':
        // Поворачиваем направо
        digitalWrite( LEFT_MOTOR_DIR_PIN, HIGH );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, LOW );
        leftMotor = LEFT_MOTOR_SPEED;
        rightMotor = RIGHT_MOTOR_SPEED;
        break;

      case 'l':
        // Поворачиваем налево
        digitalWrite( LEFT_MOTOR_DIR_PIN, LOW );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, HIGH );
        leftMotor = LEFT_MOTOR_SPEED;
        rightMotor = RIGHT_MOTOR_SPEED;
        break;

      case 'b':
        // Двигаемся назад
        digitalWrite( LEFT_MOTOR_DIR_PIN, LOW );
        digitalWrite( RIGHT_MOTOR_DIR_PIN, LOW );
        leftMotor = LEFT_MOTOR_SPEED;
        rightMotor = RIGHT_MOTOR_SPEED;
        break;

      case 'u':
        // Камера вверх
        if(cameraUpDownServo < CAMERA_EXTREAM_UP) {
          cameraUpDownServo++;
        }
        Serial.println(cameraUpDownServo);
        break;
        
      case 'd':
        // Камера вниз
        if(cameraUpDownServo > CAMERA_EXTREME_DOWN) {
          cameraUpDownServo--;
        }
        Serial.println(cameraUpDownServo);
        break;

      case 'q':
        // Камера влево
        if(cameraLeftRightServo < CAMERA_EXTREME_LEFT) {
          cameraLeftRightServo++;
        }
        Serial.println(cameraLeftRightServo);
        break;
        
      case 'e':
        // Камера вправо
        if(cameraLeftRightServo > CAMERA_EXTREME_RIGHT) {
          cameraLeftRightServo--;
        }
        Serial.println(cameraLeftRightServo);
        break;

      case 'w':
        // Камера по центру.
        cameraLeftRightServo = CAMERA_CENTER;
        cameraUpDownServo = CAMERA_STRAIGHT_FORWARD;
        break;

      case 's':
        // Останавливаемся.
        leftMotor = 0;
        rightMotor = 0;
        break;

    }
    
    Serial.write(inByte);
    Serial.write('\n');
  
  } else {
    // Если никакая команда не пришла, то поспать немного, чтобы зря не расходовать батарею.
        Serial.println(cameraLeftRightServo);
    delay(1000);
  }

}


