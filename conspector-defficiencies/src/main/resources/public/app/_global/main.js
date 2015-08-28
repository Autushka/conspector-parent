'use strict';

require('./global.service');
require('../general-layout/_index');
require('../deficiencies/_index');

var app = require('./_index'),
    http,
    authValues = {};

angular.element(document).ready(onDocumentReady);

function onDocumentReady() {
    var injector = angular.injector(['app']);
    http = injector.get('$http');
    authValues = {};
    debugger;
    http.get('http://localhost:9000/user', {
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
},        withCredentials: true
    }).success(function(response) {
        if (response) {
            authValues = response;
            createApp();
        } else {
            window.location = "http://localhost:8080/#/login?location=" + encodeURIComponent(window.location.href);
        }

    }).error(function() {
        window.location = "http://localhost:8080/#/login?location=" + encodeURIComponent(window.location.href);
    });
}

function createApp() {
    var APP_SETTINGS = require('./constants');
    APP_SETTINGS.authValues = authValues;

    angular.module('app').constant('APP_SETTINGS', APP_SETTINGS);
    angular.module('app').config(require('./on_config'));
    angular.module('app').run(require('./on_run'));
    angular.bootstrap(document, ['app']);
}