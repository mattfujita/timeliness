class TimeEntriesController {
  constructor(clientsData, timeEntriesData) {
    this.clientsData = clientsData;
    this.timeEntriesData = timeEntriesData;
  }
  
  $onInit() {
    this.clientsData
      .getAll()
      .then(clients => this.clients = clients);
    this.timeEntriesData
      .getAll()
      .then(data => this.data = data);
  }
  
  complete(entryId) {
    this.timeEntriesData
      .complete(entryId)
      .then(completedEntry => {
        let index = this.data.findIndex(e => e.id === entryId);
        this.data[index] = completedEntry;
      });
  }
  
  createNewEntry() {
    this.timeEntriesData
    .create(this.selectedClient.id)
    .then(data => {
      this.data = data;
      this.selectedClient = null;
    });
  }
}

angular
  .module('app')
  .component('timeEntries', {
    templateUrl: '/app/time-entries/time-entries.component.html',
    controllerAs: 'timeEntries',
    controller: [
      'clientsData',
      'timeEntriesData',
      (clientsData, timeEntriesData) => new TimeEntriesController(clientsData, timeEntriesData)]
  });
