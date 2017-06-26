class LoginCardController {
  constructor($http, $window) {
    this.username = '';
    this.password = '';
    this.$http = $http;
    this.$window = $window;
  }

  submitForm() {
    let credentials = {
      username: this.username,
      password: this.password
    }
    this.$http
      .put('/api/session/mine', credentials)
      .then(() => {
        this.$window.location.href = '/';
      })
      .catch(() => {
        this.error = 'Cannot login with that username and password';
      });
  }
}

angular
  .module('app')
  .component('loginCard', {
    templateUrl: '/app/login-card/login-card.component.html',
    controllerAs: 'login',
    controller: [
      '$http',
      '$window',
      ($http, $window) => new LoginCardController($http, $window)
    ]
  });
