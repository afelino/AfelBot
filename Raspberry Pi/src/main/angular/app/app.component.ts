import { Component, ViewChild } from '@angular/core';
import * as Nipplejs from 'nipplejs';
import { JoystickControl } from './JoystickControl';
import { PiService } from './pi.service';

@Component({
  selector: 'app-root',
  providers: [PiService],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  private piService: PiService;

  private cameraJoystick;
  private cameraControl: JoystickControl;

  private motorJoystick;
  private motorControl: JoystickControl;

  constructor(piService: PiService) {
    this.piService = piService;
  }

  ngOnInit(): void {
    this.cameraControl = new JoystickControl();
    this.cameraJoystick = Nipplejs.create({
      zone: document.getElementById('left'),
      mode: 'static',
      position: { left: '50%', top: '50%' },
      color: 'green',
      restJoystick: false,
      size: 200
    });

    // Инициализация джойстика основных двигателей.
    this.motorControl = new JoystickControl();
    console.log(this.motorControl);
    this.motorJoystick = Nipplejs.create({
      zone: document.getElementById('right'),
      mode: 'static',
      position: { left: '50%', top: '50%' },
      color: 'red',
      size: 200
    });
    // Реагируем на изменение положения джойстика.
    this.motorJoystick.on('move', (evt, data) => {
      console.log(this);
      this.motorControl.angle = data.angle.radian;
      this.motorControl.distance = data.distance;
      console.log('Позиция: ' + data.distance + ' ' + data.angle.degree);
      this.piService.sendData('motor', this.motorControl)
        .subscribe((res) => { });
    });
    // Реагируем на отпускание джойстика. (Останавливаемся.)
    this.motorJoystick.on('end', (evt) => {
      this.motorControl.reset();
      console.log('Позиция: ' + 0 + ' ' + 0);
      this.piService.sendData('motor', this.motorControl)
        .subscribe((res) => { });
    });
  };

}
