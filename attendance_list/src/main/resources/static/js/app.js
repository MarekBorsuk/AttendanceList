'use strict';

angular.module('app', ['ngRoute', 'ngResource'])
.config(function ($routeProvider) {
	$routeProvider
	.when('/list', {
		templateUrl: 'partials/list.html',
		controller: 'ListController',
		controllerAs: 'listCtrl'
	})
	.when('/details/:id', {
		templateUrl: 'partials/details.html',
		controller: 'DetailsController',
		controllerAs: 'detailsCtrl'
	})
	.when('/new', {
		templateUrl: 'partials/new.html',
		controller: 'NewController',
		controllerAs: 'newCtrl'
	})
	.otherwise({
		redirectTo: '/list'
	});
})
.constant('LESSON_ENDPOINT', '/api/lessons/:id')
.factory('Lesson', function($resource, LESSON_ENDPOINT) {
	return $resource(LESSON_ENDPOINT);
})
.service('Lessons', function(Lesson) {
	this.getAll = function() {
		return Lesson.query();
	}
	this.get = function(index) {
		return Lesson.get({id: index});
	}
	this.add = function(lesson) {
		lesson.$save();
	}
})
.controller('ListController', function(Lessons) {
	var vm = this;
	vm.lessons = Lessons.getAll();
})
.controller('DetailsController', function($routeParams, Lessons) {
	var vm = this;
	var lessonIndex = $routeParams.id;
	vm.lesson = Lessons.get(lessonIndex);
})
.controller('NewController', function(Lessons, Lesson) {
	var vm = this;
	vm.lesson = new Lesson();
	vm.saveLesson = function() {
		Lessons.add(vm.lesson);
		vm.lesson = new Lesson();
	}
});
