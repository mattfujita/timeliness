class MainScreenController {
  constructor($http, $state) {
    this.$http = $http;
    this.$state = $state;
  }
  
  logout() {
    this.$http
      .delete('/api/session/mine')
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
      '$http',
      '$state',
      ($http, $state) => new MainScreenController($http, $state)
    ]
  });
