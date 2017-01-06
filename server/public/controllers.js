function ComicController($scope, $http) {

    $scope.item = {};

    $scope.itens = [];
    
    $http.get('/api/comics')
        .success(function(data) {
            $scope.itens = data;
            console.log(data);
        })
        .error(function(data) {
            console.log('Error: ' + data);
        });

    $scope.adicionaItem = function () {
        $scope.itens.push({
            title: $scope.item.title, 
            short_description: $scope.item.short_description,
            picture_url: $scope.item.picture_url,
            date_of_publication: $scope.item.date_of_publication,
            edition_number: $scope.item.edition_number,
            price: $scope.item.price

        });
        $http.post('/api/comics', $scope.item)
            .success(function(data) {
                $scope.formData = {};
                console.log(data);
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
        toastr.success("Comic has been added.");
    };

    $scope.editarItem = function(index){
        $scope.item = $scope.itens[index];
        $scope.edit = true;
    };

    $scope.applyChanges = function(index){
        $http.put('/api/comics/' + $scope.item._id, $scope.item)
        .success(function(data) {
            $scope.item = {};
            console.log(data);
        })
        .error(function(data) {
            console.log('Error: ' + data);
        });
        $scope.item = {};
        $scope.edit = false;
        toastr.success("Comic has been changed.");
    };

    $scope.deleteItem = function(index){
        $scope.item = $scope.itens[index];
        $scope.itens.splice(index, 1);
        $http.delete('/api/comics/' + $scope.item._id)
            .success(function(data) {
                console.log(data);
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
        toastr.success("Comic has been deleted.");
    };
}