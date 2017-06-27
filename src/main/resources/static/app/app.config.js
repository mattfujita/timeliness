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
        component: 'mainScreen'
      };
      
      const timeEntriesState = {
        name: 'main.entries',
        url: '/',
        component: 'timeEntries'
      };
      
      const clientsListState = {
        name: 'main.clients',
        url: '/clients',
        component: 'clientsList'
      };
      
      const reportState = {
        name: 'main.report',
        url: '/report',
        component: 'report'
      }

      $stateProvider.state(loginState);
      $stateProvider.state(signUpState);
      $stateProvider.state(mainScreen);
      $stateProvider.state(timeEntriesState);
      $stateProvider.state(clientsListState);
      $stateProvider.state(reportState);
      
      $locationProvider.html5Mode(true);
      $urlServiceProvider.rules.otherwise({ state: 'login' });
    }
  ]);
