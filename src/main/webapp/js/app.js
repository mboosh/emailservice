/**
 * 
 */
var emailServiceApp = angular.module('EmailServiceApp', ["ngMessages"]);

var emailServiceController = emailServiceApp.controller('EmailServiceController', ['$scope', '$http', function($scope, $http) {  
  $scope.submitForm = function() {
	$scope.delay = true;
	  
    emailData = {
	  toEmail: $scope.toemail,
	  ccEmail: $scope.ccemail,
	  subject: $scope.subject,
	  content: $scope.content
    };	  
	  
	var res = $http.post('service', emailData);
	res.success(function(data, status, headers, config) {
		$scope.delay = false;
		$scope.message = data;
	});
	res.error(function(data, status, headers, config) {
		$scope.delay = false;
		alert("An Error Occured: " + JSON.stringify({data: data}));
	});
  };  
}]);