package ru.tsirkunov.afelbot;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.reactivestreams.Subscription;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Этот драйвер нужен для тестирования логики основной программы вне робота,
 * либо когда отправка команд на настоящий драйвер двигателя нежелательна.
 * @author Циркунов Виталий Андреевич
 */
@Service
@Profile("debug")
public class NullMotorDriver implements MotorDriver {

    private volatile Subscription subscribtion;
    
    /**
     * Сразу заявляем, что готовы принимать данные.
     * @param subscribtion 
     */
    @Override
    public void onSubscribe(Subscription subscribtion) {
        this.subscribtion = subscribtion;
        subscribtion.request(1L);
    }

    /**
     * Кога приходят данные, распечатываем на экране значения, и тут же
     * запрашиваем следующие данные.
     * @param t 
     */
    @Override
    public void onNext(MainMotorPower t) {
        print(t.getLeft(), t.getRight());
        subscribtion.request(1L);
        try {
            Thread.sleep(500L);
        } catch (InterruptedException ex) {
        }
    }

    @Override
    public void onError(Throwable thrwbl) {}

    @Override
    public void onComplete() {}

    public void print(int left, int right) {
        System.out.print("Двигатели: ");
        printMotorData("левый", left);
        System.out.print(", ");
        printMotorData("правый", right);
        System.out.println();
    }
    
    private void printMotorData(String name, int value){
        System.out.print(name + " " + direction(value) + " " + formatValue(value));
    }

    // Форматирует число так, чтобы оно всегда занимало 4 знакоместа, добавляя
    // пробелы в начале.
    private String formatValue(int value){
        StringBuilder sb = new StringBuilder();
        int abs = Math.abs(value);
        if(abs < 100){
            sb.append(" ");
        }
        if(abs < 10){
            sb.append(" ");
        }
        
        sb.append(abs);
        
        return sb.toString();
    }
    
    // определяет направление движения.
    private String direction (int value){
        if(value == 0){
            return "стоп   ";
        } else if (value > 0){
            return "вперед";
        } else {
            return "назад ";
        }
    }

}
