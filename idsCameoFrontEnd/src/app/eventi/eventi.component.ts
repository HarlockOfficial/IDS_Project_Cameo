import { Component, OnInit } from '@angular/core';
import { EventiService } from '../_services/eventi.service';

@Component({
  selector: 'app-eventi',
  templateUrl: './eventi.component.html',
  styleUrls: ['./eventi.component.css']
})
export class EventiComponent implements OnInit {

  constructor(private eventiService: EventiService) { }

  ngOnInit(): void {
    this.onGetAllEvents();
  }

  onGetAllEvents(): void {
    this.eventiService.allEventi()?.subscribe(
      data => {
        console.log(data);
      }
    );
  }

}
