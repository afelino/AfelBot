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
        serial.open("/dev/ttyUSB0", 9600);
        
        // Прописываем обработчик, который выведет на консоль
        serial.addListener(new Listener());
        
    }

    @Override
    public synchronized void forward(){
        serial.write((byte) 'f');
        serial.flush();
    }
    
    @Override
    public synchronized void left() {
        serial.write((byte) 'l');
        serial.flush();
    }
    
    @Override
    public synchronized void right () {
        serial.write((byte) 'r');
        serial.flush();
    }

    @Override
    public synchronized void backward () {
        serial.write((byte) 'b');
        serial.flush();
    }

    @Override
    public synchronized void stop () {
        serial.write((byte) 's');
        serial.flush();
    }

    @Override
    public void cameraUp() {
        serial.write((byte) 'u');
        serial.flush();
    }

    @Override
    public void cameraDown() {
        serial.write((byte) 'd');
        serial.flush();
    }

    @Override
    public void cameraLeft() {
        serial.write((byte) 'q');
        serial.flush();
    }

    @Override
    public void cameraRight() {
        serial.write((byte) 'e');
        serial.flush();
    }

    @Override
    public void cameraCenter() {
        serial.write((byte) 'w');
        serial.flush();
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
