class ReportDataService {
  constructor($http) {
    this.$http = $http;
  }
  
  create(clientId) {
    return this.$http
      .post('/api/report', { id: clientId })
      .then(response => response.data);
  }
}

angular
  .module('app')
  .factory('reportData', [
    '$http',
    ($http) => new ReportDataService($http)
  ]);
