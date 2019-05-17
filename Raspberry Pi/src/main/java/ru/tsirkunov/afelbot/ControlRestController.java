package ru.tsirkunov.afelbot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Контроллер, который принимает сигналы на управление основными двигателями.
 * 
 * @author Циркунов Виталий Андреевич
 */
@RestController
@RequestMapping(path = "/api/motor")
public class MotorRestController {
    
    @RequestMapping(method = RequestMethod.GET, path = "test")
    public Mono<String> test(){
        return Mono.just("ОК");
    }
    
}
