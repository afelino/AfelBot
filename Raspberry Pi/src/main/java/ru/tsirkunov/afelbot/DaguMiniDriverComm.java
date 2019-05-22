package ru.tsirkunov.afelbot;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;

/**
 * Реализует логику общения с платой DaguMiniDriver.
 * @author Циркунов Виталий Андреевич
 */
public class DaguMiniDriverComm implements MotorDriver {

    private final Serial serial;
    
    public DaguMiniDriverComm(){
        serial = SerialFactory.createInstance();
        serial.open("/dev/ttyUSB0", 115200);
        
        // Прописываем обработчик, который выведет на консоль
        serial.addListener(new Listener());
        
    }

    /**
     * Метод передает 5 байт в драйвер двигателя. Не самое оптимальное решение,
     * можно передавть всего 3 байта, если нужно будет соптимизировать, я
     * переделаю. В любом случае 
     * @param left
     * @param right 
     */
    @Override
    public synchronized void motor(int left, int right) {
        byte[] buf = new byte[5];
        buf[0] = (byte)'d'; // первый байт всегда d.

        if(left < 0){
            buf[1] = -1; // направление для левого двигателя
        }
        buf[2] = (byte)Math.abs(left);
        
        if(right < 0){
            buf[3] = -1;
        }
        buf[4] = (byte)Math.abs(right);
        
        serial.write(buf);
    }

    private class Listener implements SerialDataListener {

        @Override
        public void dataReceived(SerialDataEvent event) {
            synchronized(DaguMiniDriverComm.this){
                System.out.println(event.getData());
            }
        }
    }

}
