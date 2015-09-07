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

        //************************************************************************************
        //Example of basic authentication (authentication without login page but with request headers)
        //Note: Remember me feature is not supported for basic authentication and default spring security settings
        //************************************************************************************
        // var headers = {
        //     authorization: "Basic " + btoa($scope.username + ":" + $scope.password)
        // };
        // var req = {
        //     url: "http://localhost:8080/user",
        //     headers: headers,
        //     method: "POST",
        //     data: {}
        // };
        //************************************************************************************

        $http.post('login', "username=" + $scope.username + "&password=" + $scope.password + "&remember-me=" + $scope.remember_me, {
            headers: {
                "content-type": "application/x-www-form-urlencoded"
            }
        }).success(function(data) {
            onLoginSuccess();
        }).error(function() {
            onLoginFailure();
        });
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