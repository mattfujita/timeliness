class ReportController {}

angular
  .module('app')
  .component('report', {
    templateUrl: '/app/report/report.component.html',
    controllerAs: 'report',
    controller: [() => new ReportController()]
  });
