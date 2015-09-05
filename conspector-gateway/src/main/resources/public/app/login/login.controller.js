'use strict';

module = require('./_index');

function LoginCtrl($scope, $timeout, $stateParams, $http, $window, $cookies, APP_SETTINGS) {
    removeErrors();

    $stateParams.location = $stateParams.location || APP_SETTINGS.apiUrl.defaultRedirect;

    function onLoginSuccess(response) {
        $window.location = $stateParams.location;
    }

    function onLoginFailure(response) {
        $scope.authenticationError = APP_SETTINGS.errorMessages.authenticationFailed;

        $scope.username = '';
        $scope.password = '';
    }

    $scope.login = function() {
        removeErrors();

        if (!$scope.username || !$scope.password) {
            $scope.formValidation.username = $scope.username ? null : APP_SETTINGS.errorMessages.emptyUsername;
            $scope.formValidation.password = $scope.password ? null : APP_SETTINGS.errorMessages.emptyPassword;

            return;
        }

        var postData = {
            userName: $scope.username,
            password: $scope.password
        };

        var headers = postData ? {
            authorization: "Basic " + btoa(postData.userName + ":" + postData.password)
        } : {};

        $http.post('http://localhost:8080/login?remember_me=true', postData).success(function(data) {
            onLoginSuccess();
        }).error(function() {
            onLoginFailure();
        });

        // $http.get('http://localhost:8080/user', {
        //     headers: headers //,
        //     // withCredentials: true
        // }).success(function(data) {
        //     onLoginSuccess();
        // }).error(function() {
        //     onLoginFailure();
        // });
    };

    function removeErrors() {
        $scope.formValidation = {
            username: null,
            password: null
        };

        $scope.authenticationError = null;
    }
}

module.controller('LoginCtrl', LoginCtrl);