class MainScreenController {
  constructor(auth, $state) {
    this.auth = auth;
    this.$state = $state;
  }
  
  logout() {
    this.auth
      .logout()
      .then(() => {
        this.$state.go('login');
      })
      .catch(() => {
        this.$state.go('login');
      });
  }
}

angular
  .module('app')
  .component('mainScreen', {
    templateUrl: '/app/main-screen/main-screen.component.html',
    controllerAs: 'mainScreen',
    controller: [
      'authentication',
      '$state',
      (auth, $state) => new MainScreenController(auth, $state)
    ]
  });
