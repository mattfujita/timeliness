class ClientsDataService {
  constructor($http) {
    this.$http = $http;
    this.clients = [];
  }
  
  getAll() {
    return this.$http
      .get('/api/clients')
      .then(response => response.data)
      .then(clients => this.clients = clients);
  }
  
  get(id) {
    return this.clients.find(c => c.id === id);
  }
  
  update(id, name) {
    return this.$http
      .put(`/api/clients/${id}`, { id, name })
      .then(response => response.data)
      .then(client => {
        let old = this.get(client.id);
        Object.assign(old, client);
        return old;
      });
  }
  
  deactivate(id) {
    return this.$http
      .post('/api/clients/deactivations', { id })
      .then(response => response.data)
      .then(client => {
        let old = this.get(client.id);
        Object.assign(old, client);
        return old;
      });
  }
  
  activate(id) {
    return this.$http
      .post('/api/clients/activations', { id })
      .then(response => response.data)
      .then(client => {
        let old = this.get(client.id);
        Object.assign(old, client);
        return old;
      });
  }
  
  create(name) {
    return this.$http
      .post('/api/clients', { name })
      .then(response => response.data)
      .then(clients => {
        for (let i = 0; i < clients.length; i += 1) {
          this.clients[i] = clients[i];
        }
      });
  }
}

angular
  .module('app')
  .factory('clientsData', [
    '$http',
    ($http) => new ClientsDataService($http)
  ]);
