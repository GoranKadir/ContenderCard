(function () {
    'use strict';

    angular
        .module('app')
        .controller('FighterRestController', FighterRestController);

    FighterRestController.$inject = ['$http'];

    function FighterRestController($http) {
        var vm = this;

        vm.fighters = [];
        vm.getAll = getAll;
        vm.deleteFighter = deleteFighter;

        init();

        function init(){
            getAll();
        }

        function getAll(){
            var url = "http://localhost:8080/api/fighter";
            var fightersPromise = $http.get(url);
            fightersPromise.then(function(response){
                vm.fighters = response.data;
            });
        }

        function deleteFighter(id){
            var url = "/api/fighter/" + id;
            $http.post(url).then(function(response){
                vm.fighters = response.data;
            });
        }
    }
})();