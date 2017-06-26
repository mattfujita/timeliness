angular
  .module('app')
  .config([
    '$stateProvider',
    '$urlServiceProvider',
    '$locationProvider',
    ($stateProvider, $urlServiceProvider, $locationProvider) => {
      const loginState = {
        name: 'login',
        url: '/session/new',
        component: 'loginPage'
      };

      const signUpState = {
        name: 'signUp',
        url: '/users/new',
        component: 'signUpCard'
      };

      const mainScreen = {
        name: 'main',
        url: '/',
        component: 'mainScreen'
      };

      $stateProvider.state(loginState);
      $stateProvider.state(signUpState);
      $stateProvider.state(mainScreen);
      
      $locationProvider.html5Mode(true);
      $urlServiceProvider.rules.otherwise({ state: 'login' });
    }
  ]);
