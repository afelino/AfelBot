package ru.tsirkunov.afelbot;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;

/**
 * Первая версия программы, которая заставляет робота двигаться по кругу.
 *
 * @author Виталий Циркунов
 */
public class AfelBotMain {

    // Время, на которое включаются двигатели вперед.
    private static final int DRIVE_FORWARD_TIME_MS = 500;

    // Время, на которое включаются двигатели для разворота.
    private static final int TURN_TIME_MS = 250;

    // Время, на которое выключаются двигатели.
    private static final int STOP_TIME_MS = 250;

    /**
     * 
     * @param args
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        Serial serial = SerialFactory.createInstance();
        serial.open("/dev/ttyUSB0", 9600);
        
        // Прописываем обработчик, который выведет на консоль
        serial.addListener(new Listener());

        try {
            while (true) {
                // Двигаться вперед
                serial.write((byte) 'a');
                serial.flush();
                Thread.sleep(DRIVE_FORWARD_TIME_MS);

                // Разворачиваться
                serial.write((byte) 's');
                serial.flush();
                Thread.sleep(TURN_TIME_MS);

                // Остановиться
                serial.write((byte) 'd');
                serial.flush();
                Thread.sleep(STOP_TIME_MS);
            }
        } finally {
            // Когда мы останавливаем программу с помощью Ctrl+C, блок finally пошлет последнюю
            // команду, чтобы остановить двигатели.
            serial.write((byte) 'd');
            serial.flush();
        }

    }

    private static class Listener implements SerialDataListener {

        @Override
        public void dataReceived(SerialDataEvent event) {
            // Вывод потока данных от платы управления двигателями.
            System.out.println(event.getData());
        }
    }

}
