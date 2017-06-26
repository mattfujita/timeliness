class SignUpCardController {
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
        .post('/api/users', credentials)
        .then(() => {
          this.$window.location.href = '/';
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
      '$window',
      ($http, $window) => new SignUpCardController($http, $window)
    ]
  });
