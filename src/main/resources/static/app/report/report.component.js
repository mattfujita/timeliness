class ReportController {
  constructor(clientsData, reportData) {
    this.clientsData = clientsData;
    this.reportData = reportData;
  }
  
  $onInit() {
    this.clientsData
      .getAll()
      .then(clients => this.clients = clients);
  }
  
  generate() {
    this.reportData
      .create(this.selectedClient.id)
      .then(data => this.data = data);
  }
}

angular
  .module('app')
  .component('report', {
    templateUrl: '/app/report/report.component.html',
    controllerAs: 'report',
    controller: [
      'clientsData',
      'reportData',
      (clientsData, reportData) => new ReportController(clientsData, reportData)]
  });
