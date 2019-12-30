package ru.tsirkunov.afelbot;

import java.util.Arrays;
import org.reactivestreams.Processor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Schedulers;

/**
 * Главный запускаемый файл.
 * 
 * В этом файле осуществляется обработка HTTP-запросов.
 *
 * @author Виталий Циркунов Андреевич
 */
@SpringBootApplication
public class AfelBotMain {

    /**
     * Точка входа в программу, с этой процедуры начинается выполнение программы.
     * @param args
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(AfelBotMain.class, args);
    }
        
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Перечисление бинов.");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
    
    /**
     * Создает процессор, который пропускает избыточные команды от джойстика.
     * И подключает к нему драйвер двигателя.
     * @param motorDriver драйвер двигателя.
     * @return
     */
    @Bean(name = "motorProcessor")
    public UnicastProcessor<MainMotorPower> motorProcessor(MotorDriver motorDriver){
        UnicastProcessor<MainMotorPower> processor = UnicastProcessor.create(new LastElementQueue());
        
        processor
                .publishOn(Schedulers.single())
                .subscribe(motorDriver);
        
        return processor;
    }

}
