class AuthenticationService {
  constructor($http) {
    this.$http = $http;
  }
  
  login(username, password) {
    let credentials = { username, password };
    return this.$http
      .put('/api/session/mine', credentials);
  }
  
  register(username, password) {
    let credentials = { username, password };
    return this.$http
      .post('/api/users', credentials)
  }
  
  logout() {
    return this.$http
      .delete('/api/session/mine');
  }
}

angular
  .module('app')
  .factory('authentication', [
      '$http',
      ($http) => new AuthenticationService($http)
  ]);
