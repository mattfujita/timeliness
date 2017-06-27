class TimeEntriesDataService {
  constructor($http) {
    this.$http = $http;
  }
  
  getAll() {
    return this.$http
      .get('/api/entries')
      .then(response => response.data);
  }
  
  complete(id) {
    return this.$http
      .post('/api/entries/completions', { id })
      .then(repsonse => response.data);
  }
  
  create(clientId) {
    return this.$http
      .post('/api/entries', { client: { id } })
      .then(response => response.data);
  }
}

angular
  .module('app')
  .factory('timeEntriesData', [
    '$http',
    ($http) => new TimeEntriesDataService($http)
  ]);
