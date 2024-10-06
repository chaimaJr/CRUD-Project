import { Component, OnInit } from '@angular/core';
import { Task } from '../../models/task.model';
import { TaskService } from '../../services/task.service';


@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})


export class TaskListComponent implements OnInit{

  tasks?: Task[];
  currentTask: Task = {};
  currentIndex = -1;
  title = '';

  constructor(private taskService: TaskService){}

  
  // Initialisation 

  ngOnInit(): void {
      this.retrieveTasks();
  }

  retrieveTasks(): void {

    this.taskService.getAll().subscribe({
      next: (data) => {
        this.tasks = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  refreshList(): void {
    this.retrieveTasks();
    this.currentTask = {};
    this.currentIndex = -1;
  }


  setActiveTask(task: Task, index: number): void {
    this.currentTask = task;
    this.currentIndex = index;
  }

  searchTitle(): void {
    this.currentTask = {};
    this.currentIndex = -1;

    this.taskService.findByTitle(this.title).subscribe({
      next: (data) => {
        this.tasks = data;
        console.log(data)
      },
      error: (e) => console.error(e)
    })


  }


}
