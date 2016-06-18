package ru.tsirkunov.afelbot;

/**
 * Этот драйвер нужен для тестирования логики основной программы вне робота,
 * либо когда отправка команд на настоящий драйвер двигателя нежелательна.
 * @author Циркунов Виталий Андреевич
 */
public class NullMotorDriver implements MotorDriver {

    @Override
    public void backward() {
        System.out.println("Движение назад.");
    }

    @Override
    public void forward() {
        System.out.println("Движение вперед.");
    }

    @Override
    public void left() {
        System.out.println("Движение налево.");
    }

    @Override
    public void right() {
        System.out.println("Движение направо.");
    }

    @Override
    public void stop() {
        System.out.println("Остановка.");
    }

    @Override
    public void cameraUp() {
        System.out.println("Камера вверх.");
    }

    @Override
    public void cameraDown() {
        System.out.println("Камера вниз.");
    }

    @Override
    public void cameraLeft() {
        System.out.println("Камера влево.");
    }

    @Override
    public void cameraRight() {
        System.out.println("Камера вправо.");
    }

    @Override
    public void cameraCenter() {
        System.out.println("Камера по центру.");
    }
    
}
