package ru.tsirkunov.afelbot.dagu;

import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.StopBits;
import java.io.IOException;
import org.reactivestreams.Subscription;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.tsirkunov.afelbot.MainMotorPower;
import ru.tsirkunov.afelbot.MotorDriver;

/**
 * Реализует логику общения с платой DaguMiniDriver.
 *
 * @author Циркунов Виталий Андреевич
 */
@Service
@Profile("default")
public class DaguMiniDriverComm implements MotorDriver {

    private volatile Serial serial;

    public DaguMiniDriverComm() throws IOException {
        serial = SerialFactory.createInstance();
        serial.open("/dev/ttyUSB0", Baud._19200, DataBits._8, Parity.NONE, StopBits._1, FlowControl.NONE);

        // Прописываем обработчик, который выведет на консоль
//        serial.addListener(new Listener());

    }

    /**
     * Запрос на максимальное количество данных.
     * @param subscription 
     */
    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    /**
     * Метод передает 5 байт в драйвер двигателя.Не самое оптимальное решение,
     * можно передавть всего 3 байта, если нужно будет соптимизировать, я
     * переделаю. В любом случае 
     * @param power
     */
    @Override
    public void onNext(MainMotorPower power) {
        short left = power.getLeft();
        short right = power.getRight();

        byte[] buf = new byte[5];
        buf[0] = (byte) 'd'; // первый байт всегда d.

        if (left < 0) {
            buf[1] = -1; // направление для левого двигателя
        }
        buf[2] = (byte) Math.abs(left);

        if (right < 0) {
            buf[3] = -1;
        }
        buf[4] = (byte) Math.abs(right);

        boolean repeat = true;
        do {
            try {
                serial.write(buf);
                byte[] readbuf = serial.read(2);
                repeat = !(readbuf[0] == 'd' && readbuf[1] == 4);
            } catch (IllegalStateException | IOException ex) {
            }
        } while (repeat);
    }

    /**
     * Ошибок быть не может.
     *
     * @param arg0
     */
    @Override
    public void onError(Throwable arg0) {
    }

    /**
     * Это событие не может случиться.
     */
    @Override
    public void onComplete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class Listener implements SerialDataEventListener {

        @Override
        public void dataReceived(SerialDataEvent event) {
            synchronized (DaguMiniDriverComm.this) {
                try {
                    byte[] bytes = event.getBytes();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println();
            }
        }
    }

}
