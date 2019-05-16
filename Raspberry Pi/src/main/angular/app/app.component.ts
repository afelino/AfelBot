import { Component, ViewChild } from '@angular/core';
import * as Nipplejs from 'nipplejs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  private options = {
    // zone: Element,                  // active zone
    // color: String,
    // size: Integer,
    // threshold: Float,               // before triggering a directional event
    // fadeTime: Integer,              // transition time
    // multitouch: Boolean,
    // maxNumberOfNipples: Number,     // when multitouch, what is too many?
    // dataOnly: Boolean,              // no dom element whatsoever
    // position: Object,               // preset position for 'static' mode
    // mode: String,                   // 'dynamic', 'static' or 'semi'
    // restJoystick: Boolean,
    // restOpacity: Number,            // opacity when not 'dynamic' and rested
    // lockX: Boolean,                 // only move on the X axis
    // lockY: Boolean,                 // only move on the Y axis
    // catchDistance: Number           // distance to recycle previous joystick in
    // 'semi' mode
  };

  private manager;

  ngOnInit() {
    var joystickL = Nipplejs.create({
      zone: document.getElementById('left'),
      mode: 'static',
      position: { left: '20%', top: '50%' },
      color: 'green',
      size: 200
    });
    
    var joystickR = Nipplejs.create({
      zone: document.getElementById('right'),
      mode: 'static',
      position: { left: '80%', top: '50%' },
      color: 'red',
      size: 200
    });
  }
}
