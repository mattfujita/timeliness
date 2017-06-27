class LoginCardController {
  constructor(auth, $state) {
    this.username = '';
    this.password = '';
    this.auth = auth;
    this.$state = $state;
  }

  submitForm() {
    this.auth
      .login(this.username, this.password)
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
      'authentication',
      '$state',
      (auth, $state) => new LoginCardController(auth, $state)
    ]
  });
