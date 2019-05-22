package ru.tsirkunov.afelbot;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс для передачи данных, связанных с джойстиком.
 *
 * @author Циркунов Виталий Андреевич
 */
@ToString
public class JoystickControl {

    /**
     * Угол отклонения джойстика от крайнего правого положения в радианах.
     */
    @Getter
    @Setter
    private double angle;

    /**
     * Расстояние от центра джойстика до ручки (0 до 100)
     */
    @Getter
    @Setter
    private double distance;

}
