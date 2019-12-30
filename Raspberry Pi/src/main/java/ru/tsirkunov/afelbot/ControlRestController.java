package ru.tsirkunov.afelbot;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

/**
 * Контроллер, который принимает сигналы на управление основными двигателями.
 * 
 * @author Циркунов Виталий Андреевич
 */
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/control")
public class ControlRestController {

    private static final double HALF_PI = Math.PI / 2.0;
    private static final double THREE_QUATERS_PI = HALF_PI * 3.0;
    
    private static final double MAX_MOTOR_LEVEL = 255.0;

    private final UnicastProcessor<MainMotorPower> motorProcessor;
    
    @RequestMapping(method = RequestMethod.POST, path = "motor")
    public Mono<String> test(@RequestBody JoystickControl control) throws IOException{
        double angle = control.getAngle();
        double left;
        double right;
        
        // Считаем мощность левого и правого двигателя, делим 360 градусов на
        // четверти, для каждой четверти свой алгоритм.
        if(angle <= HALF_PI) {
            left = MAX_MOTOR_LEVEL;
            right = MAX_MOTOR_LEVEL * Math.sin(2 * angle - HALF_PI);
        } else if (angle <= Math.PI){
            left = MAX_MOTOR_LEVEL * Math.cos((angle - HALF_PI) * 2);
            right = MAX_MOTOR_LEVEL;
        } else if (angle <= THREE_QUATERS_PI){
            left = -MAX_MOTOR_LEVEL;
            right = MAX_MOTOR_LEVEL * Math.cos((angle - Math.PI) * 2);
        } else {
            left = -MAX_MOTOR_LEVEL * Math.sin((angle - THREE_QUATERS_PI) * 2 + HALF_PI);
            right = -MAX_MOTOR_LEVEL;
        }
        
        // Уменьшаем рассчитанную мощность с учетом того, что джойстик может быть
        // отклонен от центра не максимально.
        double distance = control.getDistance();
        left = left / 100 * distance;
        right = right / 100 * distance;
        
        // Округляем значения и приводим к типу int.
        short leftInt = (short)Math.round(left);
        short rightInt = (short)Math.round(right);
        
        MainMotorPower mainMotorPower = new MainMotorPower(leftInt, rightInt);
        motorProcessor.onNext(mainMotorPower);

        return Mono.just("true");
    }
    
}
