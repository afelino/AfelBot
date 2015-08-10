package ru.tsirkunov.afelbot;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Версия, которая мигает светодиодом.
 *
 * @author Санёк Баглай
 * @author Виталий Циркунов
 */
public class AfelBotMain {

    private static final int SHORT = 300;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello world !!!");

        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "MyLED", PinState.HIGH);

        try {
            while (true) {
                pin.high();
                Thread.sleep(SHORT);
                pin.low();
                Thread.sleep(SHORT);
            }
        } finally {

            gpio.shutdown();
        }
    }
}
