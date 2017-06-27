class ClientsListController {}

angular
  .module('app')
  .component('clientsList', {
    templateUrl: '/app/clients-list/clients-list.component.html',
    controllerAs: 'clientsList',
    controller: [() => new ClientsListController()]
  });
