package ru.tsirkunov.afelbot;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Команда, которая направляется в драйер двигателя для того, чтобы изменить
 * мощность подаваемую на основные двигатели.
 * 
 * Можность задается целым числом от 0 до 255, где 0 означет полный стоп,
 * а 255 - полный газ. Знак числа выражает направление, положительные числа
 * означают движение вперед, а отрицательные назад.
 * 
 * @author Циркунов Виталий Андреевич
 */
@AllArgsConstructor
public class MainMotorPower {
    
    /**
     * Мощность подаваемая на левый двигатель.
     */
    @Getter
    private final short left;
    
    /**
     * Мощность подаваемая на правый двигатель.
     */
    @Getter
    private final short right;
    
}
