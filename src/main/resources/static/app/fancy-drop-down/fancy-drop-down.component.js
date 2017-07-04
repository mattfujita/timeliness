class FancyDropDownController {
  $onInit() {
    this.iconName = 'arrow_drop_down';
  }
}

angular
  .module('app')
  .component('fancyDropDown', {
    templateUrl: '/app/fancy-drop-down/fancy-drop-down.component.html',
    controller: [() => new FancyDropDownController()],
    controllerAs: 'dropDown',
    transclude: true,
    bindings: {
      iconName: '@'
    }
  });
