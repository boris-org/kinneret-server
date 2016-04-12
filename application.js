var app = angular.module('plunker', []);

app.controller('MainCtrl', function($scope) {
  $scope.tasks = [];
  
  $scope.newtask = '';
  
  $scope.addTask = function() {
    $scope.tasks.push($scope.newtask);
  };
  
  $scope.removeTask = function(item) {
    var index = $scope.tasks.indexOf(item);
    $scope.tasks.splice(index, 1);
  };
});
