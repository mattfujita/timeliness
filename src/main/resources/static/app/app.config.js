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
        component: 'clientsList',
        resolve: {
          data: ['clientsData', clientsData => clientsData.getAll()],
          transitionService: ['$transition$', $transition$ => $transition$.router.transitionService],
          initialStateName: ['$transition$', $transition$ => $transition$.to().name]
        }
      };
        
      const clientsCreateState = {
        name: 'main.clients.create',
        url: '/new',
        component: 'clientsForm',
        resolve: {
          data: () => ({})
        }
      };
        
      const clientsEditState = {
        name: 'main.clients.edit',
        url: '/:clientId',
        component: 'clientsForm',
        resolve: {
          data: [
            'clientsData',
            '$transition$',
            (clientsData, $transition$) => {
              let clientId = Number.parseInt($transition$.params().clientId);
              return clientsData.get(clientId);
            }
          ]
        }
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
      $stateProvider.state(clientsCreateState);
      $stateProvider.state(clientsEditState);
      $stateProvider.state(reportState);
      
      $locationProvider.html5Mode(true);
      $urlServiceProvider.rules.otherwise({ state: 'login' });
    }
  ]);
