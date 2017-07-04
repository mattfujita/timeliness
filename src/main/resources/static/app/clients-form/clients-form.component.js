class ClientFormController {
  constructor(clientsData, $state) {
    this.clientsData = clientsData;
    this.$state = $state;
  }
  
  activate(id) {
    this.clientsData
      .activate(id)
      .then(client => this.data = client);
  }

  deactivate(id) {
    this.clientsData
      .deactivate(id)
      .then(client => this.data = client);
  }
  
  upsert() {
    let apiCall;
    if (this.data.id) {
      apiCall = this.clientsData
        .update(this.data.id, this.data.name);
    } else {
      apiCall = this.clientsData
        .create(this.data.name);
    }
    apiCall.then(() => this.$state.go('main.clients'));
  }
}

angular
  .module('app')
  .component('clientsForm', {
    templateUrl: '/app/clients-form/clients-form.component.html',
    controllerAs: 'form',
    controller: [
      'clientsData',
      '$state',
      (clientsData, $state) => new ClientFormController(clientsData, $state)],
    bindings: {
      data: '<'
    }
  });
