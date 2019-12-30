package ru.tsirkunov.afelbot;

import org.reactivestreams.Subscriber;

/**
 * Интерфейс драйвера двигательной системы. Пока реализуется простой набор
 * команд, но достаточный для того, чтобы двигаться куда захочет оператор.
 * @author Циркунов Виталий Андреевич
 */
public interface MotorDriver extends Subscriber<MainMotorPower>{

//    /**
//     * Можность подаваемая на двигатели.Допустимые значения от 0 до 255, где
//     * 0 - полная остановка, а 255 полный газ. При этом знак числа определяет
//     * направление, + - вперед, а - это назад. 
//     * 
//     * Значеня int, потому что внутри Java все типы меньше int все равно
//     * приводит к int, когда они передаются в качестве параметров.
//     * 
//     * @param left левый двигатель.
//     * @param right правый двигатель.
//     * @throws java.io.IOException Может формироваться ошибка.
//     */
//    public void motor(int left, int right) throws IOException ;
    
}
