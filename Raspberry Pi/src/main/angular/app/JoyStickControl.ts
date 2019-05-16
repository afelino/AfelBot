// Класс, который позволяет хранить и передавать значения от джойстика на сервер.

export class JoyStickControl {
    constructor(
        public x: number,
        public y: number
    ) { }
}