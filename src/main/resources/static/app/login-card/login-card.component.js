class LoginCardController {
  constructor($http, $state) {
    this.username = '';
    this.password = '';
    this.$http = $http;
    this.$state = $state;
  }

  submitForm() {
    let credentials = {
      username: this.username,
      password: this.password
    }
    this.$http
      .put('/api/session/mine', credentials)
      .then(() => {
        this.$state.go('main');
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
      '$state',
      ($http, $state) => new LoginCardController($http, $state)
    ]
  });
