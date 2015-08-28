'use strict';

require('./global.service');
require('../general-layout/_index');
require('../login/_index');

angular.element(document).ready(function() {
    var requires = [
        'ui.router',
        'templates',
        'globals',
        'generalLayout',
        'login',
        'ngCookies'
    ];

    angular.module('app', requires);
    var APP_SETTINGS = require('./constants');
    angular.module('app').constant('APP_SETTINGS', APP_SETTINGS);

    var injector = angular.injector(['app']);
    var globalService = injector.get('globalService');

    angular.module('app').config(require('./on_config'));
    angular.module('app').run(require('./on_run'));
    angular.bootstrap(document, ['app']);

});