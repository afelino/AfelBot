// Класс, который позволяет хранить и передавать значения от джойстика на сервер.

export class JoystickControl {
    public distance: number = 0;
    public angle: number = 0;

    reset() :void {
        this.distance = 0;
        this.angle = 0;
    };
}