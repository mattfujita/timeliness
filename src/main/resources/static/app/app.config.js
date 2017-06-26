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
      
      $stateProvider.state(loginState);
      $stateProvider.state(signUpState);
      
      $locationProvider.html5Mode(true);
      $urlServiceProvider.rules.otherwise({ state: 'login' });
    }
  ])
  .run(function($trace) {
    $trace.enable('TRANSITION');
  });
