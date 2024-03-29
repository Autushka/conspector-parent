'use strict';

module.exports = {
    browserport: 6001,

    root: 'src/main/resources/public',
    dist: 'src/main/resources/public/build',

    styles: {
        libSrc: [
            'src/main/resources/public/lib/bootstrap/dist/css/bootstrap.css',
            'src/main/resources/public/lib/font-awesome/css/font-awesome.css'
        ],
        src: [
            'src/main/resources/public/app/_global/main.less',
            'src/main/resources/public/app/**/*.less'
        ],
        dest: 'src/main/resources/public/build/css'
    },

    scripts: {
        src: 'src/main/resources/public/app/**/*.js',
        dest: 'src/main/resources/public/build/js'
    },

    images: {
        src: 'src/main/resources/public/img/**/*',
        dest: 'src/main/resources/public/build/img'
    },

    fonts: {
        src: ['src/main/resources/public/fonts/**/*'],
        dest: 'src/main/resources/public/build/fonts'
    },

    lib: {
        src: [
            'src/main/resources/public/lib/jquery/jquery.min.js',
            'src/main/resources/public/lib/angular/angular.min.js',
            'src/main/resources/public/lib/angular-ui-router/release/angular-ui-router.min.js',
            'src/main/resources/public/lib/angular-cookies/angular-cookies.min.js',
            'src/main/resources/public/lib/angular-bootstrap/ui-bootstrap.min.js',
            'src/main/resources/public/lib/angular-bootstrap/ui-bootstrap-tpls.min.js'
        ],
        dest: 'src/main/resources/public/build/js'
    },

    views: {
        index: 'src/main/resources/public/index.html',
        src: [
            'src/main/resources/public/app/**/*.html',
            '!src/main/resources/public/lib/**/*.html'
        ],
    },

    gzip: {
        src: 'src/main/resources/public/build/**/*.{css,js}',
        dest: 'src/main/resources/public/build/',
        options: {}
    },

    browserify: {
        entries: ['./src/main/resources/public/app/_global/main.js'],
        bundleName: 'app.js',
        sourcemap: true
    },

    test: {
        karma: 'test/karma.conf.js',
        protractor: 'test/protractor.conf.js'
    },

    tmp: 'src/main/resources/public/build/.tmp'
};