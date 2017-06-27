class SignUpCardController {
  constructor(auth, $state) {
    this.username = '';
    this.password = '';
    this.auth = auth;
    this.$state = $state;
  }
  
  submitForm() {
    this.auth
      .register(this.username, this.password)
      .then(() => {
        this.$state.go('main');
      })
      .catch(() => {
        this.error = 'Please select another username because that one is already being used';
      });
  }
}

angular
  .module('app')
  .component('signUpCard', {
    templateUrl: '/app/sign-up-card/sign-up-card.component.html',
    controllerAs: 'signUp',
    controller: [
      'authentication',
      '$state',
      (auth, $state) => new SignUpCardController(auth, $state)
    ]
  });
