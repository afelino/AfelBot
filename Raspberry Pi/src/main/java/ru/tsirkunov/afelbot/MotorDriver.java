package ru.tsirkunov.afelbot;

/**
 * Интерфейс драйвера двигательной системы. Пока реализуется простой набор
 * команд, но достаточный для того, чтобы двигаться куда захочет оператор.
 * @author Циркунов Виталий Андреевич
 */
public interface MotorDriver {

    /**
     * Двигаться назад.
     */
    public void backward();

    /**
     * Двигаться вперед.
     */
    public void forward();

    /**
     * Поворачивать влево на месте.
     */
    public void left();

    /**
     * Поворачивать вправо на месте.
     */
    public void right();

    /**
     * Остановиться.
     */
    public void stop();
    
    /**
     * Поднять камеру
     */
    public void cameraUp(); 
    
    /**
     * Опустить камеру
     */
    public void cameraDown(); 
    
    /**
     * Повернуть камеру влево.
     */
    public void cameraLeft(); 
    
    /**
     * Повернуть камеру вправо.
     */
    public void cameraRight(); 
    
    /**
     * Отцентрировать камеру.
     */
    public void cameraCenter(); 
    
}
