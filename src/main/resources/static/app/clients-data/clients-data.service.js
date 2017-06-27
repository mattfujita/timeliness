class ClientsDataService {
  constructor($http) {
    this.$http = $http;
  }
  
  getAll() {
    return this.$http
      .get('/api/clients')
      .then(response => response.data);
  }
  
  get(id) {
    return this.$http
      .get(`/api/clients/${id}`)
      .then(response => response.data);
  }
  
  update(id, name) {
    return this.$http
      .put(`/api/clients/${id}`, { id, name })
      .then(response => response.data);
  }
  
  deactivate(id) {
    return this.$http
      .post('/api/clients/deactivations', { id })
      .then(repsonse => response.data);
  }
  
  activate(id) {
    return this.$http
      .post('/api/clients/activations', { id })
      .then(repsonse => response.data);
  }
  
  create(name) {
    return this.$http
      .post('/api/clients', { name })
      .then(response => response.data);
  }
}

angular
  .module('app')
  .factory('clientsData', [
    '$http',
    ($http) => new ClientsDataService($http)
  ]);
