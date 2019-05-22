import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';

// Этот класс занимается обменом данными с сервером запущенным на плате Raspberry Pi.
// Этот сервис позволяет отправлять данные 
@Injectable({
  providedIn: 'root'
})
export class PiService {

  constructor(
    private http: Http
  ) { }

  // Отправляет данные в 
  sendData(name: string, value: any): Observable<string> {

    return this.http.post('/api/control/' + name, value)
      .map((response: Response) => response.json());

  }


}
