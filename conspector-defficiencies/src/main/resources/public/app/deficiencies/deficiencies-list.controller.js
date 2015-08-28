'use strict';

module = require('./_index');

function DeficienciesListCtrl($scope, $timeout, $stateParams, $http, $window, $cookies, APP_SETTINGS) {
    $scope.authValues = APP_SETTINGS.authValues;

    $scope.onLogOut = function() {
        $http.post('logout', {}).success(function() {}).error(function(data) {});

    };
}

module.controller('DeficienciesListCtrl', DeficienciesListCtrl);