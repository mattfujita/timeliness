class TimeEntriesController {}

angular
  .module('app')
  .component('timeEntries', {
    templateUrl: '/app/time-entries/time-entries.component.html',
    controllerAs: 'timeEntries',
    controller: [() => new TimeEntriesController()]
  });
