class ClientsListController {
  $onInit() {
    this.stateName = this.initialStateName;
    this.offFinish = this.transitionService
      .onFinish({}, transition => this.stateName = transition.to().name)
  }
  
  $onDestroy() {
    console.log('de-registering');
    this.offFinish();
  }
  
  isOnlyListState() {
    return this.stateName === 'main.clients';
  }
}

angular
  .module('app')
  .component('clientsList', {
    templateUrl: '/app/clients-list/clients-list.component.html',
    controllerAs: 'clientsList',
    controller: [() => new ClientsListController()],
    bindings: {
      data: '<',
      transitionService: '<',
      initialStateName: '<'
    }
  });
