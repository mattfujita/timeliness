class SignUpCardController {
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
        .post('/api/users', credentials)
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
      '$http',
      '$state',
      ($http, $state) => new SignUpCardController($http, $state)
    ]
  });
